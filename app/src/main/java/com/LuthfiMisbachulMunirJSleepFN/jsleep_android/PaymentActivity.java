package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Payment;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    BaseApiService mApiService;
    protected static Payment payment;
    Context mContext;
    Button createbutton, cancelbutton;
    TextView createpayment_from,createpayment_to,createpayment_title_name,
            createpayment_title_address,createpayment_price, balance,
            bedType_Payment, size_Payment, city_Payment, price_Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date enddate = dateFormat.parse(ChooseDate.enddate);
            Date startdate = dateFormat.parse(ChooseDate.startdate);
            long diff = enddate.getTime() - startdate.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            balance = findViewById(R.id.balance);
            balance.setText(String.valueOf(MainActivity.accountLogin.balance));
            createpayment_title_name = findViewById(R.id.name_Detail);
            createpayment_title_address = findViewById(R.id.address_detail);
            createpayment_title_name.setText(DetailRoomActivity.tempRoom.name);
            createpayment_title_address.setText(DetailRoomActivity.tempRoom.address);
            city_Payment = findViewById(R.id.city_Detail);
            city_Payment.setText(String.valueOf(DetailRoomActivity.tempRoom.city));
            size_Payment = findViewById(R.id.size_Detail);
            size_Payment.setText(String.valueOf(DetailRoomActivity.tempRoom.size));
            price_Temp = findViewById(R.id.price_Detail);
            price_Temp.setText(String.valueOf(DetailRoomActivity.tempRoom.price));
            createpayment_price = findViewById(R.id.total_Payment);
            bedType_Payment = findViewById(R.id.bedType_detail);
            bedType_Payment.setText(String.valueOf(DetailRoomActivity.tempRoom.bedType));

            createbutton = findViewById(R.id.acceptPayment);
            cancelbutton = findViewById(R.id.cancelPayment);
            createpayment_from = findViewById(R.id.from_Date);
            createpayment_to = findViewById(R.id.to_Date);
            createpayment_price.setText(String.valueOf(((double)diffDays) * DetailRoomActivity.tempRoom.price.price));
            createpayment_from.setText(ChooseDate.startdate);
            createpayment_to.setText(ChooseDate.enddate);

            createbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked");
                    createPayment(MainActivity.accountLogin.id,
                            DetailRoomActivity.tempRoom.accountId,
                            DetailRoomActivity.tempRoom.id,
                            ChooseDate.startdate,
                            ChooseDate.enddate);
                }
            });

            cancelbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked Failed");
                    Intent moveback = new Intent(PaymentActivity.this, ChooseDate.class);
                    startActivity(moveback);
                }
            });

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected Payment createPayment(int buyerId,
                                    int renterId,
                                    int roomId,
                                    String from,
                                    String to){
        System.out.println("Callback");
        //print all parameter
        System.out.println(buyerId);
        System.out.println(renterId);
        System.out.println(roomId);
        System.out.println(from);
        System.out.println(to);
        mApiService.createPayment(buyerId, renterId, roomId, from, to).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(@NonNull Call<Payment> call, @NonNull Response<Payment> response) {
                System.out.println(response.toString());
                if(response.isSuccessful()){
                    System.out.println("Success");
                    payment = response.body();
                    System.out.println(payment);
//                    MainActivity.accountLogin.balance = MainActivity.accountLogin.balance - String.valueOf(createpayment_price.getText().toString());
                    Toast.makeText(mContext, "Payment created", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(PaymentActivity.this,MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Payment> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Create Payment Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}