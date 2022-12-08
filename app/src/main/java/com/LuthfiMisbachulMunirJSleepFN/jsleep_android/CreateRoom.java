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
    BaseApiService mApiService;
    Context mContext;
    ArrayAdapter adapterCity, adapterBedType;
    Spinner city, bedType;
    EditText roomName, roomAddress, roomPrice, roomSize;
    Button create, cancel;
    CheckBox ac, refrigerator, wifi, bathUb, balcony, restaurant, swimmingPool, fitnessCenter;
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
        roomAddress = findViewById(R.id.addCreateRoom);
        roomPrice = findViewById(R.id.priceCreateRoom);
        roomSize = findViewById(R.id.sizeCreateRoom);

        create = findViewById(R.id.createButton);
        cancel = findViewById(R.id.cancelButton);

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

        create.setOnClickListener(new View.OnClickListener() {
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
                String bed = bedSpin.getSelectedItem().toString();
                String cityStr = citySpin.getSelectedItem().toString();
                bedType = BedType.valueOf(bed);
                city = City.valueOf(cityStr);

                Integer priceObj = new Integer(roomPrice.getText().toString());
                Integer sizeObj = new Integer(roomSize.getText().toString());

                int priceInt = priceObj.parseInt(roomPrice.getText().toString());
                int sizeInt = sizeObj.parseInt(roomSize.getText().toString());
                //price.price = priceInt;
                Room room = requestRoom(MainActivity.savedAccount.id, roomName.getText().toString(), sizeInt, priceInt, facility, city, roomAddress.getText().toString(), bedType);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Cancelled create room");
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
                Integer.parseInt(roomSize.getText().toString()),
                Double.parseDouble(roomPrice.getText().toString()),
                facilityList,
                City.valueOf(city.getSelectedItem().toString()),
                roomAddress.getText().toString(),
                BedType.valueOf(bedType.getSelectedItem().toString())
        ).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    System.out.println("Response Success");
                    Toast.makeText(mContext, "Create Room Successful!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(CreateRoom.this, MainActivity.class);
                    startActivity(move);
                } else{
                    System.out.println("Respond not success");
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