package com.cookandroid.mysonge.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.mysonge.Adapter.ScheduleAdapter;
import com.cookandroid.mysonge.DTO.Schedule;
import com.cookandroid.mysonge.Interface.ScheduleService;
import com.cookandroid.mysonge.R;
import com.cookandroid.mysonge.Retrofit.RetrofitClient;
import com.cookandroid.mysonge.Util.ScheduleItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleCheckAcitivity extends Activity {
    TextView day;
    ListView schedule_list;
    ScheduleAdapter adapter;
    List<ScheduleItem> scheduleItems;
    String today, color, title, time;
    private static final String TAG = "ScheduleCheckActivity";

    private int user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulecheck);
        Intent intent = getIntent();
        int daypos = intent.getIntExtra("int", -1);
        user_id = intent.getIntExtra("userID", -1);
        System.out.println(daypos);

        //상단 해당 날짜로 설정
        day = (TextView) findViewById(R.id.day);
        today = "2022-05-26";

       if (daypos == 10292) today = "2022-05-01";
        else if (daypos > 10292 & daypos < 10322) {
            int dayNum = daypos - 10292 + 1;
            today = "2022-05-" + String.format("%02d", dayNum);
        }
        else if (daypos == 10327) today = "2022-06-01";
        else if (daypos > 10327 & daypos < 10357) {
            int dayNum = daypos - 10327 + 1;
            today = "2022-06-" + String.format("%02d", dayNum);
        }

        day.setText(today);

        //리스트뷰 참조
        schedule_list = (ListView) findViewById(R.id.schedule_list);
        scheduleItems = new ArrayList<ScheduleItem>();

        //Adapter 생성
        adapter = new ScheduleAdapter(getApplicationContext(), scheduleItems, user_id);
        schedule_list.setAdapter(adapter);

        //해당 날짜 일정 GET
        //Retrofit 인터페이스 객체 구현
        ScheduleService scheduleService = RetrofitClient.getClient().create(ScheduleService.class);
        Call<List<Schedule>> call = scheduleService.date(user_id, today);
        call.enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, String.valueOf(response.code()));
                    return;
                }
                Log.d(TAG, "Schedule Response Success");
                List<Schedule> scheduleResponse = response.body();
                for(Schedule schedule : scheduleResponse){
                    color = schedule.getColor();
                    title = schedule.getTitle();
                    time = schedule.getStartYmd() + "~" + schedule.getEndYmd();
                    System.out.println(schedule.getTitle());
                    System.out.println(color+title+time);
                    //GET한 DATA 리스트뷰에 추가
                    ScheduleItem scheduleItem = new ScheduleItem(title, time, color);
                    scheduleItems.add(scheduleItem);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

        //->일정등록화면으로 이동
        ImageButton plus =(ImageButton)findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScheduleUpdateActivity.class);
                intent.putExtra("userID", user_id);
                startActivity(intent);
            }
        });
    }
}
