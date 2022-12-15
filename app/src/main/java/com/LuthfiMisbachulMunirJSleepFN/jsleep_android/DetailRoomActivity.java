package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.City;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Facility;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {
    TextView roomName, roomPrice, roomSize, roomAddress, roomBedtype, roomCity;
    CheckBox ac, refrig, wifi, bathub, balcony, restaurant, pool, fitness;
    Button order, cancel;

    public static Room tempRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        roomName = findViewById(R.id.name_Detail);
        roomPrice = findViewById(R.id.price_Detail);
        roomSize = findViewById(R.id.size_Detail);
        roomAddress = findViewById(R.id.address_detail);
        roomBedtype = findViewById(R.id.bedType_detail);

        order = findViewById(R.id.booked_Detail);
        cancel = findViewById(R.id.cancel_Detail);

        ac = findViewById(R.id.AC_Facility);
        refrig = findViewById(R.id.Refrigator_Fac);
        wifi = findViewById(R.id.Wifi_Fac);
        bathub = findViewById(R.id.Bathub_fac);
        balcony = findViewById(R.id.balcony_fac);
        restaurant = findViewById(R.id.resto_fac);
        pool = findViewById(R.id.pool_fac);
        fitness = findViewById(R.id.fitness_fac);
        roomCity = findViewById(R.id.city_Detail);

        roomName.setText(tempRoom.name);
        roomPrice.setText(String.valueOf(tempRoom.price.price));
        roomCity.setText(tempRoom.city.toString());
        roomSize.setText(String.valueOf(tempRoom.size));
        roomAddress.setText(tempRoom.address);
        roomBedtype.setText(tempRoom.bedType.toString());

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailRoomActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(DetailRoomActivity.this, ChooseDate.class);
                startActivity(move);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailRoomActivity.this, "Failed to book a room", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(DetailRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });

        for (int i = 0; i < tempRoom.facility.size(); i++) {
            if (tempRoom.facility.get(i).equals(Facility.AC)) {
                ac.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Refrigerator)) {
                refrig.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.WiFi)) {
                wifi.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Bathtub)) {
                bathub.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Balcony)) {
                balcony.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.Restaurant)) {
                restaurant.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                pool.setChecked(true);
            } else if (tempRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                fitness.setChecked(true);
            }
        }
    }
}