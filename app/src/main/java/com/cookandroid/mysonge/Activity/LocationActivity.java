package com.cookandroid.mysonge.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.mysonge.Adapter.LocationAdapter;
import com.cookandroid.mysonge.DTO.Location.Document;
import com.cookandroid.mysonge.DTO.Location.ResultKeyword;
import com.cookandroid.mysonge.Interface.LocationService;
import com.cookandroid.mysonge.R;
import com.cookandroid.mysonge.Retrofit.LocationRetrofitClient;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationActivity extends AppCompatActivity {

    private EditText search;
    private TextView pagenum;
    private Button btn_search, btn_select, btn_prev, btn_next;
    private MapView mapView;

    private String keyword, location;
    private Double x, y;
    private int num;
    private RecyclerView recyclerView;

    private LocationService locationService;
    private LocationAdapter locationAdapter;

    private ArrayList<Document> locationItemArrayList = new ArrayList<>();
    private List<Document> documentList = new ArrayList<>();

    private String API_KEY = "KakaoAK 8267284f26c823bdac89070cabe710bb";

    final static String TAG = "LocationActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Intent intent = getIntent();
        String schedulelocation = intent.getStringExtra("location");

        search = (EditText) findViewById(R.id.location_search);
        btn_search = (Button) findViewById(R.id.btn_search);
        mapView = (MapView) findViewById(R.id.mapView);
        recyclerView = (RecyclerView) findViewById(R.id.location_list);

        search.setText(schedulelocation);

        //Adapter 생성
        locationAdapter = new LocationAdapter(locationItemArrayList, getApplicationContext(), search, recyclerView);
        recyclerView.setAdapter(locationAdapter);

        //리사이클러뷰 & layoutManager 생성
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(locationAdapter);

        //위치권한허용
        //Foreground 위치 권한 확인
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        //Background 위치 권한 확인
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        if(permissionCheck2 == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 0);
        }

        //리스트 아이템 클릭 시 지도에서 해당 위치로 이동 (미완성)
        locationAdapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                MapPoint mapPoint;
                Document locationItem= locationItemArrayList.get(pos);
                Intent resultIntent = new Intent(getApplicationContext(), ScheduleUpdateActivity.class);
                resultIntent.putExtra("locationName", locationItem.getPlaceName());
                resultIntent.putExtra("locationX", locationItem.getX());
                resultIntent.putExtra("locationY", locationItem.getY());
                setResult(RESULT_OK, resultIntent);
                finish();
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
/*
        //장소 확정 시, location값 넘겨주기
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = search.getText().toString();
                Intent intent = new Intent(LocationActivity.this, ScheduleUpdateActivity.class);
                intent.putExtra("Location", location);
                intent.putExtra("x", x);
                intent.putExtra("y", y);
                startActivity(intent);
            }
        });

 */

    }

    //keyword 검색 함수
    private void searchKeyword(String keyword, int num) {
        locationItemArrayList.clear();
        locationAdapter.clear();
        locationAdapter.notifyDataSetChanged();

        LocationService locationService = LocationRetrofitClient.getClient().create(LocationService.class);
        Call<ResultKeyword> call = locationService.getResultKeyword(API_KEY, keyword, 10);
        call.enqueue(new Callback<ResultKeyword>() {
            @Override
            public void onResponse(Call<ResultKeyword> call, Response<ResultKeyword> response) {
                if(response.isSuccessful()){
                    ResultKeyword resultKeyword = response.body();
                    documentList = resultKeyword.getDocuments();
                    for(Document locationItem : documentList){
                        locationAdapter.addItem(locationItem);
                        x = Double.valueOf(locationItem.getX());
                        y = Double.valueOf(locationItem.getY());
                    }
                    locationAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<ResultKeyword> call, Throwable t) {

            }
        });
    }
}
