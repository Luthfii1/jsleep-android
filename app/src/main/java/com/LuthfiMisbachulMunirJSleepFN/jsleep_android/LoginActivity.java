package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText username, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        Button loginButton = findViewById(R.id.LoginButtonLogin);
        TextView register = findViewById(R.id.RegisterLogin);
        username = findViewById(R.id.UsernameLogin);
        password = findViewById(R.id.PasswordLogin);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Account account = requestAccount();
//                Intent move = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(move);
            }
        });
    }

    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id=0", Toast.LENGTH_SHORT).show();
                System.out.println(t.toString());
            }
        });
        return null;
    }
}