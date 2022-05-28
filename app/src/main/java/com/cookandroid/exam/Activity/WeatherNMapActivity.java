package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.exam.DTO.Dust.PmInfo;
import com.cookandroid.exam.DTO.Weather.Item;
import com.cookandroid.exam.DTO.Weather.Result;
import com.cookandroid.exam.Interface.DustService;
import com.cookandroid.exam.Interface.WeatherService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.DustRetrofitClient;
import com.cookandroid.exam.Retrofit.WeatherRetrofitClient;
import com.cookandroid.exam.Util.ScheduleData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherNMapActivity extends Activity {

    private static final String TAG = "DetailPageActivity";

    private TextView tvDay, tvEventName, tvEventTime, tvTmp, tvRain, tvWind, tvDust;
    private ImageView weatherImg;
    private ImageButton backButton;

    private String MyKey = "3QkdRZx/R+OBMH+5QoKu7iRbyDkdjaO0nMixw6RktnNL74/9rWajdRkCmtRfYxxYrWv8OABBzFaEY5h6WqwJFA==";
    private String DustKey = "+WSXT9nqJHijeDm0+DfSdKPLPcMLKnfHaJ6XU7N2hq0VkI3x+NM7Yc4Bgbkbok08RCFQEIgd0W/LpiVOg2j3Ow==";

    private WeatherService weatherService;
    private List<Item> weatherItemList = new ArrayList<>();

    private DustService dustService;
    private List<com.cookandroid.exam.DTO.Dust.Item> dustItemList = new ArrayList<>();

    private String rainPercent;
    private String temperature;
    private String windSpeed;
    private String weatherCode;

    private String dustValue, dustGrade;

    private ArrayList<ScheduleData> list = new ArrayList<>();
    ScheduleData data;
    private int pos;
    private String base_date, base_time, dust_daytime, strMonth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            list = intent.getParcelableArrayListExtra("scheduleList");
            pos = intent.getIntExtra("pos", -1);
            pos = pos + 1;
        }
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //레이아웃 xml 지정
        setContentView(R.layout.activity_detail_page);

        for (ScheduleData scheduleData : list) {
            if (Integer.parseInt(scheduleData.getStartH()) == pos) {
                data = scheduleData;
                base_time = String.format("%02d",pos);
            }
        }

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat curDayFormat = new SimpleDateFormat("dd");
        String strCurMonth = curMonthFormat.format(date);
        String strCurDay = curDayFormat.format(date);

        //상단 월
        switch (strCurMonth) {
            case "01" : strMonth = "Jan"; break;
            case "02" : strMonth = "Feb"; break;
            case "03" : strMonth = "Mar"; break;
            case "04" : strMonth = "Apr"; break;
            case "05" : strMonth = "May"; break;
            case "06" : strMonth = "Jun"; break;
            case "07" : strMonth = "Jul"; break;
            case "08" : strMonth = "Aug"; break;
            case "09" : strMonth = "Sep"; break;
            case "10" : strMonth = "Oct"; break;
            case "11" : strMonth = "Nov"; break;
            case "12" : strMonth = "Dec"; break;
        }

        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        int dayofWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String weekday="SUN";

        //상단 요일
        switch (dayofWeekNumber){
            case 1: weekday="Sunday";    break;
            case 2: weekday="Monday";    break;
            case 3: weekday="Tuesday";    break;
            case 4: weekday="Wednesday";    break;
            case 5: weekday="Thursday";    break;
            case 6: weekday="Friday";    break;
            case 7: weekday="Saturday";    break;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        base_date = format.format(date);
        dust_daytime = data.getStartYmd() + " " + base_time + ":00";
        base_time = base_time.concat("00");

        //UI 객체 설정
        backButton = (ImageButton) findViewById(R.id.detail_back);
        tvDay = (TextView) findViewById(R.id.detail_day);
        tvEventName = (TextView) findViewById(R.id.event_name);
        tvEventTime = (TextView) findViewById(R.id.event_time);
        tvTmp = (TextView) findViewById(R.id.weather_temperature);
        tvRain = (TextView) findViewById(R.id.weather_rainpercent);
        tvWind = (TextView) findViewById(R.id.weather_windspeed);
        tvDust = (TextView) findViewById(R.id.weather_dust);
        weatherImg = (ImageView) findViewById(R.id.weather_image);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvDay.setText(weekday + ", " + strMonth + " " + strCurDay);
        tvEventName.setText(data.getTitle());
        tvEventTime.setText(data.getStartHms() + " - " + data.getEndHms());

        weatherService = WeatherRetrofitClient.getClient().create(WeatherService.class);

        weatherService.getWeather(MyKey,
                "1",
                "10",
                "JSON",
                base_date,
                base_time,
                "60",
                "126").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                Result weatherResponse = response.body();
                weatherItemList = weatherResponse.getResponse().getBody().getItems().getItem();
                for (Item item : weatherItemList) {
                    String str = item.getCategory();

                    switch(str) {
                        case "POP" : {
                            //rainPercent = Integer.parseInt(item.getObsrValue());
                            rainPercent = item.getObsrValue();
                            break;
                        }
                        case "PTY" : {
                            if (item.getObsrValue().equals("0")) {
                                rainPercent = "0";
                                weatherCode = item.getObsrValue();
                            }
                            else if (item.getObsrValue().equals("1")) rainPercent = "100";
                            break;
                        }
                        case "WSD" : {
                            //windSpeed = Double.parseDouble(item.getObsrValue());
                            windSpeed = item.getObsrValue();
                            break;
                        }
                        case "T1H" : {
                            Double temDouble = Double.parseDouble(item.getObsrValue());
                            int tem = (int) Math.round(temDouble);
                            System.out.println(item.getCategory());
                            System.out.println("T1H = " + tem);
                            temperature = String.valueOf(tem);
                            break;
                        }
                        case "TMP" : {
                            Double temDouble = Double.parseDouble(item.getObsrValue());
                            int tem = (int) Math.round(temDouble);
                            System.out.println(item.getCategory());
                            System.out.println("TMP = " + tem);
                            temperature = String.valueOf(tem);
                            break;
                        }
                    }
                }
                System.out.println(temperature);
                System.out.println(rainPercent);
                System.out.println(windSpeed);

                switch (weatherCode) {
                    case "0" : weatherImg.setImageResource(R.drawable.weather_sunny);
                }

                tvTmp.setText(temperature);
                tvRain.setText(rainPercent);
                tvWind.setText(windSpeed);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });


        dustService = DustRetrofitClient.getClient().create(DustService.class);

        dustService.getDust("용산구", "json", "daily", 1, 10, DustKey).enqueue(new Callback<PmInfo>() {
            @Override
            public void onResponse(Call<PmInfo> call, retrofit2.Response<PmInfo> response) {
                PmInfo dustResponse = response.body();
                dustItemList = dustResponse.getResponse().getBody().getItems();

                for(com.cookandroid.exam.DTO.Dust.Item item : dustItemList) {
                    if (item.getDataTime().equals(dust_daytime)) {
                        dustValue = item.getPm10Grade();
                        System.out.println(dustValue);
                    }
                }

                switch (dustValue) {
                    case "1" : dustGrade = "좋음";
                    case "2" : dustGrade = "보통";
                    case "3" : dustGrade = "나쁨";
                    case "4" : dustGrade = "매우나쁨";
                }

                tvDust.setText(dustGrade);
            }

            @Override
            public void onFailure(Call<PmInfo> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

}
