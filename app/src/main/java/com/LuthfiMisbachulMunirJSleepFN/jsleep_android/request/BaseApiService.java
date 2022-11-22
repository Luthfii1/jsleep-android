package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String mail, @Query("password") String Password);

    @POST("account/register")
    Call<Account> register (@Query("name") String Name, @Query("email") String Email, @Query("password") String Password);
}