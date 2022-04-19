package com.cookandroid.exam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.exam.R;

public class SetCharacterActivity extends AppCompatActivity {
    String name, intro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcharacter);

        //닉네임, 한줄소개입력
        EditText chName=(EditText)findViewById(R.id.chName);
        EditText message=(EditText)findViewById(R.id.message);
        //닉네임, 한줄소개 DB로 보내기
        name=chName.getText().toString();
        intro=message.getText().toString();

        //->메인페이지로 이동
        Button start=(Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNaviActivity.class);
                startActivity(intent);
            }
        });
    }

}
