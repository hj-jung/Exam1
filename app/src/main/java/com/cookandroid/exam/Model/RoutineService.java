package com.cookandroid.exam.Model;

import com.cookandroid.exam.Util.Routine;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoutineService {

    @GET("/routines/{day}")
    Call<List<Routine>> getRoutine(@Path("day") String post);

    @POST("/routine")
    Call<Routine> addRoutine(@Body Routine routine);

}
