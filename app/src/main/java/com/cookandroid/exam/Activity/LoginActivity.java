package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cookandroid.exam.DTO.Register;
import com.cookandroid.exam.DTO.UserLogin;
import com.cookandroid.exam.Interface.UserService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    String email, password;
    Button btn_login, btn_register_page;
    EditText loginemail, loginpassword;

    private UserService userService;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail = (EditText)findViewById(R.id.login_email);
        loginpassword = (EditText)findViewById(R.id.login_password);
        btn_login = (Button)findViewById(R.id.login);
        btn_register_page = (Button)findViewById(R.id.register_page);

        userService = RetrofitClient.getClient().create(UserService.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        btn_register_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void login() {

        Intent intent = new Intent(getApplicationContext(), BottomNaviActivity.class);

        loginemail=(EditText)findViewById(R.id.login_email);
        loginpassword=(EditText)findViewById(R.id.login_password);

        //닉네임, 한줄소개 값 얻어오기
        email=loginemail.getText().toString();
        password=loginpassword.getText().toString();

        UserLogin userLogin = new UserLogin(email, password);

        Call<Integer> call = userService.login(userLogin);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "회원 정보 없음", Toast.LENGTH_SHORT).show();
                    return;
                }
                userId = response.body();
                if (userId == 0) Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                else {
                    intent.putExtra("userID", userId);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

}
