package com.cookandroid.exam.Interface;

import com.cookandroid.exam.DTO.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleService {
    //GET방식으로 오늘의 일정 반환 - 날짜를 LocalTime으로 받아 오늘 날짜가 캘린더 일정의 start~end사이에 있으면 반환
    @GET("/calendar/today")
    Call<List<Schedule>> today();

    //GET방식으로 전체 캘린더 일정 조회
    @GET("/calendars")
    Call<List<Schedule>> all(@Query("startYmd")String startYmd);

    //POST방식으로 일정 등록
    @POST("/calendar")
    Call<Schedule> addSchedule(@Body Schedule schedule); //Call객체 선언하여 HTTP요청을 웹서버로 보냄
                                                         //<>안의 자료형으로 JSON데이터를 받음

    //PUT방식으로 일정 수정
    @PUT("/calendar/{id}")
    Call<Schedule> putschedule(@Path("id") int id, @Body Schedule schedule);

    //DELETE방식으로 일정 삭제
    @DELETE("/calendar/{id}")
    Call<Schedule> deleteSchedule(@Path("id") int id);
}
