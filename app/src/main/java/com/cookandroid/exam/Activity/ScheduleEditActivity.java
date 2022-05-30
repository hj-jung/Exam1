package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.exam.DTO.Schedule;
import com.cookandroid.exam.Interface.ScheduleService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleEditActivity extends AppCompatActivity {

    EditText scheduleName, scheduleLocation, scheduleContext;
    Button col1, col2, col3, col4, col5, col6;
    Button put, del;
    private ScheduleService scheduleService;
    private String color, startY, endY, startH, endH;
    private int calendarID, pos;
    private static final String TAG = "SchedulePutActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleedit);

        Intent intent = getIntent();
        pos = intent.getIntExtra("position",-1);

        scheduleName = (EditText) findViewById(R.id.schedule_edit_name);
        scheduleLocation = (EditText) findViewById(R.id.edit_location);
        scheduleContext = (EditText) findViewById(R.id.edit_context);

        //Retrofit 인스턴스 생성
        scheduleService = RetrofitClient.getClient().create(ScheduleService.class);

        //기존 일정 GET
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
                int i = 0;
                for(Schedule schedule : scheduleResponse){
                    if (i == pos) {
                        calendarID = schedule.getId();
                        color = schedule.getColor();
                        scheduleName.setText(schedule.getTitle());
                        scheduleLocation.setText(schedule.getLocation());
                        scheduleContext.setText(schedule.getContext());
                        System.out.println(schedule.getTitle());
                    }
                    i++;
                }
            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });


        put = (Button) findViewById(R.id.btn_put);
        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                putschedule();
                finish();
            }
        });

        del = (Button) findViewById(R.id.btn_delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                delSchedule();
                finish();
            }
        });

    }

    //일정 PUT
    public void putschedule() {



    }

    //일정 DELETE
    public void delSchedule(){
        Call<Schedule> call = scheduleService.deleteSchedule(calendarID);
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Toast.makeText(getApplicationContext(), "일정삭제완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
