package com.cookandroid.exam.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cookandroid.exam.Activity.ScheduleUpdateActivity;
import com.cookandroid.exam.Activity.WeatherNMapActivity;
import com.cookandroid.exam.Adapter.TimelineAdapter;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.ScheduleData;
import com.cookandroid.exam.Util.TimelineItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyFragment extends Fragment {

    private View view;
    private ListView listView;
    private TimelineAdapter adapter;
    private List<TimelineItem> timelineItemList;

    ArrayList<ScheduleData> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_daily, container, false);

        ImageButton tempbtn = (ImageButton) view.findViewById(R.id.weather_page_go);
        ImageButton plus = (ImageButton) view.findViewById(R.id.daily_plus);
        TextView day = (TextView) view.findViewById(R.id.day);

        tempbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivityForResult(new Intent(getContext(), WeatherNMapActivity.class), 5);
            }
        });

        //상단 오늘 날짜로 설정
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd", Locale.getDefault());
        String strday = format.format(date);

        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        int dayofWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String weekday="SUN";

        //상단 요일
        switch (dayofWeekNumber){
            case 1: weekday="SUN";    break;
            case 2: weekday="MON";    break;
            case 3: weekday="TUE";    break;
            case 4: weekday="WED";    break;
            case 5: weekday="THU";    break;
            case 6: weekday="FRI";    break;
            case 7: weekday="SAT";    break;
        }
        day.setText(strday+"  "+weekday);

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
