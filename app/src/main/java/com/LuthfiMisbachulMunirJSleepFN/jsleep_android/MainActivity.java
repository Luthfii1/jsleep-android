package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    protected static Account accountLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream filepath = null;
        ArrayList<Room> ListRoom = new ArrayList<>();
        ArrayList<String> listId = new ArrayList<>();
        Gson gson = new Gson();
        try {
            filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            Room[] temp = gson.fromJson(reader, Room[].class);
            Collections.addAll(ListRoom, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Room r : ListRoom ) {
            listId.add(r.name);
        }
        ArrayAdapter<String> roomArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listId);
        ListView listView = findViewById(R.id.listView_Main);
        listView.setAdapter(roomArrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent aboutMeIntent = new Intent(MainActivity.this, About_Me.class);
                startActivity(aboutMeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}