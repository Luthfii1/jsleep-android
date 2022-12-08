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
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.City;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Facility;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Renter;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoom extends AppCompatActivity {
    Button ButtonCreate, ButtonCancel;
    BaseApiService mApiService;
    ArrayAdapter adapterCity, adapterBedType;
    CheckBox ac, refrigerator, wifi, bathUb, balcony, restaurant, swimmingPool, fitnessCenter;
    EditText roomName, price, address, size;
    Context mContext;
    Spinner city, bedType;
    private ArrayList<Facility> facilityList =  new ArrayList<>();
    City cityData;
    BedType bedTypeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        city = findViewById(R.id.spinnerCity);
        bedType = findViewById(R.id.spinnerBedType);

        roomName = findViewById(R.id.NameCreateRoom);
        address = findViewById(R.id.addCreateRoom);
        price = findViewById(R.id.priceCreateRoom);
        size = findViewById(R.id.sizeCreateRoom);

        ButtonCreate = findViewById(R.id.createButton);
        ButtonCancel = findViewById(R.id.cancelButton);

        ac = findViewById(R.id.AC_Facility);
        refrigerator = findViewById(R.id.Refrigator_Fac);
        wifi = findViewById(R.id.Wifi_Fac);
        bathUb = findViewById(R.id.Bathub_fac);
        balcony = findViewById(R.id.balcony_fac);
        restaurant = findViewById(R.id.resto_fac);
        swimmingPool= findViewById(R.id.pool_fac);
        fitnessCenter= findViewById(R.id.fitness_fac);

        adapterCity = new ArrayAdapter<>(getApplicationContext(),R.layout.selected_item, City.values());
        adapterCity.setDropDownViewResource(R.layout.dropdown_item);
        city.setAdapter(adapterCity);

        adapterBedType = new ArrayAdapter<>(getApplicationContext(),R.layout.selected_item, BedType.values());
        adapterBedType.setDropDownViewResource(R.layout.dropdown_item);
        bedType.setAdapter(adapterBedType);

        ButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click");
                if (ac.isChecked())
                    facilityList.add(Facility.AC);
                if (refrigerator.isChecked())
                    facilityList.add(Facility.Refrigerator);
                if (wifi.isChecked())
                    facilityList.add(Facility.WiFi);
                if (bathUb.isChecked())
                    facilityList.add(Facility.Bathtub);
                if (balcony.isChecked())
                    facilityList.add(Facility.Balcony);
                if (restaurant.isChecked())
                    facilityList.add(Facility.Restaurant);
                if (swimmingPool.isChecked())
                    facilityList.add(Facility.SwimmingPool);
                if (fitnessCenter.isChecked())
                    facilityList.add(Facility.FitnessCenter);
                Room createRoom = createRoom();
            }
        });

        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Cancelled!", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(CreateRoom.this, MainActivity.class);
                startActivity(move);
            }
        });
    }

    protected Room createRoom() {
        mApiService.createRoomRequest(
                MainActivity.accountLogin.id,
                roomName.getText().toString(),
                Integer.parseInt(size.getText().toString()),
                Double.parseDouble(price.getText().toString()),
                facilityList,
                City.valueOf(city.getSelectedItem().toString()),
                address.getText().toString(),
                BedType.valueOf(bedType.getSelectedItem().toString())
        ).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    if(response.isSuccessful()){
                        Toast.makeText(mContext, "Create Room Successful!", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(CreateRoom.this, MainActivity.class);
                        startActivity(move);
                    }
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println("Fail");
                System.out.println(t.toString());
                Toast.makeText(mContext, "Create Room Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}