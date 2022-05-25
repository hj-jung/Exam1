package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.exam.DTO.Weather.Item;
import com.cookandroid.exam.DTO.Weather.Result;
import com.cookandroid.exam.Interface.WeatherService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.WeatherRetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherNMapActivity extends Activity {

    private static final String TAG = "DetailPageActivity";

    private TextView tvTmp, tvRain, tvWind;
    private ImageView weatherImg;

    private String MyKey = "3QkdRZx/R+OBMH+5QoKu7iRbyDkdjaO0nMixw6RktnNL74/9rWajdRkCmtRfYxxYrWv8OABBzFaEY5h6WqwJFA==";

    private WeatherService weatherService;
    private List<Item> weatherItemList = new ArrayList<>();

    private String rainPercent;
    private String temperature;
    private String windSpeed;
    private String weatherCode;


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
        weatherImg = (ImageView) findViewById(R.id.weather_image);

        weatherService = WeatherRetrofitClient.getClient().create(WeatherService.class);

        weatherService.getWeather(MyKey,
                "1",
                "10",
                "JSON",
                "20220526",
                "0200",
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
    }

}
