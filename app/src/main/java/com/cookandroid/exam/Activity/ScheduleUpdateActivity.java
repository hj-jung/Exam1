package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.cookandroid.exam.Fragment.MainFragment;
import com.cookandroid.exam.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleUpdateActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleupdate);

        //이전 화면 돌아가기
        Button cancel = (Button) findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //MainFragment로 돌아가기기
        Button add= (Button) findViewById(R.id.btn_add);

        TextView eventStartTime = findViewById(R.id.event_start_time);
        TextView eventEndTime = findViewById(R.id.event_end_time);
        TextView eventStartDay = findViewById(R.id.event_start_day);
        TextView eventEndDay = findViewById(R.id.event_end_day);

        //현재 시간 정보 불러오기
        long now_Time = System.currentTimeMillis();
        Date date = new Date(now_Time);

        SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat curDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");

        String strCurYear = curYearFormat.format(date);
        String strCurMonth = curMonthFormat.format(date);
        String strCurDay = curDayFormat.format(date);
        String strCurHour = CurHourFormat.format(date);
        String strCurMinute = CurMinuteFormat.format(date);

        eventStartDay.setText(strCurYear + "." + strCurMonth + "." + strCurDay + ".");
        eventEndDay.setText(strCurYear + "." + strCurMonth + "." + strCurDay + ".");

        int curHour = Integer.parseInt(strCurHour);
        int startHour, endHour;
        int curMinute = Integer.parseInt(strCurMinute);
        String curAmPm;

        if (curHour < 12) {
            curAmPm = "AM";
            startHour = curHour;
        }
        else {
            startHour = curHour - 12;
            curAmPm = "PM";
        }
        endHour = startHour + 1;
        if (startHour < 10) {
            if (curMinute < 10) {
                eventStartTime.setText("0" + startHour + ":" + "0" + curMinute + " " + curAmPm);
                eventEndTime.setText("0" + endHour + ":" + "0" + curMinute + " " + curAmPm);
            }
            else {
                eventStartTime.setText("0" + startHour + ":" + curMinute + " " + curAmPm);
                eventEndTime.setText("0" + endHour + ":" + curMinute + " " + curAmPm);
            }
        }
        else {
            if (curMinute < 10) {
                eventStartTime.setText(startHour + ":" + "0" + curMinute + " " + curAmPm);
                eventEndTime.setText(endHour + ":" + "0" + curMinute + " " + curAmPm);
            }
            else {
                eventStartTime.setText(startHour + ":" + curMinute + " " + curAmPm);
                eventEndTime.setText(endHour + ":" + curMinute + " " + curAmPm);
            }
        }

        //datepicker StartDay
        eventStartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(ScheduleUpdateActivity.this, R.style.MyDatePickerStyle, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        eventStartDay.setText(year + "." + monthOfYear + "." + dayOfMonth);
                    }
                }, Integer.parseInt(strCurYear), Integer.parseInt(strCurMonth), Integer.parseInt(strCurDay));
                dialog.show();
            }
        });

        //datepicker endDay
        eventEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(ScheduleUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        eventEndDay.setText(year + "." + monthOfYear + "." + dayOfMonth);
                    }
                }, Integer.parseInt(strCurYear), Integer.parseInt(strCurMonth), Integer.parseInt(strCurDay));
                dialog.show();
            }
        });


        //timepicker startTime
        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(ScheduleUpdateActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eventStartTime.setTextSize(12);
                        int startsHour = hourOfDay;
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                            startsHour = hourOfDay - 12;
                        }
                        if (startsHour < 10) {
                            if (minute < 10) {
                                eventStartTime.setText("0" + startsHour + ":" + "0" + minute + " " + AM_PM);
                            }
                            else {
                                eventStartTime.setText("0" + startsHour + ":" + minute + " " + AM_PM);
                            }
                        }
                        else {
                            if (minute < 10) {
                                eventStartTime.setText(startsHour + ":" + "0" + minute + " " + AM_PM);
                            }
                            else {
                                eventStartTime.setText(startsHour + ":" + minute + " " + AM_PM);
                            }
                        }
                    }
                }, curHour, curMinute, false);
                dialog.setTitle("Alert Time");
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });

        //timepicker endTime
        eventEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(ScheduleUpdateActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        eventEndTime.setTextSize(12);
                        int endsHour = hourOfDay;
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                            endsHour = hourOfDay - 12;
                        }
                        if (endsHour < 10) {
                            if (minute < 10) {
                                eventEndTime.setText("0" + endsHour + ":" + "0" + minute + " " + AM_PM);
                            }
                            else {
                                eventEndTime.setText("0" + endsHour + ":" + minute + " " + AM_PM);
                            }
                        }
                        else {
                            if (minute < 10) {
                                eventEndTime.setText(endsHour + ":" + "0" + minute + " " + AM_PM);
                            }
                            else {
                                eventEndTime.setText(endsHour + ":" + minute + " " + AM_PM);
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
