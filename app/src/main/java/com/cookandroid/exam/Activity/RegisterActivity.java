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

public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";

    String email, name, password, userId;
    Button btn_join, btn_login_page;
    EditText joinemail, joinname, joinpassword;

    private UserService userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_join = (Button)findViewById(R.id.join);
        btn_login_page = (Button)findViewById(R.id.login_page);

        userService = RetrofitClient.getClient().create(UserService.class);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        btn_login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    private void register() {

        Intent intent = new Intent(getApplicationContext(), BottomNaviActivity.class);

        joinemail = (EditText)findViewById(R.id.join_email);
        joinname = (EditText)findViewById(R.id.join_name);
        joinpassword = (EditText)findViewById(R.id.join_password);

        email = joinemail.getText().toString();
        name = joinname.getText().toString();
        password = joinpassword.getText().toString();

        Register register= new Register(email, name, password);

        Call<String> call = userService.register(register);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                userId = response.body();
                if (userId.equals("0")) Toast.makeText(getApplicationContext(), "이미 존재하는 회원입니다.", Toast.LENGTH_SHORT).show();
                else login();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    private void login() {

        Intent intent = new Intent(getApplicationContext(), SetCharacterActivity.class);

        joinemail = (EditText)findViewById(R.id.join_email);
        joinpassword = (EditText)findViewById(R.id.join_password);

        email = joinemail.getText().toString();
        password = joinpassword.getText().toString();

        UserLogin userLogin = new UserLogin(email, password);

        Call<String> call = userService.login(userLogin);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext(), "회원 정보 없음", Toast.LENGTH_SHORT).show();
                    return;
                }
                userId = response.body();
                if (userId.equals("0")) Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                else {
                    intent.putExtra("userID", userId);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
