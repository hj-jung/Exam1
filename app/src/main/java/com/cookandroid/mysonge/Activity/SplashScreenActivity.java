package com.cookandroid.mysonge.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cookandroid.mysonge.R;

public class SplashScreenActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);	//theme로 지정했다면 삭제한다.

        ImageView loadingImage = (ImageView) findViewById(R.id.loading);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(loadingImage);
        Glide.with(this).load(R.drawable.loading).into(gifImage);

        moveMain(1);	//1초 후 main activity 로 넘어감
    }

    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //new Intent(현재 context, 이동할 activity)
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(intent);	//intent 에 명시된 액티비티로 이동

                finish();	//현재 액티비티 종료
            }
        }, 1000 * sec); // sec초 정도 딜레이를 준 후 시작
    }

}
