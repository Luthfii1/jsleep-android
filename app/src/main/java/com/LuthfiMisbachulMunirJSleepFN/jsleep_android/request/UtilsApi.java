package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.R;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;

public class UtilsApi {

    public static final String BASE_URL_API = "http://192.168.100.65:8080/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}