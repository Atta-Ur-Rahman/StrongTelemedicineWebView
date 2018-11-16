package com.techease.strongtelemedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utilities.withOutBackStackConnectFragment(MainActivity.this, new SplashFragment());



    }

    @Override
    protected void onPause() {
        Utilities.putValueInEditor(this).putBoolean("onPause", false).commit();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Utilities.putValueInEditor(this).putBoolean("onPause",true).commit();
        super.onResume();
    }
}
