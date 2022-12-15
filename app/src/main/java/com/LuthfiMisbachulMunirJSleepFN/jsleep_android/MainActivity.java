package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Payment;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Renter;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The MainActivity class is an Android activity that represents the main page for the app.
 *
 * <p>It displays a list of rooms that are available for rent, and allows the user to navigate to different pages of the list
 * or to view the details of a selected room. It also provides options for viewing the user's profile and creating a new room.</p>
 * @author Luthfi Misbachul Munir
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    protected static Account accountLogin;
    /**
     * The user's renter information.
     */
    public static Renter renter;
    BaseApiService mApiService;
    Context mContext;
    Button next, prev, history;
    ListView list;
    List<Room> roomTemp ;
    public static List<String> roomName = new ArrayList<>();
    int numPage;
    TextView letter;
    ImageView filter;

    List<String> nameStr;
    List<Room> roomFix ;

    static BaseApiService mApiServiceStatic;
    int page = 1, pageSize = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = UtilsApi.getApiService();
        mContext = this;
        next = findViewById(R.id.nextButton);
        prev = findViewById(R.id.prevButton);
        letter = findViewById(R.id.LetterPaginate);
        history = findViewById(R.id.historyButton);

        list = findViewById(R.id.listView_Main);
        list.setOnItemClickListener(this::onItemClick);

        filter = findViewById(R.id.filterButton);
        getRoomList(0);

        history.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numPage += 1;
                roomFix = getRoomList(numPage);
            }
        });

        /**
         * button to search a filter
         */
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchRoomActivity.class);
                startActivity(intent);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numPage == 0) {
                    Toast.makeText(mContext, "You are at the first page", Toast.LENGTH_SHORT).show();
                } else {
                    numPage -= 1;
                    roomFix = getRoomList(numPage);
                    Toast.makeText(mContext, "Page " + numPage, Toast.LENGTH_SHORT).show();
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

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        mApiService.getAllRoom(page, 10).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    roomTemp = response.body();
                    nameStr = getName(roomTemp);
                    roomName.addAll(nameStr);
                    System.out.println("name extracted"+roomTemp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, R.layout.list_item, R.id.text_view,nameStr);
                    list = (ListView) findViewById(R.id.listView_Main);
                    list.setAdapter(itemAdapter);
                    Toast.makeText(mContext, "getRoom success", Toast.LENGTH_SHORT).show();
                    letter.setText(String.valueOf(numPage+1));
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
        intent.putExtra("id", id);
        startActivity(intent);
    }
}