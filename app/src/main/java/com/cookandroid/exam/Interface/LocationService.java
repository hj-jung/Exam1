package com.cookandroid.exam.Interface;

import com.cookandroid.exam.DTO.Location.ResultKeyword;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface LocationService {
    //장소이름으로 검색
    @GET("v2/local/search/keyword.json")
    Call<ResultKeyword> getResultKeyword(
            @Header("Authorization") String key,
            @Query("query") String query,
            @Query("size") int size
    );
}

