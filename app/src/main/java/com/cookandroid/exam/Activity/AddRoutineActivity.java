package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.cookandroid.exam.Model.RetrofitClient;
import com.cookandroid.exam.Model.RoutineService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.Routine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoutineActivity extends Activity {

    private static final String TAG = "AddRoutineActivity";

    private String repeatTime = "00:00 AM ";

    EditText routineName, routineContent;
    ToggleButton sunButton, monButton, tueButton, wedButton, thuButton, friButton, satButton;
    int sun = 0;
    int mon = 0;
    int tue = 0;
    int wed = 0;
    int thu = 0;
    int fri = 0;
    int sat = 0;

    private int routineID;

    private int curHour2;
    private String strCurMinute;
    private int routineHour;
    private String routineMin;
    private String routine_hour;
    private String routine_min;

    private RoutineService routineService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 팝업창 검정색 배경 없애기
        getWindow().setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //레이아웃 xml 지정
        setContentView(R.layout.routine_add_dialog);

        //액티비티 >> 팝업창 형태 크기 및 위치 커스텀
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int pointWidth = point.x; //가로
        int pointHeight = point.y; //세로

        int width = (int) (pointWidth * 1.0); //Display 가로 사이즈 100%
        int height = (int) (pointHeight * 0.9); //Display 세로 사이즈 90%

        getWindow().getAttributes().width = width; //가로 크기
        getWindow().getAttributes().height = height; //세로 크기
        getWindow().getAttributes().gravity = Gravity.BOTTOM; //위치 아래로 설정

        Button cancelbtn = (Button) findViewById(R.id.routine_add_cancle);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        routineName = (EditText) findViewById(R.id.routine_name);

        //반복 요일 설정
        sunButton = (ToggleButton) findViewById(R.id.repeat_sun);
        monButton = (ToggleButton) findViewById(R.id.repeat_mon);
        tueButton = (ToggleButton) findViewById(R.id.repeat_tue);
        wedButton = (ToggleButton) findViewById(R.id.repeat_wed);
        thuButton = (ToggleButton) findViewById(R.id.repeat_thu);
        friButton = (ToggleButton) findViewById(R.id.repeat_fri);
        satButton = (ToggleButton) findViewById(R.id.repeat_sat);

        sunButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) sun = 1;
            }
        });

        monButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) mon = 2;
            }
        });

        tueButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) tue = 3;
            }
        });

        wedButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) wed = 4;
            }
        });

        thuButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) thu = 5;
            }
        });

        friButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) fri = 6;
            }
        });

        satButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) sat = 7;
            }
        });


        //시간 설정
        TextView routineTime = findViewById(R.id.routine_time);

        //현재 시간 정보 불러오기
        long now_Time = System.currentTimeMillis();
        Date date = new Date(now_Time);

        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");

        String strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);

        int curHour = Integer.parseInt(strCurHour);
        int curMinute = Integer.parseInt(strCurMinute);
        String curAmPm;

        if (curHour < 12) {
            curAmPm = "AM";
            curHour2 = curHour;
        }
        else {
            curHour2 = curHour - 12;
            curAmPm = "PM";
        }

        routine_hour = String.format("%02d", curHour2);
        routine_min = String.format("%02d", curMinute);

        routineTime.setText(routine_hour + ":" + routine_min + " " + curAmPm);

        //timepicker
        routineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(AddRoutineActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        routineTime.setTextSize(20);
                        routineHour = hourOfDay;
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                            routineHour = hourOfDay - 12;
                        }

                        routine_hour = String.format("%02d", hourOfDay);
                        routine_min = String.format("%02d", minute);
                        String routine_Hour = String.format("%02d", routineHour);

                        routineTime.setText(routine_hour + ":" + routine_min + " " + AM_PM);
                    }
                }, curHour, curMinute, false);
                dialog.setTitle("Alert Time");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        routineService = RetrofitClient.getClient().create(RoutineService.class);

        Button savebtn = (Button) findViewById(R.id.routine_add_add);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                addRoutine();
                finish();
            }
        });

    }

    private void addRoutine() {
        routineName = (EditText) findViewById(R.id.routine_name);
        routineContent = (EditText) findViewById(R.id.routine_context);
        String routine_name = routineName.getText().toString();
        String routine_content = routineContent.getText().toString();
        String routine_ttime = routine_hour.concat(":"+routine_min);
        Routine routine = new Routine(false, routine_content, fri, mon, routine_name, routine_ttime, sat, sun, thu, tue, wed);

        Call<Routine> call = routineService.addRoutine(routine);
        call.enqueue(new Callback<Routine>() {
            @Override
            public void onResponse(Call<Routine> call, Response<Routine> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Routine routineResponse = response.body();
                routineID = Integer.parseInt(response.toString());
                System.out.println("routineID = " + routineID);
                System.out.println("routineReponse = " + routineResponse);
            }

            @Override
            public void onFailure(Call<Routine> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}