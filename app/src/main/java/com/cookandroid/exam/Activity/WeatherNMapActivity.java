package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.exam.DTO.Dust.PmInfo;
import com.cookandroid.exam.DTO.Dust.Response;
import com.cookandroid.exam.DTO.Weather.Item;
import com.cookandroid.exam.DTO.Weather.Result;
import com.cookandroid.exam.Interface.DustService;
import com.cookandroid.exam.Interface.WeatherService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.DustRetrofitClient;
import com.cookandroid.exam.Retrofit.WeatherRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherNMapActivity extends Activity {

    private static final String TAG = "DetailPageActivity";

    private TextView tvTmp, tvRain, tvWind, tvDust;
    private ImageView weatherImg;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //레이아웃 xml 지정
        setContentView(R.layout.activity_detail_page);

        //UI 객체 설정
        tvTmp = (TextView) findViewById(R.id.weather_temperature);
        tvRain = (TextView) findViewById(R.id.weather_rainpercent);
        tvWind = (TextView) findViewById(R.id.weather_windspeed);
        tvDust = (TextView) findViewById(R.id.weather_dust);
        weatherImg = (ImageView) findViewById(R.id.weather_image);

        weatherService = WeatherRetrofitClient.getClient().create(WeatherService.class);

        weatherService.getWeather(MyKey,
                "1",
                "10",
                "JSON",
                "20220526",
                "1200",
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
                    if (item.getDataTime().equals("2022-05-26 12:00")) {
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
