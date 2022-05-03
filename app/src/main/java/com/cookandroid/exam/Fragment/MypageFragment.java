package com.cookandroid.exam.Fragment;


import static android.os.Build.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cookandroid.exam.Activity.AddRoutineActivity;
import com.cookandroid.exam.Activity.EditMypageActivity;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.ActivityResultEvent;


public class MypageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        ImageButton EditMypagebtn = (ImageButton) view.findViewById(R.id.editMypage);
        TextView mypageName = (TextView) view.findViewById(R.id.mypageName);
        TextView mypageMessage = (TextView) view.findViewById(R.id.mypageMessage);

        EditMypagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), EditMypageActivity.class);
                getActivity().startActivityForResult(new Intent(getContext(), EditMypageActivity.class), 2);
            }
        });

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String message = getArguments().getString("message");
            mypageName.setText(name);
            mypageMessage.setTextSize(20);
            mypageMessage.setText(message);
        }

        return view;
    }

}