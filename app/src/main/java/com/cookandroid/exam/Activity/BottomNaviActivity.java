package com.cookandroid.exam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.exam.Fragment.DailyFragment;
import com.cookandroid.exam.Fragment.MainFragment;
import com.cookandroid.exam.Fragment.MypageFragment;
import com.cookandroid.exam.Fragment.RoutineFragment;
import com.cookandroid.exam.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNaviActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm = getSupportFragmentManager();   //fragmentManager생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavi);

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
                        ft.replace(R.id.btmnavi_frame, new RoutineFragment()).commit(); break;
                    case R.id.tab_mypage:
                        ft.replace(R.id.btmnavi_frame, new MypageFragment()).commit(); break;
                }
                return true;
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
    }
}

