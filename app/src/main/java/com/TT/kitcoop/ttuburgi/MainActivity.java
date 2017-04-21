package com.TT.kitcoop.ttuburgi;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.TT.kitcoop.ttuburgi.Data.StudyDataAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;

    private EditText mEmail, mPass;

    private String password;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = null;
        if (user != null) {
            userId = user.getUid();
        }
        DatabaseReference databaseReference;
        if (userId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (user.isEmailVerified() && !dataSnapshot.child("language").exists()) {
                        //value exists, do something
                        startActivity(new Intent(MainActivity.this, LangSelect.class));
                    } else if (user.isEmailVerified() && dataSnapshot.child("language").exists() && !dataSnapshot.child("user_settime").exists()) {
                        startActivity(new Intent(MainActivity.this, TimeSelectActivity.class));

                    } else if (user.isEmailVerified() && dataSnapshot.child("language").exists() && dataSnapshot.child("user_settime").exists()) {
                        startActivity(new Intent(MainActivity.this, NavigationActivity.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        mEmail = (EditText) findViewById(R.id.txt_email);
        mPass = (EditText) findViewById(R.id.txt_pass);

        Button btn_SignIn = (Button) findViewById(R.id.bt_login);
        Button btn_SignUp = (Button) findViewById(R.id.bt_join);

        // 패스워드 찾기
        TextView textView = (TextView)findViewById(R.id.textView3);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResetPasswordActivity.class));
            }
        });

        // 체크 페이지 테스트 위한 임의 버튼이벤트
        Button btnTest = (Button)findViewById(R.id.bt_gitHub);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LangSelect.class));
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Signing Out...");
                }
                // ...
            }
        };

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // trim 때문에 에러가 났다는 게 어이가 없다.
                String email = mEmail.getText().toString().trim();
                password = mPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.equals("") && !password.equals("")) {
                    callsignin(email, password);
                }
            }
        });


        /*btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Signing Out...");
            }
        });*/

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    //Now start Sign In Process
    //SignIn Process
    private void callsignin(String email,String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "sign In Successful:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("TESTING", "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            // there was an error -> 고침
                            if (MainActivity.this.password.length() < 6) {
                                mPass.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                if (user.isEmailVerified()) {
                                    Intent callMainScreen = new Intent(MainActivity.this, LangSelect.class);
                                    callMainScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    callMainScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(callMainScreen);

                                } else {
                                    Toast.makeText(MainActivity.this, "Please verify your Email", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //add a toast to show when successfully signed in

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}