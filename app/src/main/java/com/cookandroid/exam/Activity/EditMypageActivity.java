package com.cookandroid.exam.Activity;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.cookandroid.exam.Interface.CharacterService;
import com.cookandroid.exam.Retrofit.RetrofitClient;
import com.cookandroid.exam.R;
import com.cookandroid.exam.DTO.Character;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMypageActivity extends Activity {

    EditText editName;
    EditText editMessage;

    private CharacterService characterService;

    private int characterID, user_id;

    //private FragmentManager fm = FragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        characterID = intent.getIntExtra("id", 0);
        user_id = intent.getIntExtra("userID", -1);
        System.out.println("EditMyPage id = " + characterID);

        //다이얼로그 팝업창 검정색 배경 없애기
        getWindow().setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //레이아웃 xml 지정
        setContentView(R.layout.activity_edit_mypage);

        //액티비티 >> 팝업창 형태 크기 및 위치 커스텀
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int pointWidth = point.x; //가로
        int pointHeight = point.y; //세로

        int width = (int) (pointWidth * 1.0); //Display 가로 사이즈 100%
        int height = (int) (pointHeight * 0.5); //Display 세로 사이즈 90%

        getWindow().getAttributes().width = width; //가로 크기
        getWindow().getAttributes().height = height; //세로 크기
        getWindow().getAttributes().gravity = Gravity.BOTTOM; //위치 아래로 설정

        Button cancelbtn = (Button) findViewById(R.id.mypage_cancle);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editName = findViewById(R.id.editName);
        editMessage = findViewById(R.id.editMessage);
        Button editbtn = (Button) findViewById(R.id.mypage_edit);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCharacter();
                finish();
            }
        });

        characterService = RetrofitClient.getClient().create(CharacterService.class);

    }

    private void updateCharacter() {

        editName = findViewById(R.id.editName);
        editMessage = findViewById(R.id.editMessage);

        //닉네임, 한줄소개 값 얻어오기
        String inputName = editName.getText().toString(); //editName에 입력한 문자열을 얻어온다.
        String inputMessage = editMessage.getText().toString(); //editMessgae에 입력한 문자열을 얻어온다.

        Character character = new Character(inputName, inputMessage, user_id);

        Call<Character> call = characterService.updateCharacter(characterID, character);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (!response.isSuccessful()) {
                    Log.d("MainActivity", String.valueOf(response.code()));
                    return;
                }

                Character getCharacterResponse = response.body();

                Log.d("TAG", getCharacterResponse.getName());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("name", getCharacterResponse.getName());
                returnIntent.putExtra("message", getCharacterResponse.getQuote());
                setResult(Activity.RESULT_OK, returnIntent);
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d("getCharacter=", t.getMessage());
            }
        });
    }

}