package com.cookandroid.exam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cookandroid.exam.Activity.ScheduleEditActivity;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.ScheduleItem;

import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {

    private ArrayList<ScheduleItem> scheduleItems = new ArrayList<ScheduleItem>();

    public ScheduleAdapter(){

    }

    @Override
    public int getCount() {
        return scheduleItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_schedule_listview, parent, false);
        }

        TextView color = (TextView) convertView.findViewById(R.id.color);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        ImageButton btn = (ImageButton) convertView.findViewById(R.id.btn_more);

        ScheduleItem scheduleItem = scheduleItems.get(position);

        if(scheduleItem.getColor()=="RED")  color.setBackgroundColor(Color.RED);
        if(scheduleItem.getColor()=="ORANGE")  color.setBackgroundColor(Color.parseColor("#F0CA00"));
        if(scheduleItem.getColor()=="GREEN")  color.setBackgroundColor(Color.GREEN);
        if(scheduleItem.getColor()=="BLUE")  color.setBackgroundColor(Color.BLUE);
        if(scheduleItem.getColor()=="PURPLE")  color.setBackgroundColor(Color.parseColor("#E200CC"));
        if(scheduleItem.getColor()=="BLACK")  color.setBackgroundColor(Color.BLACK);
        title.setText(scheduleItem.getTitle());
        time.setText(scheduleItem.getTime());

        //->일정 수정 화면으로 이동
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ScheduleEditActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return scheduleItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String title, String time, String color){
        ScheduleItem item = new ScheduleItem();

        item.setColor(color);
        item.setTitle(title);
        item.setTime(time);

        scheduleItems.add(item);
    }

}
