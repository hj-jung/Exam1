package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.exam.Model.CharacterService;
import com.cookandroid.exam.Model.RetrofitClient;
import com.cookandroid.exam.Model.RoutineService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.Character;
import com.cookandroid.exam.Util.Routine;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetCharacterActivity extends AppCompatActivity {

    private static final String TAG = "SetCharacterActivity";

    String name, intro;
    Button btn_start;
    EditText chName, message;

    private CharacterService characterService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcharacter);

        //닉네임, 한줄소개입력
        chName=(EditText)findViewById(R.id.chName);
        message=(EditText)findViewById(R.id.message);
        btn_start=(Button)findViewById(R.id.start);

        characterService = RetrofitClient.getClient().create(CharacterService.class);

        //->메인페이지로 이동 && 닉네임과 한줄소개 DB로 보내기
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNaviActivity.class);
                addCharacter();
                startActivity(intent);
            }
        });

    }
    private void addCharacter() {

        Intent intent = new Intent(getApplicationContext(), BottomNaviActivity.class);

        chName=(EditText)findViewById(R.id.chName);
        message=(EditText)findViewById(R.id.message);

        //닉네임, 한줄소개 값 얻어오기
        name=chName.getText().toString();
        intro=message.getText().toString();

        Character character = new Character(name, intro);

        Call<Character> call = characterService.addCharacter(character);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Character characterResponse = response.body();
                intent.putExtra("characterID", characterResponse.getId());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

}
