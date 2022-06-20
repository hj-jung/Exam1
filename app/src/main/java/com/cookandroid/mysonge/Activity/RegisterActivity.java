package com.cookandroid.mysonge.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cookandroid.mysonge.DTO.Register;
import com.cookandroid.mysonge.DTO.UserLogin;
import com.cookandroid.mysonge.Interface.UserService;
import com.cookandroid.mysonge.R;
import com.cookandroid.mysonge.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";

    String email, name, password;
    Button btn_join, btn_login_page;
    EditText joinemail, joinname, joinpassword;

    private UserService userService;
    private int userId;

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

        Call<Integer> call = userService.register(register);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                userId = response.body();
                if (userId == 0) Toast.makeText(getApplicationContext(), "이미 존재하는 회원입니다.", Toast.LENGTH_SHORT).show();
                else login();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
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
