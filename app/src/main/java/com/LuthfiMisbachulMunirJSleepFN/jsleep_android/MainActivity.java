package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Payment;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String name;
    public static List<Room> getRoom;
    List<Room> roomTemp ;
    List<String> nameStr;
    List<Room> roomFix ;
    public static Payment payment;
    ListView list;
    BaseApiService mApiService;
    static BaseApiService mApiServiceStatic;
    Context mContext;
    Button next, prev;
    int numPage;
    int page = 1, pageSize = 15;
    protected static Account accountLogin;
    EditText letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);
        list = findViewById(R.id.listView_Main);
        list.setOnItemClickListener(this::onItemClick);

        getRoomList(0);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numPage += 1;
                roomFix = getRoomList(numPage);
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
                    roomFix = getRoomList(numPage);  //return null
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
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent move = new Intent(MainActivity.this, About_Me.class);
                startActivity(move);
                return true;
            case R.id.add_button:
                Intent move2 = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(move2);
                return true;
            case R.id.home:
                Intent move3 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(move3);
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

    protected List<Room> getRoomList(int page) {
        //System.out.println(pageSize);
        mApiService.getAllRoom(page, 10).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    roomTemp = response.body();
                    nameStr = getName(roomTemp);
                    System.out.println("name extracted"+roomTemp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,nameStr);
                    list = (ListView) findViewById(R.id.listView_Main);
                    list.setAdapter(itemAdapter);
                    Toast.makeText(mContext, "getRoom success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected static Account reloadAccount(int id){
        mApiServiceStatic.getAccount(id).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    MainActivity.accountLogin = response.body();
                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
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

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, DetailRoomActivity.class);
        DetailRoomActivity.tempRoom = roomTemp.get(position);
        intent.putExtra("position", position);
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }
}