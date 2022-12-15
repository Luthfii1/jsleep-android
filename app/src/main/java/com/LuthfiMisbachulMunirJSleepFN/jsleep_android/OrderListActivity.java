package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Payment;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends AppCompatActivity {
    Context mContext;
    BaseApiService mApiService;
    static BaseApiService mApiServiceStatic;
    List idRoomBuyer = new ArrayList();
    List<Payment> temp ;
    public static List idRoom = new ArrayList<>();
    List<Payment> acc ;
    ListView lv;
    Button next, prev;
    public static Room tempRoom = null;
    int currentPage;
    TextView letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        mApiService= UtilsApi.getApiService();
        mApiServiceStatic= UtilsApi.getApiService();

        mContext=this;
        lv = findViewById(R.id.listView_Order);
        next = findViewById(R.id.nextBtnOrder);
        letter = findViewById(R.id.letterOrder);
        prev = findViewById(R.id.prevOrder);
        lv.setOnItemClickListener(this::onItemClick);
        System.out.println("gap sblm acc");
        currentPage = 0;
        acc = getPaymentList(PaymentActivity.payment.renterId, 0, 10);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1;
//                acc = getPaymentList(PaymentActivity.payment.renterId, currentPage, 10);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage == 0) {
                    Toast.makeText(mContext, "You are at the first page", Toast.LENGTH_SHORT).show();
                } else {
                    currentPage -= 1;
//                    acc = getPaymentList(PaymentActivity.payment.renterId, currentPage, 10);
                    Toast.makeText(mContext, "Page " + currentPage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /**
     Called when an item in the list view is clicked.
     @param l the list view
     @param v the view that was clicked
     @param position the position of the item that was clicked
     @param id the id of the item that was clicked
     */
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent();
        intent.setClass(this, DetailOrderActivity.class);
        DetailOrderActivity.tempPayment = temp.get(position);
        intent.putExtra("position", position);
        // Or / And
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     Retrieves a list of payments for a given account.
     @param accId the id of the account
     @param page the page number of the list to retrieve
     @param pageSize the number of items per page
     @return a list of payments
     */
    protected List<Payment> getPaymentList(int accId, int page, int pageSize){
        mApiService.getOrderForRenter(accId,page, pageSize).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    temp = response.body();
                    idRoomBuyer = getName(temp);
                    idRoom.addAll(idRoomBuyer);
                    System.out.println("name extracted"+temp.toString());
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(mContext, R.layout.list_item, R.id.text_view,idRoomBuyer);
                    lv = (ListView) findViewById(R.id.listView_Order);
                    lv.setAdapter(itemAdapter);
                    System.out.println("display lv");
                    Toast.makeText(mContext, "getRoom success", Toast.LENGTH_SHORT).show();
                    letter.setText(String.valueOf(currentPage+1));
                }
            }
            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, "get room failed", Toast.LENGTH_SHORT).show();
            }


        });
        return null;
    }

//    protected static  Room loadRoom(int id){
//        Room test;
//        mApiServiceStatic.room(id).enqueue(new Callback<Room>() {
//            @Override
//            public void onResponse(Call<Room> call, Response<Room> response) {
//                if (response.isSuccessful()) {
//                    tempRoom = response.body();
//                    System.out.println("Room loaded");
//                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onFailure(Call<Room> call, Throwable t) {
//                t.printStackTrace();
//                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return tempRoom;
//    }

    /**
     Retrieves a list of names from a list of payments.
     @param list<Payment> the list of payments
     @return a list of names
     */
    public List<String> getName(List<Payment> list) {
        ArrayList<String> ret = new ArrayList<String>();
        int i;

        for (i = 0; i < list.size(); i++) {
            ret.add("Buyer " + list.get(i).roomId);
        }

        return ret;
    }
}