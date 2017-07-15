package com.example.choi.cracker.Network;

import com.example.choi.cracker.data.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by choi on 2017. 7. 15..
 */

public interface Retrofit_Interface {
    @GET("/facebook/token")
    Call<User> facebookLogin(@Query("access_token") String token);
}
