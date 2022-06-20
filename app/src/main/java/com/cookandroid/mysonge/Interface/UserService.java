package com.cookandroid.mysonge.Interface;

import com.cookandroid.mysonge.DTO.Register;
import com.cookandroid.mysonge.DTO.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/user")
    Call<Integer> register(@Body Register register);

    @POST("/login")
    Call<Integer> login(@Body UserLogin userLogin);
}
