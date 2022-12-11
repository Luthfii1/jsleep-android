package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.*;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Renter;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

/**
 * Created by Luthfi on 11/12/2022.
 * About_Me.java using for set the about me page
 * @author Luthfi
 * there a 3 card view in this page
 * 1. card view will show if the user doesn't have a renter
 * 2. card view will show if the user have a renter
 * 3. card view will show if the user have a renter and the renter have a room
 */

public class About_Me extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    Button ButtonConfirm, ButtonCancel, ButtonRegisterRen, ButtonTopUp, OrderList, LogOut;
    CardView CardRenterReg, CardRegister, CardDetailed;
    TextView NameRent, AddRent, PhoneRent, name, email, balance, RenterName, RenterAdd, RenterPhone;
    EditText InpName, InpAdd, InpPhone, topUpBalance;
    LinearLayout renterButtonLayout, registerLayout, dataLayout;

    /**
     * this method will be called when the page is created
     * @param savedInstanceState
     * @author Luthfi
     * @since 11/12/2022
     * @version 1.0
     *
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        //Connect to API
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //Var for information Account
        name = findViewById(R.id.dataNameAboutMe);
        email = findViewById(R.id.dataEmailAboutMe);
        balance = findViewById(R.id.dataAmountAboutMe);
        topUpBalance = findViewById(R.id.inputTopUpAboutMe);
        ButtonTopUp = findViewById(R.id.buttonTopUpAboutMe);
        OrderList = findViewById(R.id.orderList);
        LogOut = findViewById(R.id.logout);


        if(balance == null){
            balance.setText("0");
        }

        //Save data to accountLogin
        name.setText(MainActivity.accountLogin.name);
        email.setText(MainActivity.accountLogin.email);
        balance.setText(String.valueOf(MainActivity.accountLogin.balance));

        //For register Button
        ButtonRegisterRen = findViewById(R.id.registerRenter_Button);
        CardRegister = findViewById(R.id.card_view_register);

        //For Input Register
        ButtonConfirm = findViewById(R.id.registerButton_2);
        ButtonCancel = findViewById(R.id.cancelButton_2);
        CardRenterReg = findViewById(R.id.card_view_input_register);
        InpName = findViewById(R.id.inputName_2);
        InpAdd = findViewById(R.id.inputAddress_2);
        InpPhone = findViewById(R.id.inputPhone_2);

        //For Detailed Register
        CardDetailed = findViewById(R.id.card_view_info_AboutMe);
        NameRent = findViewById(R.id.Renter_Name);
        AddRent = findViewById(R.id.Renter_Address);
        PhoneRent = findViewById(R.id.Renter_Phone);

        /**
         * this method will be called when the user click the top up button
         *
         */
        ButtonTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopUp(MainActivity.accountLogin.id,Double.parseDouble(topUpBalance.getText().toString()));
            }
        });

        /**
         * this method will be called when the user click the log out button
         * user will be log out from this account and get back to menu login
         */
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Log Out From Your Account", Toast.LENGTH_SHORT).show();
                Intent logout = new Intent(About_Me.this, LoginActivity.class);
                startActivity(logout);
            }
        });

        /**
         * this method will be called when the uer click the renter detail
         * user will see all of the renter from another user to their room
         */
        OrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("See Renter Detail");
                Intent seeOrder = new Intent(About_Me.this, OrderListActivity.class);
                startActivity(seeOrder);
            }
        });

        //Jika belum memiliki renter maka fitur yang dapat digunakan adalah menambah renter
        if(MainActivity.accountLogin.renter == null){
            //Hanya meng-visiblekan button register renter
            CardRegister.setVisibility(CardView.VISIBLE);
            CardRenterReg.setVisibility(CardView.GONE);
            CardDetailed.setVisibility(CardView.GONE);

            //Jika button register renter di klik
            ButtonRegisterRen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Yang menjadi visible adalah card input renter
                    CardRegister.setVisibility(CardView.GONE);
                    CardRenterReg.setVisibility(CardView.VISIBLE);
                    CardDetailed.setVisibility(CardView.GONE);

                    //Jika button confirm di klik maka akan kembali ke class about me
                    ButtonConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Renter renter = requestRenter(MainActivity.accountLogin.id, InpName.getText().toString(),
                                    InpAdd.getText().toString(), InpPhone.getText().toString());
                            Intent move = new Intent(About_Me.this, About_Me.class);
                            startActivity(move);
                        }
                    });

                    //Jika button cancel di klik akan kembali ke tampilan button register renter
                    ButtonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CardRenterReg.setVisibility(CardView.GONE);
                            CardDetailed.setVisibility(CardView.GONE);
                            CardRegister.setVisibility(CardView.VISIBLE);
                        }
                    });
                }
            });
        
            //Jika sudah memiliki akun akan ke layout detailed data
        }else{
            CardDetailed.setVisibility(CardView.VISIBLE);
            CardRegister.setVisibility(CardView.GONE);
            CardRenterReg.setVisibility(CardView.GONE);

            NameRent.setText(MainActivity.accountLogin.renter.username);
            PhoneRent.setText(MainActivity.accountLogin.renter.address);
            AddRent.setText(MainActivity.accountLogin.renter.phoneNumber);
        }
    }



    //Method untuk melakukan renter ketika ingin baru membuar renter
    protected Renter requestRenter(int id, String username, String address, String phone){
        System.out.println(id);
        System.out.println(username);
        System.out.println(address);
        System.out.println(phone);
        mApiService.registerRenter(id, username, address, phone).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    Renter renter;
                    renter = response.body();
                    MainActivity.accountLogin.renter = renter;
                    Toast.makeText(mContext, "Succes Register Renter", Toast.LENGTH_SHORT).show();
                    System.out.println("Success on Response");
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                Toast.makeText(mContext, "Failed to register renter", Toast.LENGTH_SHORT).show();
                System.out.println("Failed on Response");
            }
        });
        return null;
    }

    protected Renter TopUp(int id, double balance){
        mApiService.topUpRequest(id,balance).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Top Up Success", Toast.LENGTH_SHORT).show();
                    MainActivity.accountLogin.balance = MainActivity.accountLogin.balance + balance;
                    recreate();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Fail", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}