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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class JoinActivity extends AppCompatActivity {
    private EditText mEmail, mName, mPw, mPwc;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        mAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.txt_email);
        mPw = (EditText) findViewById(R.id.txt_pass);
        mPwc = (EditText) findViewById(R.id.txt_passCheck);

        Button btnSignIn = (Button) findViewById(R.id.bt_join2);
        Button btnForgotPassword = (Button) findViewById(R.id.bt_forgotPassword);

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JoinActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPw.getText().toString().trim();
                String passwordCheck = mPwc.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6 || passwordCheck.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(passwordCheck)) {
                    Toast.makeText(getApplicationContext(), "Enter password again!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!mPw.getText().toString().equals(mPwc.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                callsignup(email, password);
            }
        });

    }

    public void makeDate() {
        Date date = new Date(System.currentTimeMillis()); // 가입 날짜
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = null;
        if (user != null) {
            userId = user.getUid();
        }

        DatabaseReference databaseReference;

        if (userId != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
            databaseReference.child("user_startdate").setValue(date.toString());
            databaseReference.child("user_email").setValue(user.getEmail());
            databaseReference.child("user_uid").setValue(user.getUid());
        }

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }


    //Create Account
    private void callsignup(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(JoinActivity.this, String.format("Signed up Failed%s", task.getException()), Toast.LENGTH_SHORT).show();
                        } else {
                            sendEmailVerification();
                            makeDate();
                            startActivity(new Intent(JoinActivity.this, MainActivity.class));

                            finish();
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // [START_EXCLUDE]

                            if (task.isSuccessful()) {
                                Toast.makeText(JoinActivity.this,
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // Log.e(TAG, "sendEmailVerification", task.getException());
                                Toast.makeText(JoinActivity.this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();

                            }
                            // [END_EXCLUDE]
                        }
                    });
        }
        // [END send_email_verification]
    }


        /*findViewById(R.id.bt_join2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferedWriter bw = null;

                try {
                    bw = new BufferedWriter(new OutputStreamWriter(openFileOutput("login.txt", MODE_PRIVATE)));

                    String makeJ = "{\"USEREMAIL\"" + ":" + "\"" + email.getText().toString() +"\""+ ","
                            + "\"USERNICK\"" + ":" + "\"" + name.getText().toString() +"\"" + ","
                            + "\"USERPW\"" + ":" + "\"" + pw.getText().toString() + "\"" + ","
                            + "\"PWCHECK\"" + ":" + "\"" + pwc.getText().toString() +"\"" + "}";

                    bw.write(makeJ);

                    Toast.makeText(JoinActivity.this, "파일저장됨", Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(bw != null) try{bw.close();} catch (IOException e){}
                }

                //tv.setText(makeJ);
            }
        });

        findViewById(R.id.bt_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BufferedReader br = null;

                try {
                    br = new BufferedReader(new InputStreamReader(openFileInput("login.txt")));

                    String logJson = "";
                    while ((logJson = br.readLine()) != null) {
                        nameText.append(logJson + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(br != null) try{br.close();} catch (IOException e){}
                }
            }
        });*/
}

