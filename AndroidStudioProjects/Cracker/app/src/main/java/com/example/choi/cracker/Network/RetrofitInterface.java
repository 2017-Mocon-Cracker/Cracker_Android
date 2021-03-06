package com.example.choi.cracker.Network;

import com.example.choi.cracker.Data.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 7. 15..
 */

public interface RetrofitInterface {
    @GET("/facebook/token")
    Call<User> facebookLogin(@Query("access_token") String token);

    @POST("/card/add")
    Call<User> cardInfo(@Query("param") String json);

    @POST("/card/del")
    Call<User> carddel(@Query("delete") String ID);
}