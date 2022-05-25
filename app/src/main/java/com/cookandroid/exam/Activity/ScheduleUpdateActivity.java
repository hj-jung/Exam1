package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.cookandroid.exam.DTO.Schedule;
import com.cookandroid.exam.Fragment.MainFragment;
import com.cookandroid.exam.Interface.ScheduleService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleUpdateActivity extends FragmentActivity {

    EditText scheduleName, scheduleLocation, scheduleContext;
    Button col1, col2, col3, col4, col5, col6;
    private ScheduleService scheduleService;
    private String color, startY, endY;
    private LocalTime startHms, endHms;

    private static final String TAG = "ScheduleUpdateActivity";

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
                DatePickerDialog dialog = new DatePickerDialog(ScheduleUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        eventStartDay.setText(year + "." + (monthOfYear+1) + "." + dayOfMonth);
                        String start_year = String.valueOf(year);
                        String start_month = String.valueOf(monthOfYear+1);
                        if(Integer.valueOf(start_month)<10) start_month="0"+start_month;
                        String start_day = String.valueOf(dayOfMonth);
                        startY = start_year.concat("-"+start_month+"-"+start_day);
                    }
                }, Integer.parseInt(strCurYear), Integer.parseInt(strCurMonth)-1, Integer.parseInt(strCurDay));
                dialog.show();
            }
        });

        //datepicker EndDay
        eventEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(ScheduleUpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        eventEndDay.setText(year + "." + (monthOfYear+1) + "." + dayOfMonth);
                        String end_year = String.valueOf(year);
                        String end_month = String.valueOf(monthOfYear+1);
                        if(Integer.valueOf(end_month)<10) end_month="0"+end_month;
                        String end_day = String.valueOf(dayOfMonth);
                        endY = end_year.concat("-"+end_month+"-"+end_day);
                    }
                }, Integer.parseInt(strCurYear), Integer.parseInt(strCurMonth)-1, Integer.parseInt(strCurDay));
                dialog.show();
            }
        });


        //timepicker startTime
        eventStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(ScheduleUpdateActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
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

                        startHms = LocalTime.of(hourOfDay, minute);

                        String start_hour = String.valueOf(hourOfDay);
                        String start_min = String.valueOf(minute);

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
                    @RequiresApi(api = Build.VERSION_CODES.O)
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

                        endHms = LocalTime.of(hourOfDay, minute);

                        String end_hour = String.valueOf(hourOfDay);
                        String end_min = String.valueOf(minute);

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

        //color string값으로 넣어주기
        col1 = (Button) findViewById(R.id.color1);
        col2 = (Button) findViewById(R.id.color2);
        col3 = (Button) findViewById(R.id.color3);
        col4 = (Button) findViewById(R.id.color4);
        col5 = (Button) findViewById(R.id.color5);
        col6 = (Button) findViewById(R.id.color6);

        col1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "RED";
            }
        });
        col2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "ORANGE";
            }
        });
        col3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "GREEN";
            }
        });
        col4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "BLUE";
            }
        });
        col5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "PURPLE";
            }
        });
        col6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "BLACK";
            }
        });

        //Retrofit 인스턴스 생성
        scheduleService = RetrofitClient.getClient().create(ScheduleService.class);

        Button add= (Button) findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                addSchedule();
                finish();
            }
        });
    }

    //일정 POST
    private void addSchedule(){
        String schedule_name, schedule_location, schedule_context;
        scheduleName = (EditText) findViewById(R.id.schedule_name);
        scheduleLocation = (EditText) findViewById(R.id.location);
        scheduleContext = (EditText) findViewById(R.id.context);

        schedule_name = scheduleName.getText().toString();
        schedule_location = scheduleLocation.getText().toString();
        schedule_context = scheduleContext.getText().toString();


        Schedule schedule = new Schedule(color, schedule_context, endHms.toString() , endY, schedule_location, startHms.toString(), startY, schedule_name);

        Call<Schedule> call = scheduleService.addSchedule(schedule);
        call.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if(!response.isSuccessful()){   //일정 등록 실패
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Toast.makeText(getApplicationContext(), "일정등록성공", Toast.LENGTH_SHORT).show();
                Schedule scheduleResponse = response.body();
                System.out.println(scheduleResponse.getTitle());
            }

            @Override
            public void onFailure(Call<Schedule> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
