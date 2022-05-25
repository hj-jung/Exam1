package com.cookandroid.exam.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cookandroid.exam.Activity.ScheduleInfoActivity;
import com.cookandroid.exam.Adapter.TimelineAdapter;
import com.cookandroid.exam.DTO.Schedule;
import com.cookandroid.exam.Interface.ScheduleService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.RetrofitClient;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimelineFragment extends ListFragment {
    TimelineAdapter adapter;
    private int time;
    private String color, title, startH, AMPM;
    private static final String TAG = "DailyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_daily, container, false);
        String[][] strings = new String[25][4];
        adapter = new TimelineAdapter();
        setListAdapter(adapter);

        //오늘 날짜 일정 GET
        ScheduleService scheduleService = RetrofitClient.getClient().create(ScheduleService.class);
        Call<List<Schedule>> call = scheduleService.today();
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Log.d(TAG, "Schedule Response Success");
                List<Schedule> scheduleResponse = response.body();
                for(Schedule schedule : scheduleResponse){
                    color = schedule.getColor();
                    title = schedule.getTitle();
                    String startTime = schedule.getStartHms();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime localTime= LocalTime.parse(startTime, formatter);
                        time = Integer.valueOf(localTime.getHour());
                        if(localTime.getHour()>12){
                            startH = String.valueOf(localTime.getHour()-12);
                            AMPM = "PM";
                        }
                        else{
                            startH = String.valueOf(localTime.getHour());
                            AMPM = "AM";
                        }
                    }
                    strings[time][0] = startH;
                    strings[time][1] = AMPM;
                    strings[time][2] = title;
                    strings[time][3] = color;

                    System.out.println(title+startH+color);
                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

        //해당 시간대에 일치하는 일정 DailyList에 띄우기
        for(int i=1; i<25; i++){
            if(strings[i][0]!=null){
                adapter.addItem(strings[i][0],strings[i][1], strings[i][2],strings[i][3]);
            }
            else {
                if (i > 12) {
                    adapter.addItem(String.valueOf(i - 12), "PM", "", "WHITE");
                } else {
                    adapter.addItem(String.valueOf(i), "AM", "", "WHITE");
                }
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //일정 클릭 이벤트 처리 -> 일정상세페이지로 이동
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(getActivity(), ScheduleInfoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}