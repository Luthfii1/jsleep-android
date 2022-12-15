package com.LuthfiMisbachulMunirJSleepFN.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.BaseApiService;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request.UtilsApi;

public class HistoryActivity extends AppCompatActivity {

    Context mContext;
    BaseApiService mApiService;
    Button progressBtn, BookedBtn;
    ListView lvProgress, lvBooked;
    LinearLayout progressLayout, bookedLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        progressBtn = findViewById(R.id.progresButton);
        BookedBtn = findViewById(R.id.bookedButton);
        lvBooked = findViewById(R.id.listView_Booked);
        lvProgress = findViewById(R.id.listView_Progres);
        progressLayout = findViewById(R.id.listProgress);
        bookedLayout = findViewById(R.id.listBooked);

        progressLayout.setVisibility(View.VISIBLE);
        bookedLayout.setVisibility(View.GONE);

        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLayout.setVisibility(View.VISIBLE);
                bookedLayout.setVisibility(View.GONE);
            }
        });

        BookedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLayout.setVisibility(View.GONE);
                bookedLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}