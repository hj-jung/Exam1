package com.cookandroid.exam.Interface;

import com.cookandroid.exam.DTO.Routine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoutineService {

    @GET("/routine/today")
    Call<List<Routine>> getRoutine();

    @POST("/routine")
    Call<Routine> addRoutine(@Body Routine routine);

    @DELETE("/routine/{id}")
    Call<Routine> deleteRoutine(@Path("id") int routineId);

}
