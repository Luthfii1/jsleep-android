package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request;

import android.content.Context;
import android.widget.EditText;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient (String baseUrl){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}