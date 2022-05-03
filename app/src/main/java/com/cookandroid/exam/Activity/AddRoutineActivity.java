package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;

import com.cookandroid.exam.R;

public class AddRoutineActivity extends Activity {

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

        TextView routineTime = findViewById(R.id.routine_time);

        //현재 시간 정보 불러오기
        long now_Time = System.currentTimeMillis();
        Date date = new Date(now_Time);

        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");

        String strCurHour = CurHourFormat.format(date);
        String strCurMinute = CurMinuteFormat.format(date);

        int curHour = Integer.parseInt(strCurHour);
        int curHour2;
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
        if (curHour2 < 10) {
            if (curMinute < 10) {
                routineTime.setText("0" + curHour2 + ":" + "0" + curMinute + " " + curAmPm);
            }
            else {
                routineTime.setText("0" + curHour2 + ":" + curMinute + " " + curAmPm);
            }
        }
        else {
            if (curMinute < 10) {
                routineTime.setText(curHour2 + ":" + "0" + curMinute + " " + curAmPm);
            }
            else {
                routineTime.setText(curHour2 + ":" + curMinute + " " + curAmPm);
            }
        }

        //timepicker
        routineTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(AddRoutineActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        routineTime.setTextSize(20);
                        int routineHour = hourOfDay;
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                            routineHour = hourOfDay - 12;
                        }
                        if (routineHour < 10) {
                            if (minute < 10) {
                                routineTime.setText("0" + routineHour + ":" + "0" + minute + " " + AM_PM);
                            }
                            else {
                                routineTime.setText("0" + routineHour + ":" + minute + " " + AM_PM);
                            }
                        }
                        else {
                            if (minute < 10) {
                                routineTime.setText(routineHour + ":" + "0" + minute + " " + AM_PM);
                            }
                            else {
                                routineTime.setText(routineHour + ":" + minute + " " + AM_PM);
                            }
                        }
                    }
                }, curHour, curMinute, false);
                dialog.setTitle("Alert Time");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });
    }
}