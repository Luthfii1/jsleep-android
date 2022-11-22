package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class About_Me extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        TextView name = findViewById(R.id.inputNameAboutMe);
        TextView email = findViewById(R.id.inputEmailAboutMe);
        TextView balance =findViewById(R.id.inputBalanceAboutMe);
        name.setText(MainActivity.accountLogin.name);
        email.setText(MainActivity.accountLogin.email);
        balance.setText(String.valueOf(MainActivity.accountLogin.balance));
    }


}