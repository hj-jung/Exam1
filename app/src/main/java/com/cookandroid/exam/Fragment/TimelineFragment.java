package com.cookandroid.exam.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cookandroid.exam.Activity.WeatherNMapActivity;
import com.cookandroid.exam.Adapter.TimelineAdapter;
import com.cookandroid.exam.Util.ScheduleData;

import java.util.ArrayList;


public class TimelineFragment extends ListFragment {

    TimelineAdapter adapter;

    private static final String TAG = "DailyFragment";

    private String[][] strings = new String[25][5];
    private ArrayList<ScheduleData> scheduleDataArrayList = new ArrayList<>();

    private int time;
    private String color, title, eventTime, AMPM, startH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_daily, container, false);

        adapter = new TimelineAdapter();

        if (getArguments() != null) {
            scheduleDataArrayList = getArguments().getParcelableArrayList("TodaySchedule");

            for (ScheduleData scheduleData : scheduleDataArrayList) {
                time = scheduleData.getTime();
                startH = scheduleData.getStartH();
                eventTime = scheduleData.getStartHms() + " - " + scheduleData.getEndHms();
                color = scheduleData.getColor();
                title = scheduleData.getTitle();
                AMPM = scheduleData.getAMPM();

                strings[time][0] = startH;
                strings[time][1] = AMPM;
                strings[time][2] = title;
                strings[time][3] = color;
                strings[time][4] = eventTime;
            }

            //해당 시간대에 일치하는 일정 DailyList에 띄우기
            adapter.addItem("0", "","","","");
            for(int i=1; i<25; i++){
                if(strings[i][0]!=null){
                    //System.out.println(i + " = " + strings[i][0] + strings[i][1] + strings[i][2] + strings[i][3] + strings[i][4]);
                    adapter.addItem(strings[i][0],strings[i][1], strings[i][2], strings[i][3], strings[i][4]);
                }
                else {
                    if (i > 12) {
                        adapter.addItem(String.valueOf(i - 12), "PM", "", "WHITE", "");
                    } else {
                        adapter.addItem(String.valueOf(i), "AM", "", "WHITE", "");
                    }
                }
            }
        }

        setListAdapter(adapter);

        adapter.notifyDataSetChanged();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //일정 클릭 이벤트 처리 -> 일정상세페이지로 이동
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(getActivity(), WeatherNMapActivity.class);
        intent.putExtra("scheduleList", scheduleDataArrayList);
        intent.putExtra("pos", position);
        System.out.println(position);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}