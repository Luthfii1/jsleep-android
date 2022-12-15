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

/**
 * The LoginActivity class is an Android activity that represents the login session to JSleep.
 *
 * @author Luthfi Misbachul Munir
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * A {@link BaseApiService} instance for making API requests.
     */
    BaseApiService mApiService;
    /**
     * The {@link EditText} where the user can enter their email and password.
     */
    EditText username, password;
    /**
     * The {@link Context} of the activity.
     */
    Context mContext;
    /**
     * The {@link TextView} where the user can click to go to the register page.
     */
    TextView register;
    /**
     * The {@link Button} where the user can click to login.
     */
    Button ButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Hides the action bar.
         */
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        register = findViewById(R.id.RegisterLogin);
        ButtonLogin = findViewById(R.id.LoginButtonLogin);
        username = findViewById(R.id.UsernameLogin);
        password = findViewById(R.id.PasswordLogin);

        /**
         * Sets the onClickListener for the register {@link TextView}.
         */
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        /**
         * Sets the onClickListener for the login {@link Button}.
         */
        ButtonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){Account account = requestLogin();
            }
        });
    }

    /**
     * Makes a request to the API to login.
     *
     * @return The {@link Account} that is logged in.
     */
    protected Account requestLogin(){
        mApiService.login(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                /**
                 * If the response is successful, the user is logged in.
                 */
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                    Toast.makeText(mContext, "Login Successful", Toast.LENGTH_SHORT).show();
                    MainActivity.accountLogin = account;
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            /**
             * If the response is not successful, the user is not logged in.
             */
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println("failed");
                System.out.println(t.toString());
                Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}