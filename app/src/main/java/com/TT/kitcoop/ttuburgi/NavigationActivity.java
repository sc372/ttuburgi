package com.TT.kitcoop.ttuburgi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.TT.kitcoop.ttuburgi.Data.StudyDataAdapter;
import com.TT.kitcoop.ttuburgi.Data.UserStudyDataAdapter;


public class NavigationActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navigation);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        fragmentManager = getFragmentManager();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_menu1:
                        fragment = new FragmentMenu1();
                        //mTextMessage.setText(R.string.title_home);
                        break;
                    case R.id.navigation_menu2:
                        fragment = new FragmentMenu2();
                        //mTextMessage.setText(R.string.title_dashboard);
                        break;
                    case R.id.navigation_menu3:
                        fragment = new FragmentMenu3();
                        //mTextMessage.setText(R.string.title_notifications);
                        break;
                    case R.id.navigation_menu4:
                        fragment = new FragmentMenu4();
                        //mTextMessage.setText(R.string.title_menu4);
                        break;
                    case R.id.navigation_menu5:
                        fragment = new FragmentMenu5();
                        //mTextMessage.setText(R.string.title_menu5);
                        break;
                }


                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                return true;
            }
        });
    }

}
