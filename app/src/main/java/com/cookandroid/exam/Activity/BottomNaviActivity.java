package com.cookandroid.exam.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.exam.Fragment.DailyFragment;
import com.cookandroid.exam.Fragment.MainFragment;
import com.cookandroid.exam.Fragment.MypageFragment;
import com.cookandroid.exam.Fragment.RoutineFragment;
import com.cookandroid.exam.Interface.CharacterService;
import com.cookandroid.exam.Retrofit.RetrofitClient;
import com.cookandroid.exam.Interface.RoutineService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.DTO.GetCharacter;
import com.cookandroid.exam.DTO.Routine;
import com.cookandroid.exam.Util.RoutineAchive;
import com.cookandroid.exam.Util.RoutineData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNaviActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm = getSupportFragmentManager(); //fragmentManager생성

    private CharacterService characterService;
    private RoutineService routineService;

    private int characterid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavi);

        Intent intent = getIntent(); //전달할 데이터를 받을 Intent
        // text 키값으로 데이터를 받는다. String을 받아야 하므로 getStringExtra()를 사용함

        if (intent != null) {
            characterid = intent.getIntExtra("characterID", 0);
            System.out.println(characterid);
        }


        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        fm.beginTransaction().add(R.id.btmnavi_frame, new MainFragment()).commit(); //첫 fragment 화면 설정

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = fm.beginTransaction(); //실제 transaction 실행하는 아이-얘가 onNavigationItemSelected밖에 있으면 'commit already called'오류
                switch (item.getItemId()){  //fragment 교체 실행 함수
                    case R.id.tab_main:
                        ft.replace(R.id.btmnavi_frame, new MainFragment()).commit(); break;
                    case R.id.tab_daily:
                        ft.replace(R.id.btmnavi_frame, new DailyFragment()).commit(); break;
                    case R.id.tab_routine:
                        getRoutine();
                        ft.replace(R.id.btmnavi_frame, new RoutineFragment()).commit(); break;
                    case R.id.tab_mypage:
                        getCharacter();
                        ft.replace(R.id.btmnavi_frame, new MypageFragment()).commit(); break;
                }
                return true;
            }
        });

        characterService = RetrofitClient.getClient().create(CharacterService.class);
        routineService = RetrofitClient.getClient().create(RoutineService.class);
    }

    private void getCharacter() {
        Call<GetCharacter> call = characterService.getCharacter(characterid);
        call.enqueue(new Callback<GetCharacter>() {
            @Override
            public void onResponse(Call<GetCharacter> call, Response<GetCharacter> response) {
                if (!response.isSuccessful()) {
                    Log.d("MainActivity", String.valueOf(response.code()));
                    return;
                }

                GetCharacter getCharacterResponse = response.body();

                Log.d("TAG", getCharacterResponse.getName());

                Bundle bundle = new Bundle();
                bundle.putInt("id",characterid);
                bundle.putString("name", getCharacterResponse.getName());
                bundle.putString("message", getCharacterResponse.getQuote());

                MypageFragment mypageFragment = new MypageFragment();
                mypageFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.btmnavi_frame, mypageFragment).commit();

            }

            @Override
            public void onFailure(Call<GetCharacter> call, Throwable t) {
                Log.d("getCharacter=", t.getMessage());
            }
        });
    }

    private void getRoutine() {
        Call<List<Routine>> call = routineService.getRoutine();
        call.enqueue(new Callback<List<Routine>>() {
            @Override
            public void onResponse(Call<List<Routine>> call, Response<List<Routine>> response) {
                if (!response.isSuccessful()) {
                    Log.d("MainActivity", String.valueOf(response.code()));
                    return;
                }
                List<Routine> routineList = response.body();

                Bundle bundle = new Bundle();
                ArrayList<RoutineData> routineDataArrayList = new ArrayList<RoutineData>();

                for (Routine routine : routineList ) {
                    System.out.println(routine.getId());
                    String routineStrTime = routine.getRoutineTime();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        LocalTime localTime= LocalTime.parse(routineStrTime, formatter);
                        System.out.println(localTime);
                    }

                    routineDataArrayList.add(new RoutineData(routine.getId(), routine.getName(), routine.getRoutineTime(), routine.getContext(), routine.getAchieve()));
                }

                bundle.putParcelableArrayList("routineList", (ArrayList<? extends Parcelable>) routineDataArrayList);

                if (routineDataArrayList.isEmpty()) System.out.println("routineDataArrayList is empty");
                else {
                    for (RoutineData routineData : routineDataArrayList) {
                        System.out.println(routineData.routineName);
                    }
                }

                RoutineFragment routineFragment = new RoutineFragment();
                routineFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.btmnavi_frame, routineFragment).commit();
            }

            @Override
            public void onFailure(Call<List<Routine>> call, Throwable t) {
                Log.d("getCharacter=", t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("tag", "===requestCode===" + requestCode);
        if (requestCode == 2) {
            if (data != null) {
                String name = data.getStringExtra("name");
                String message = data.getStringExtra("message");

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("message", message);

                MypageFragment mypageFragment = new MypageFragment();
                mypageFragment.setArguments(bundle);
                fm.beginTransaction().replace(R.id.btmnavi_frame, mypageFragment).commit();
            }
        }

        if (requestCode == 3) {
            getRoutine();
            RoutineFragment routineFragment = new RoutineFragment();
            fm.beginTransaction().replace(R.id.btmnavi_frame, routineFragment).commit();
        }
        if (requestCode == 4) {
            getRoutine();
            RoutineFragment routineFragment = new RoutineFragment();
            fm.beginTransaction().replace(R.id.btmnavi_frame, routineFragment).commit();
        }
    }
}

