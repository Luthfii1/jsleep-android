package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.BedType;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Facility;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Renter;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoom extends AppCompatActivity {
    Button ButtonCreate, ButtonCancel;
    BaseApiService mApiService;
    EditText username, price, address, size;
    Context mContext;
    Spinner city, bedType;
    CheckBox facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButtonCreate = findViewById(R.id.createButton);
        username = findViewById(R.id.NameCreateRoom);
        price = findViewById(R.id.priceCreateRoom);
        address = findViewById(R.id.addCreateRoom);
        size = findViewById(R.id.sizeCreateRoom);
        city = findViewById(R.id.spinnerCity);
        bedType = findViewById(R.id.spinnerBedType);
        ButtonCreate = findViewById(R.id.createButton);
        ButtonCancel = findViewById(R.id.cancelButton);
        facility = findViewById(R.id.AC_Facility);

        setContentView(R.layout.activity_create_room);

        Spinner spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerCity.setAdapter(new ArrayAdapter<Facility>(this, android.R.layout.simple_spinner_item, Facility.values()));
        Spinner spinnerBedType = (Spinner) findViewById(R.id.spinnerBedType);
        spinnerBedType.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));

        ButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Room room = createRoom(MainActivity.accountLogin.id, username.getText().toString(), size, price, facility, city, address.getText().toString(), bedType);
            }
        });
    }

//    protected Room createRoom(){
//        mApiService.create(MainActivity.accountLogin.id, username.getText().toString(), size, price, facility, city, address.getText().toString(), bedType).enqueue(new Callback<Account>() {
//            @Override
//            public void onResponse(Call<Account> call, Response<Account> response) {
//                if(response.isSuccessful()){
//                    Toast.makeText(mContext, "Register Successful", Toast.LENGTH_SHORT).show();
//                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(move);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Account> call, Throwable t) {
//                Toast.makeText(mContext, "Already Registered", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return null;
//    }
}