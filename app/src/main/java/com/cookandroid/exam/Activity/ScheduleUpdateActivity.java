package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.cookandroid.exam.Fragment.MainFragment;
import com.cookandroid.exam.R;

public class ScheduleUpdateActivity extends FragmentActivity {

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

        //MainFragment로 돌아가기기
        Button add= (Button) findViewById(R.id.btn_add);

    }


}
