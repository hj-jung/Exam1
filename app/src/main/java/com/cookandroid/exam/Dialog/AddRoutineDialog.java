package com.cookandroid.exam.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.exam.R;

public class AddRoutineDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 팝업창 검정색 배경 없애기
        getWindow().setBackgroundDrawable(new PaintDrawable(Color.TRANSPARENT));

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //레이아웃 xml 지정
        setContentView(R.layout.routine_add_dialog);

        //액티비티 >> 팝업창 형태 크기 및 위치 커스텀
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        int pointWidth = point.x; //가로
        int pointHeight = point.y; //세로

        int width = (int) (pointWidth * 1.0); //Display 가로 사이즈 100%
        int height = (int) (pointHeight * 0.9); //Display 세로 사이즈 80%

        getWindow().getAttributes().width = width; //가로 크기
        getWindow().getAttributes().height = height; //세로 크기
        getWindow().getAttributes().gravity = Gravity.BOTTOM; //위치 아래로 설정

    }
}