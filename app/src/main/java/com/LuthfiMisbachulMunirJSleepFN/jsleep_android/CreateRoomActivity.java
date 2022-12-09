package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class CreateRoomActivity extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    CheckBox ac, refrig, wifi, bathub, balcony, restaurant, pool, fitness;
    Spinner city, bed;
    EditText nameInput, priceInput, sizeInput, addressInput;
    Button createRoom, cancelCreateRoom;
    ArrayList<Facility> facility = new ArrayList<Facility>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mApiService = UtilsApi.getApiService();
        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);


        //button
        createRoom = findViewById(R.id.createButton);
        cancelCreateRoom = findViewById(R.id.cancelButton);

        //spinner
        city = findViewById(R.id.spinnerCity);
        bed = findViewById(R.id.spinnerBedType);

        //facility checkbox
        ac = findViewById(R.id.AC_Facility);
        refrig = findViewById(R.id.Refrigator_Fac);
        wifi = findViewById(R.id.Wifi_Fac);
        bathub = findViewById(R.id.Bathub_fac);
        balcony = findViewById(R.id.balcony_fac);
        restaurant = findViewById(R.id.resto_fac);
        pool = findViewById(R.id.pool_fac);
        fitness = findViewById(R.id.fitness_fac);

        //text room details
        nameInput = findViewById(R.id.NameCreateRoom);
        priceInput = findViewById(R.id.priceCreateRoom);
        sizeInput = findViewById(R.id.sizeCreateRoom);
        addressInput = findViewById(R.id.addCreateRoom);

        bed.setAdapter(new ArrayAdapter<BedType>(this, android.R.layout.simple_spinner_item, BedType.values()));
        city.setAdapter(new ArrayAdapter<City>(this, android.R.layout.simple_spinner_item, City.values()));

        createRoom.setOnClickListener(v -> {

            if (ac.isChecked()) {
                facility.add(Facility.AC);
            }
            if (refrig.isChecked()) {
                facility.add(Facility.Refrigerator);
            }
            if (wifi.isChecked()) {
                facility.add(Facility.WiFi);
            }
            if (bathub.isChecked()) {
                facility.add(Facility.Bathtub);
            }
            if (balcony.isChecked()) {
                facility.add(Facility.Balcony);
            }
            if (restaurant.isChecked()) {
                facility.add(Facility.Restaurant);
            }
            if (pool.isChecked()) {
                facility.add(Facility.SwimmingPool);
            }
            if (fitness.isChecked()) {
                facility.add(Facility.FitnessCenter);
            }
            String bedType = bed.getSelectedItem().toString();
            String cityStr = city.getSelectedItem().toString();

            BedType bedtype = BedType.valueOf(bedType);
            City city = City.valueOf(cityStr);

            int size = Integer.parseInt(sizeInput.getText().toString());
            int price = Integer.parseInt(priceInput.getText().toString());

            requestRoom(MainActivity.accountLogin.id, nameInput.getText().toString(), size,price, facility, city, addressInput.getText().toString(), bedtype);
        });
    }

    protected Room requestRoom(int id, String name, int size, int price, ArrayList<Facility> facility, City city, String address, BedType bedType) {
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Size: " + size);
        System.out.println("facility: " + facility);
        System.out.println("Address: " + address);
        System.out.println("Bed: " + bedType);
        mApiService.room(id, name, size, price, facility, city, address, bedType).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.toString());
                    Toast.makeText(mContext, "Berhasil buat room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "gagal buat room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}