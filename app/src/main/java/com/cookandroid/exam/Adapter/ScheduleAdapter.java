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
import com.cookandroid.exam.Util.TimelineItem;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends BaseAdapter {

    private Context context;
    private List<ScheduleItem> scheduleItems;
    private int user_id;

    public ScheduleAdapter(Context context, List<ScheduleItem> scheduleItems, Integer user_id){
        this.context = context;
        this.scheduleItems = scheduleItems;
        this.user_id = user_id;
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

        System.out.println("먕"+scheduleItem.getColor());

        if(scheduleItem.getColor().equals("RED"))  color.setBackgroundColor(Color.parseColor("#FA7683"));
        if(scheduleItem.getColor().equals("ORANGE"))  color.setBackgroundColor(Color.parseColor("#FDB483"));
        if(scheduleItem.getColor().equals("YELLOW"))  color.setBackgroundColor(Color.parseColor("#FDF17C"));
        if(scheduleItem.getColor().equals("GREEN"))  color.setBackgroundColor(Color.parseColor("#7CEB70"));
        if(scheduleItem.getColor().equals("BLUE"))  color.setBackgroundColor(Color.parseColor("#A4DAFE"));
        if(scheduleItem.getColor().equals("PURPLE"))  color.setBackgroundColor(Color.parseColor("#DFCAFE"));
        title.setText(scheduleItem.getTitle());
        time.setText(scheduleItem.getTime());

        //->일정 수정 화면으로 이동
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), ScheduleEditActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("userID", user_id);
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

}
