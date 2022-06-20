package com.cookandroid.mysonge.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.cookandroid.mysonge.DTO.Dust.PmInfo;
import com.cookandroid.mysonge.DTO.Weather.Item;
import com.cookandroid.mysonge.DTO.Weather.Result;
import com.cookandroid.mysonge.Interface.DustService;
import com.cookandroid.mysonge.Interface.WeatherService;
import com.cookandroid.mysonge.R;
import com.cookandroid.mysonge.Retrofit.DustRetrofitClient;
import com.cookandroid.mysonge.Retrofit.WeatherRetrofitClient;
import com.cookandroid.mysonge.Util.ScheduleData;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailPageActivity extends Activity {

    private static final String TAG = "DetailPageActivity";

    private TextView tvDay, tvEventName, tvEventTime, tvTmp, tvRain, tvWind, tvDust, tvRec;
    private ImageView weatherImg, dressImg1, dressImg2;
    private ImageButton backButton;
    private Button KakaoMapBtn;
    private MapView mapView;
    private LinearLayout mapViewContainer;

    private String MyKey = "3QkdRZx/R+OBMH+5QoKu7iRbyDkdjaO0nMixw6RktnNL74/9rWajdRkCmtRfYxxYrWv8OABBzFaEY5h6WqwJFA==";
    private String DustKey = "+WSXT9nqJHijeDm0+DfSdKPLPcMLKnfHaJ6XU7N2hq0VkI3x+NM7Yc4Bgbkbok08RCFQEIgd0W/LpiVOg2j3Ow==";
    private final String packageName = "net.daum.android.map";

    public static int TO_GRID = 0;

    private WeatherService weatherService;
    private List<Item> weatherItemList = new ArrayList<>();

    private DustService dustService;
    private List<com.cookandroid.mysonge.DTO.Dust.Item> dustItemList = new ArrayList<>();

    private String rainPercent = "-";
    private String temperature = "-";
    private String windSpeed = "-";
    private String weatherCode = "0";

    private String dustAddress = "용산구";
    private String dustValue, dustGrade, eventTime;

    private ArrayList<ScheduleData> list = new ArrayList<>();
    ScheduleData data;
    private int pos, dressTem;
    private String base_date = "20220606";
    private String base_time = "0200";
    private String nx = "60";
    private String ny = "127";
    private String dust_daytime = "2022-06-06 02:00";
    private String strMonth = "May";
    private MapPoint BASE_LOCATION;
    private double x, y, curLat, curLon;

    String posTime;
    int timeH;

    String urlScheme = "kakaomap://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=PUBLICTRANSIT";

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

        if (list != null) {
            System.out.println("here");
            for (ScheduleData scheduleData : list) {
                posTime = scheduleData.getStartHms();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime localTime = LocalTime.parse(posTime, formatter);
                    timeH = Integer.valueOf(localTime.getHour());
                    if (timeH == pos) {
                        data = scheduleData;
                        base_time = String.format("%02d", pos);
                    }
                }
            }
        }
        //현재 위치 받아오기
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc_Current = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        curLat = loc_Current.getLatitude();
        curLon = loc_Current.getLongitude();
        System.out.println("=====================================================" + curLat + " " + curLon);

        //장소 x,y
        x = data.getX();
        y = data.getY();
        System.out.println("=====================================================" + x + " " + y);

        urlScheme = "kakaomap://route?sp=" + curLat + "," + curLon + "&ep=" + y + "," + x + "&by=PUBLICTRANSIT";
        System.out.println(urlScheme);

        LatXLngY gridInfo = convertGRID_GPS(TO_GRID, y, x);
        nx = Integer.toString((int) Math.round(gridInfo.x));
        ny = Integer.toString((int) Math.round(gridInfo.y));

        System.out.println("=====" + nx + ", " + ny);

        Geocoder geocoder = new Geocoder(this);
        List<Address> gList = null;
        try {
            gList = geocoder.getFromLocation(y, x, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("====TAG=====", "setMaskLocation");
        }
        if (gList != null) {
            if (gList.size() == 0) {
                Toast.makeText(getApplicationContext(), "해당 위치에서 검색된 주소정보가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Address address = gList.get(0);
                dustAddress = address.getSubLocality();
            }
        }

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat curDayFormat = new SimpleDateFormat("dd");
        String strCurMonth = curMonthFormat.format(date);
        String strCurDay = curDayFormat.format(date);

        //상단 월
        switch (strCurMonth) {
            case "01":
                strMonth = "Jan";
                break;
            case "02":
                strMonth = "Feb";
                break;
            case "03":
                strMonth = "Mar";
                break;
            case "04":
                strMonth = "Apr";
                break;
            case "05":
                strMonth = "May";
                break;
            case "06":
                strMonth = "Jun";
                break;
            case "07":
                strMonth = "Jul";
                break;
            case "08":
                strMonth = "Aug";
                break;
            case "09":
                strMonth = "Sep";
                break;
            case "10":
                strMonth = "Oct";
                break;
            case "11":
                strMonth = "Nov";
                break;
            case "12":
                strMonth = "Dec";
                break;
        }

        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        int dayofWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String weekday = "SUN";

        //상단 요일
        switch (dayofWeekNumber) {
            case 1:
                weekday = "Sunday";
                break;
            case 2:
                weekday = "Monday";
                break;
            case 3:
                weekday = "Tuesday";
                break;
            case 4:
                weekday = "Wednesday";
                break;
            case 5:
                weekday = "Thursday";
                break;
            case 6:
                weekday = "Friday";
                break;
            case 7:
                weekday = "Saturday";
                break;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        base_date = format.format(date);
        System.out.println("WeatherNMap 171 : " + data.getStartYmd());
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
        tvRec = (TextView) findViewById(R.id.dress_recommendation);
        weatherImg = (ImageView) findViewById(R.id.weather_image);
        dressImg1 = (ImageView) findViewById(R.id.dress_1);
        dressImg2 = (ImageView) findViewById(R.id.dress_2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //상단 날짜 및 이벤트 이름 설정
        tvDay.setText(weekday + ", " + strMonth + " " + strCurDay);
        tvEventName.setText(data.getTitle());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localStartTime= LocalTime.parse(data.getStartHms(), formatter);
            String eventStart = localStartTime.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
            LocalTime localEndTime= LocalTime.parse(data.getEndHms(), formatter);
            String eventEnd = localEndTime.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
            eventTime = eventStart + " - " + eventEnd;
        }

        tvEventTime.setText(eventTime);

        //날씨 API
        weatherService = WeatherRetrofitClient.getClient().create(WeatherService.class);
        weatherService.getWeather(MyKey,
                "1",
                "10",
                "JSON",
                base_date,
                base_time,
                nx,
                ny).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                Result weatherResponse = response.body();
                String resultCode = weatherResponse.getResponse().getHeader().getResultCode();
                if (resultCode.equals("00")) {
                    weatherItemList = weatherResponse.getResponse().getBody().getItems().getItem();
                    for (Item item : weatherItemList) {
                        String str = item.getCategory();

                        switch (str) {
                            case "POP": {
                                rainPercent = item.getObsrValue();
                                break;
                            }
                            case "PTY": {
                                if (item.getObsrValue().equals("0")) {
                                    rainPercent = "0";
                                    weatherCode = item.getObsrValue();
                                } else if (item.getObsrValue().equals("1")) {
                                    rainPercent = "100";
                                    weatherCode = item.getObsrValue();
                                } else weatherCode = item.getObsrValue();
                                break;
                            }
                            case "WSD": {
                                windSpeed = item.getObsrValue();
                                break;
                            }
                            case "T1H": {
                                Double temDouble = Double.parseDouble(item.getObsrValue());
                                int tem = (int) Math.round(temDouble);
                                System.out.println(item.getCategory());
                                System.out.println("T1H = " + tem);
                                temperature = String.valueOf(tem);
                                break;
                            }
                            case "TMP": {
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
                        case "0":
                            weatherImg.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "1":
                            weatherImg.setImageResource(R.drawable.weather_rainyday);
                            break;
                        case "3":
                            weatherImg.setImageResource(R.drawable.weather_snowy);
                    }

                    tvTmp.setText(temperature);
                    tvRain.setText(rainPercent);
                    tvWind.setText(windSpeed);

                    dressTem = Integer.parseInt(temperature);

                    if (dressTem >= 27) {
                        tvRec.setText("나시티, 반바지, 민소매 원피스");
                        dressImg1.setImageResource(R.drawable.dress_sleeveless);
                        dressImg2.setImageResource(R.drawable.dress_shorts);
                    } else if (dressTem >= 23 && dressTem <= 26) {
                        tvRec.setText("반팔, 얇은 셔츠, 얇은 긴팔, 반바지, 면바지");
                        dressImg1.setImageResource(R.drawable.dress_short_sleeve);
                        dressImg2.setImageResource(R.drawable.dress_shirt);
                    } else if (dressTem >= 20) {
                        tvRec.setText("긴팔티, 가디건, 후드티, 면바지, 슬랙스, 스키니");
                        dressImg1.setImageResource(R.drawable.dress_jean);
                        dressImg2.setImageResource(R.drawable.dress_long_sleeve);
                    } else if (dressTem >= 17) {
                        tvRec.setText("니트, 가디건, 후드티, 맨투맨, 청바지, 슬랙스");
                        dressImg1.setImageResource(R.drawable.dress_hoodie);
                        dressImg2.setImageResource(R.drawable.dress_sleeve);
                    } else if (dressTem >= 12) {
                        tvRec.setText("자켓, 셔츠, 가디건, 간절기 야상");
                        dressImg1.setImageResource(R.drawable.dress_jacket);
                        dressImg2.setImageResource(R.drawable.dress_long_shirt);
                    } else if (dressTem >= 10) {
                        tvRec.setText("트렌치코트, 간절기 야상, 여러겹 껴입기");
                        dressImg1.setImageResource(R.drawable.dress_trench);
                        dressImg2.setImageResource(R.drawable.dress_knit);
                    } else if (dressTem >= 6) {
                        tvRec.setText("코트, 가죽자켓");
                        dressImg1.setImageResource(R.drawable.dress_coat);
                        dressImg2.setImageResource(R.drawable.dress_sweater_winter);
                    } else {
                        tvRec.setText("겨울 옷(야상, 패딩, 목도리 등)");
                        dressImg1.setImageResource(R.drawable.dress_puffer_coat);
                        dressImg2.setImageResource(R.drawable.dress_scarf);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });


        //미세먼지 API
        dustService = DustRetrofitClient.getClient().create(DustService.class);

        dustService.getDust(dustAddress, "json", "daily", 1, 10, DustKey).enqueue(new Callback<PmInfo>() {
            @Override
            public void onResponse(Call<PmInfo> call, retrofit2.Response<PmInfo> response) {
                PmInfo dustResponse = response.body();
                dustItemList = dustResponse.getResponse().getBody().getItems();

                dustValue = "0";

                for (com.cookandroid.mysonge.DTO.Dust.Item item : dustItemList) {
                    if (item.getDataTime().equals(dust_daytime)) {
                        dustValue = item.getPm10Grade();
                        System.out.println(dustValue);
                    }
                }

                switch (dustValue) {
                    case "0":
                        dustGrade = "-";
                    case "1":
                        dustGrade = "좋음";
                    case "2":
                        dustGrade = "보통";
                    case "3":
                        dustGrade = "나쁨";
                    case "4":
                        dustGrade = "매우나쁨";
                }

                tvDust.setText(dustGrade);
            }

            @Override
            public void onFailure(Call<PmInfo> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });


        //카카오맵 API + 마커
        //여기에 LocationActivity에서 장소 선택 시에 받아온 x,y(경도,위도값) 넣어줘야함 - 우선 서울역으로 설정
        //BASE_LOCATION = MapPoint.mapPointWithGeoCoord(37.553836, 126.969652);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BASE_LOCATION = MapPoint.mapPointWithGeoCoord(y, x);
        //BASE_LOCATION = MapPoint.mapPointWithGeoCoord(37.546438, 126.964725);

        mapView = new MapView(this);
        mapViewContainer = (LinearLayout) findViewById(R.id.detail_mapView);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPoint(BASE_LOCATION, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Event Location");
        marker.setTag(0);
        marker.setMapPoint(BASE_LOCATION);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

        //카카오맵 앱으로 이동
        KakaoMapBtn = (Button) findViewById(R.id.detail_kakaomap);
        //Intent intentmap = this.getPackageManager().getLaunchIntentForPackage(packageName);
        Intent intentmap = new Intent();
        intentmap.setData(Uri.parse(urlScheme));
        KakaoMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentmap);
                //DetailPageActivity.this.startActivity(intentmap);
            }
        });
    }


    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }

    class LatXLngY
    {
        public double lat;
        public double lng;

        public double x;
        public double y;

    }
}
