package com.cookandroid.mysonge.Interface;

import com.cookandroid.mysonge.DTO.Routine;
import com.cookandroid.mysonge.Util.RoutineAchive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoutineService {

    @GET("/routine/today/{user}")
    Call<List<Routine>> getRoutine(@Path("user") int user);

    @POST("/routine")
    Call<Routine> addRoutine(@Body Routine routine);

    @DELETE("/routine/{id}")
    Call<Routine> deleteRoutine(@Path("id") int routineId);

    @PUT("/routine/achieve/{id}")
    Call<Routine> checkRoutine(@Path("id") int routineId, @Body RoutineAchive routineAchive);

    @GET("/routine/achieve/{user}")
    Call<String> getAchieve(@Path("user") int user);
}
