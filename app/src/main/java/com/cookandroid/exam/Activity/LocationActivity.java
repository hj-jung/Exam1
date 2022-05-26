package com.cookandroid.exam.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.exam.Interface.LocationService;
import com.cookandroid.exam.Interface.WeatherService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.LocationRetrofitClient;
import com.cookandroid.exam.Retrofit.WeatherRetrofitClient;
import com.cookandroid.exam.Util.LocationItem;

import net.daum.android.map.MapView;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private EditText search;
    private TextView pagenum;
    private Button btn_search, btn_prev, btn_next;
    private MapView mapView;

    private String location, name, road, address;
    private int num;
    private RecyclerView recyclerView;

    private LocationService locationService;

    private ArrayList<LocationItem> locationItemArrayList = new ArrayList<>();

    private String API_KEY = "8267284f26c823bdac89070cabe710bb";

    final static String TAG = "LocationActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        search = (EditText) findViewById(R.id.location_search);
        pagenum = (TextView) findViewById(R.id.pageNum);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_prev = (Button) findViewById(R.id.btn_prev);
        btn_next = (Button) findViewById(R.id.btn_next);
        mapView = (MapView) findViewById(R.id.mapView);

        location = search.getText().toString();

        locationService = LocationRetrofitClient.getClient().create(LocationService.class);


    }


}
