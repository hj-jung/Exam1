package com.cookandroid.exam.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cookandroid.exam.Interface.CharacterService;
import com.cookandroid.exam.Retrofit.RetrofitClient;
import com.cookandroid.exam.R;
import com.cookandroid.exam.DTO.Character;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetCharacterActivity extends AppCompatActivity {

    private static final String TAG = "SetCharacterActivity";

    String name, intro;
    Button btn_start;
    EditText chName, message;
    private int user_id;

    private CharacterService characterService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcharacter);
        getHashKey();
        Intent data = getIntent();
        if (data != null) {
            user_id = data.getIntExtra("userID", -1);
        }

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

        Character character = new Character(name, intro, user_id);

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
                intent.putExtra("userID", user_id);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    //HashKey값 구하기
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
