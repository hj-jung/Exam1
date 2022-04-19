package com.cookandroid.exam.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.exam.Activity.ScheduleCheckAcitivity;
import com.cookandroid.exam.Activity.ScheduleUpdateActivity;
import com.cookandroid.exam.R;

public class DailyFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daily, container, false);

        ImageButton plus = (ImageButton) view.findViewById(R.id.daily_plus);

        //->일정등록페이지로 이동
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleUpdateActivity.class); //fragment라서 activity intent와는 다른 방식
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        return view;
    }
}
