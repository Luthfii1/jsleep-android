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
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String name;
    static ArrayList<Room> roomList = new ArrayList<Room>();
    List<String> stringName;
    List<Room> roomTemp ;
    List<Room> roomFix ;
    ListView list;
    BaseApiService mApiService;
    Context mContext;
    Button next, prev;
    int numPage;
    protected static Account accountLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);
        list = findViewById(R.id.listView_Main);
        roomFix = getRoomList(10,10);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(roomTemp.size()>numPage){
                    numPage=1;
                    return;
                }
                numPage++;
                try {
                    roomFix = getRoomList(numPage-1, 1);
                    Toast.makeText(mContext, "page "+numPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numPage<=1){
                    numPage=1;
                    Toast.makeText(mContext, "Now your on the first page", Toast.LENGTH_SHORT).show();
                    return;
                }
                numPage--;
                try {
                    roomFix = getRoomList(numPage-1, 1);  //return null
                    Toast.makeText(mContext, "page "+numPage, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent move = new Intent(MainActivity.this, About_Me.class);
                startActivity(move);
                return true;
            case R.id.add_button:
                Intent move2 = new Intent(MainActivity.this, CreateRoom.class);
                startActivity(move2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem register = menu.findItem(R.id.add_button);
        if(accountLogin.renter == null){
            register.setVisible(false);
        }
        else {
            register.setVisible(true);
        }
        return true;
    }

    protected List<Room> getRoomList(int page, int pageSize) {
        mApiService.getAllRoom(page, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    roomTemp = response.body();
                    stringName = getName(roomTemp);
                    System.out.println("name extracted "+roomTemp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,stringName);
                    ListView listView = (ListView) findViewById(R.id.listView_Main);
                    listView.setAdapter(itemAdapter);
                    Toast.makeText(mContext, "Success to get a room", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "Failed to get a room", Toast.LENGTH_SHORT).show();
            }

        });
        return null;
    }

    public static ArrayList<String> getName(List<Room> list) {
        ArrayList<String> ret = new ArrayList<String>();
        int i;
        for (i = 0; i < list.size(); i++) {
            ret.add(list.get(i).name);
        }
        return ret;
    }
}