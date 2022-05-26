package com.cookandroid.exam.Interface;

import com.cookandroid.exam.DTO.Routine;
import com.cookandroid.exam.Util.RoutineAchive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoutineService {

    @GET("/routine/today")
    Call<List<Routine>> getRoutine();

    @POST("/routine")
    Call<Routine> addRoutine(@Body Routine routine);

    @DELETE("/routine/{id}")
    Call<Routine> deleteRoutine(@Path("id") int routineId);

    @PUT("/routine/achieve/{id}")
    Call<Routine> checkRoutine(@Path("id") int routineId, @Body RoutineAchive routineAchive);

    @GET("/routine/achieve")
    Call<String> getAchieve();
}
