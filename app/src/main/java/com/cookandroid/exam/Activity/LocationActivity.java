package com.cookandroid.exam.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.exam.Adapter.LocationAdapter;
import com.cookandroid.exam.DTO.Location.Place;
import com.cookandroid.exam.DTO.Location.ResultKeyword;
import com.cookandroid.exam.DTO.Weather.Result;
import com.cookandroid.exam.Interface.LocationService;
import com.cookandroid.exam.Interface.WeatherService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.LocationRetrofitClient;
import com.cookandroid.exam.Retrofit.RetrofitClient;
import com.cookandroid.exam.Retrofit.WeatherRetrofitClient;
import com.cookandroid.exam.Util.LocationItem;

import net.daum.android.map.MapView;
import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {

    private EditText search;
    private TextView pagenum;
    private Button btn_search, btn_prev, btn_next;
    private MapView mapView;

    private String keyword, name, road, address;
    private int num;
    private RecyclerView recyclerView;

    private LocationService locationService;
    private LocationAdapter locationAdapter;

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
        recyclerView = (RecyclerView) findViewById(R.id.location_list);

        //Adapter 생성
        locationAdapter = new LocationAdapter(locationItemArrayList, getApplicationContext(), search, recyclerView);
        recyclerView.setAdapter(locationAdapter);

        //리사이클러뷰 & layoutManager 생성
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(locationAdapter);

        //리스트 아이템 클릭 시 지도에서 해당 위치로 이동 (미완성)
        locationAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                MapPoint mapPoint;
            }
        });

        //검색 버튼
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = search.getText().toString();
                num = 1;
                searchKeyword(keyword, num);
            }
        });

        //리사이클러뷰 이전 페이지 버튼
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                pagenum.setText(String.valueOf(num));
                searchKeyword(keyword, num);
            }
        });

        //리사이클러뷰 다음 페이지 버튼
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                pagenum.setText(String.valueOf(num));
                searchKeyword(keyword, num);
            }
        });

    }

    //keyword 검색 함수
    private void searchKeyword(String keyword, int num){
        LocationService locationService = RetrofitClient.getClient().create(LocationService.class);
        Call<ResultKeyword> call = locationService.getResultKeyword(API_KEY, keyword, 15);
        call.enqueue(new Callback<ResultKeyword>() {
            @Override
            public void onResponse(Call<ResultKeyword> call, Response<ResultKeyword> response) {
                if(response.isSuccessful()){
                    for(Place place : response.body().getDocuments()){
                        addItemsAndMarkers(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultKeyword> call, Throwable t) {

            }
        });
    }

    //검색 결과 처리 함수
    private void addItemsAndMarkers(ResultKeyword resultKeyword){
        //검색 결과 있음
        if(resultKeyword!=null){
            locationItemArrayList.clear();
            //결과를 리사이클러뷰에 추가

        }
    }

}
