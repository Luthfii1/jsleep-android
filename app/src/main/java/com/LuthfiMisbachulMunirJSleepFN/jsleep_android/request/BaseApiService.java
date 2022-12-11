package com.LuthfiMisbachulMunirJSleepFN.jsleep_android.request;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Account;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.BedType;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.City;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Facility;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Payment;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Renter;
import com.LuthfiMisbachulMunirJSleepFN.jsleep_android.model.Room;

import java.util.ArrayList;
import java.util.List;

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

    @POST("account/{id}/topUp")
    Call<Boolean> topUpRequest (
            @Path("id") int id,
            @Query("balance") double balance
    );

    @POST("account/register")
    Call<Account> register (@Query("name") String Name, @Query("email") String Email, @Query("password") String Password);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom (@Query("page") int page, @Query("pageSize") int pageSize);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                      @Query("username") String username,
                                      @Query("address") String address,
                                      @Query("phoneNumber") String phoneNumber);

    @POST("room/create")
    Call<Room> room(@Query("accountId") int accountId,
                    @Query("name") String name,
                    @Query("size") int size,
                    @Query("price") int price,
                    @Query("facility") ArrayList<Facility> facility,
                    @Query("city") City city,
                    @Query("address") String address,
                    @Query("bedType") BedType bedType);

    @POST("payment/create")
    Call<Payment> createBookingRequest (
            @Query("buyerId") int buyerId,
            @Query("renterId") int renterId,
            @Query("roomId") int roomId,
            @Query("from") String from,
            @Query("to") String to
    );

    @POST("payment/create")
    Call<Payment> createPayment(@Query("buyerId") int buyerId,
                                @Query("renterId") int renterId,
                                @Query("roomId") int roomId,
                                @Query("from") String from,
                                @Query("to") String to);

    @GET("payment/getOrderForRenter")
    Call<List<Payment>> getOrderForRenter(@Path("id") int id,@Query("page") int page,@Query("pageSize") int pageSize);


    @GET("room/{id}")
    Call<Room> room (@Path("id") int id);

    @GET("payment/getAll/{id}")
    Call<List<Payment>> getRoomByRenter(@Path("id") int renterId,@Query("page") int page, @Query("pageSize") int pageSize);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancelPaymentRequest (@Path("id") int id);

    @POST("payment/{id}/accept")
    Call<Boolean> acceptPaymentRequest (@Path("id") int id);

    @GET("room/collectByName")
    Call<List<Room>> collectByName(@Query("name") String name);

    @GET("room/collectByPrice")
    Call<List<Room>> collectByPrice(@Query("min") int min,@Query("max") int max);

    @GET("room/filterByCity")
    Call<List<Room>> collectByCity(@Query("city") City city);
}