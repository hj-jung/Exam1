package com.cookandroid.mysonge.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookandroid.mysonge.Activity.EditMypageActivity;
import com.cookandroid.mysonge.R;


public class MypageFragment extends Fragment {

    private int characterID, user_id;

    private String routineAchieve;
    private double achieve;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        ImageButton EditMypagebtn = (ImageButton) view.findViewById(R.id.editMypage);
        TextView mypageName = (TextView) view.findViewById(R.id.mypageName);
        TextView mypageMessage = (TextView) view.findViewById(R.id.mypageMessage);
        TextView mypageLevel = (TextView) view.findViewById(R.id.character_level);
        ImageView character = (ImageView) view.findViewById(R.id.mypage_character);

        EditMypagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditMypageActivity.class);
                intent.putExtra("id", characterID);
                intent.putExtra("userID", user_id);
                getActivity().startActivityForResult(intent, 2);
            }
        });

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String message = getArguments().getString("message");
            characterID = getArguments().getInt("id");
            user_id = getArguments().getInt("userID");
            routineAchieve = getArguments().getString("routineAchive");

            System.out.println("name = " + name);
            System.out.println("message = " + message);
            System.out.println("routineAchieve" + routineAchieve);

            if (routineAchieve.equals("NaN")) {
                achieve = 0.0;
            }
            else {
                achieve = Double.parseDouble(routineAchieve);
            }

            if (achieve < 50) {
                character.setImageResource(R.drawable.character_lv1);
                mypageLevel.setText("Lv.1");
            }
            else if (achieve < 100) {
                character.setImageResource(R.drawable.character_lv2);
                mypageLevel.setText("Lv.2");
            }
            else {
                character.setImageResource(R.drawable.character_lv3);
                mypageLevel.setText("Lv.3");
            }

            mypageName.setText(name);
            mypageMessage.setTextSize(20);
            mypageMessage.setText(message);
        }

        return view;
    }

}