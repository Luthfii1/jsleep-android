package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Facility;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Invoice;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Payment;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends AppCompatActivity {
    public static Payment tempPayment;
    public static Room checkoutRoom = null;
    TextView roomName, roomPrice, roomSize, roomAddress, roomBedtype, fromDate, toDate, totalPayment, status, rateText;
    CheckBox ac, refrig, wifi, bathub, balcony, restaurant, pool, fitness;
    Button order, cancel, rate;
    static BaseApiService mApiServiceStatic;
    LinearLayout checkout, ratingView;
    CardView cardRating;
    Spinner ratingSpinner;

    BaseApiService mApiService;
    Context mContext;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        mContext = this;
        mApiServiceStatic = UtilsApi.getApiService();
        mApiService = UtilsApi.getApiService();
        loadRoom(tempPayment.roomId);
        roomName = (TextView) findViewById(R.id.name_Detail);
        roomPrice = (TextView) findViewById(R.id.price_Detail);
        roomSize = (TextView) findViewById(R.id.size_Detail);
        roomAddress = (TextView) findViewById(R.id.address_detail);
        roomBedtype = (TextView) findViewById(R.id.bedType_detail);
//        ac = findViewById(R.id.acCheckout);
//        refrig = findViewById(R.id.refrigeratorCheckout);
//        wifi = findViewById(R.id.wifiCheckout);
//        bathub = findViewById(R.id.bathubCheckout);
//        balcony = findViewById(R.id.balconyCheckout);
//        restaurant = findViewById(R.id.restaurantCheckout);
//        pool = findViewById(R.id.poolCheckout);
//        fitness = findViewById(R.id.fitnessCheckout);
        order = findViewById(R.id.acceptPayment);
        cancel = findViewById(R.id.cancelPayment);
        fromDate = findViewById(R.id.from_Date);
        toDate = findViewById(R.id.to_Date);
        totalPayment = findViewById(R.id.income);
//        status = findViewById(R.id.status);
//        checkout = findViewById(R.id.cancelAccept);
//        cardRating = findViewById(R.id.cvRating);
//        ratingSpinner = findViewById(R.id.spRating);
//        rate = findViewById(R.id.rateButton);
//        rateText = findViewById(R.id.rateText);
//        ratingView = findViewById(R.id.ratingView);
//        back = findViewById(R.id.backButton);


        if(!tempPayment.status.equals(Invoice.PaymentStatus.WAITING)){
            order.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }

        try {
            roomName.setText(checkoutRoom.name);
            roomPrice.setText(String.valueOf(checkoutRoom.price.price));
            roomSize.setText(String.valueOf(checkoutRoom.size));
            roomAddress.setText(checkoutRoom.address);
            roomBedtype.setText(checkoutRoom.bedType.toString());
            fromDate.setText(tempPayment.from.toString().substring(0,10));
            toDate.setText(tempPayment.to.toString().substring(0,10));
            status.setText(tempPayment.status.toString());
            rateText.setText(tempPayment.rating.toString());

            long diffInMilliseconds = tempPayment.to.getTime() - tempPayment.from.getTime();
            long diffInDays = diffInMilliseconds / (1000 * 60 * 60 * 24);
            totalPayment.setText(String.valueOf(diffInDays * checkoutRoom.price.price));

            for (int i = 0; i < checkoutRoom.facility.size(); i++) {
                if (checkoutRoom.facility.get(i).equals(Facility.AC)) {
                    ac.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Refrigerator)) {
                    refrig.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.WiFi)) {
                    wifi.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Bathtub)) {
                    bathub.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Balcony)) {
                    balcony.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.Restaurant)) {
                    restaurant.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.SwimmingPool)) {
                    pool.setChecked(true);
                } else if (checkoutRoom.facility.get(i).equals(Facility.FitnessCenter)) {
                    fitness.setChecked(true);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("data isnt loaded");
            Intent move3 = new Intent(DetailOrderActivity.this, DetailOrderActivity.class);
            startActivity(move3);
        }

        if (tempPayment.status.equals(Invoice.PaymentStatus.SUCCESS)){
            if(tempPayment.rating.equals(Invoice.RoomRating.GOOD)){
                cardRating.setVisibility(View.GONE);
            }
            else if(tempPayment.rating.equals(Invoice.RoomRating.BAD)){
                cardRating.setVisibility(View.GONE);
            }
            else if(tempPayment.rating.equals(Invoice.RoomRating.NEUTRAL)){
                cardRating.setVisibility(View.GONE);
            }
            else if(tempPayment.rating.equals(Invoice.RoomRating.NONE)){
                cardRating.setVisibility(View.VISIBLE);
                ratingSpinner.setAdapter(new ArrayAdapter<Invoice.RoomRating>(this, android.R.layout.simple_spinner_item, Invoice.RoomRating.values()));
            }
            checkout.setVisibility(View.GONE);
//            rate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String rateStr = ratingSpinner.getSelectedItem().toString();
//                    System.out.println(rateStr);
//                    requestRating(tempPayment.id, rateStr);
//                    Intent startIntent = getIntent();
//                    finish();
//                    startActivity(startIntent);
//                }
//            });
        }


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestAccept(tempPayment.id);
                Intent move3 = new Intent(DetailOrderActivity.this, OrderListActivity.class);
                startActivity(move3);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelDialog();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(DetailOrderActivity.this, OrderListActivity.class);
                startActivity(move);
            }
        });
    }

    protected static  Room loadRoom(int id){
        mApiServiceStatic.room(id).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    DetailOrderActivity.checkoutRoom = response.body();
                    System.out.println("Room loaded");
                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return DetailOrderActivity.checkoutRoom;
    }


    protected Boolean requestCancel(int id){
        mApiServiceStatic.cancelPaymentRequest(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    System.out.println("Payment cancelled");
                    Toast.makeText(mContext, "Payment cancelled", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(mContext, "getAccount success", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    protected Boolean requestAccept(int id){
        mApiServiceStatic.acceptPaymentRequest(id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    System.out.println("Payment Accepted");
                    Toast.makeText(mContext, "Payment Accepted", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

//    protected Boolean requestRating(int id, String rating){
//        mApiService.rating(id, rating).enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                if (response.isSuccessful()) {
//                    rateText.setText(rating);
//                    loadRoom(checkoutRoom.id);
//                    System.out.println("Room rated");
//                    Toast.makeText(mContext, "Room rated", Toast.LENGTH_SHORT).show();
//                    Intent move = new Intent(DetailRoomActivity.this, OrderListActivity.class);
//                    startActivity(move);
//                }
//            }
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//                t.printStackTrace();
//                Toast.makeText(mContext, "get account failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//        return true;
//    }


    private void cancelDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title dialog
        alertDialogBuilder.setTitle("Are you sure you want to cancel the order?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Press yes to cancel")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        requestCancel(tempPayment.id);
                        Intent move = new Intent(DetailOrderActivity.this, About_Me.class);
                        startActivity(move);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}