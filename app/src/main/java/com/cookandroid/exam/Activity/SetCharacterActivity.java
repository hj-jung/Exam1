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
    Button btn_start;
    EditText chName, message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcharacter);

        //닉네임, 한줄소개입력
        chName=(EditText)findViewById(R.id.chName);
        message=(EditText)findViewById(R.id.message);
        btn_start=(Button)findViewById(R.id.start);

        //닉네임, 한줄소개 값 얻어오기
        name=chName.getText().toString();
        intro=message.getText().toString();

        //->메인페이지로 이동 && 닉네임과 한줄소개 DB로 보내기
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNaviActivity.class);
                startActivity(intent);
            }
        });
    }

}
