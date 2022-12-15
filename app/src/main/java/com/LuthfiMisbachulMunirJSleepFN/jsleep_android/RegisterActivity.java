package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class RegisterActivity extends AppCompatActivity {
    /**
     * A {@link BaseApiService} instance for making API requests.
     */
    BaseApiService mApiService;
    /**
     * The {@link EditText} where the user can enter their desired name, email and password to make a new account.
     */
    EditText username, email, password;
    /**
     * The {@link Context} of the activity.
     */
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        Button register = findViewById(R.id.ButtonRegister);
        username = findViewById(R.id.NameRegister);
        email = findViewById(R.id.EmailRegister);
        password = findViewById(R.id.PasswordRegister);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Account registerAccount = requestRegister();
            }
        });
    }

    /**
     * This function is used to request register to the server
     *
     * @return Account object
     * @see Account
     */
    protected Account requestRegister(){
        mApiService.register(username.getText().toString() ,email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    System.out.println("Succes");
                    Toast.makeText(mContext, "Register Successful", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                System.out.println("Failed");
                Toast.makeText(mContext, "Register Failed, Please Create a 8 length password", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}