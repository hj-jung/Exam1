package com.cookandroid.exam.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cookandroid.exam.DTO.Schedule;
import com.cookandroid.exam.Util.ScheduleData;
import com.cookandroid.exam.Util.TimelineItem;
import com.cookandroid.exam.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class TimelineAdapter extends BaseAdapter {

    private ArrayList<TimelineItem> timelineItemList = new ArrayList<TimelineItem>();

    private int tListCnt = 0;

    public TimelineAdapter(){
    }

    @Override
    public int getCount() {
        return tListCnt;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_daily_timeline, parent, false);
        }

        TextView timeTextView = (TextView) convertView.findViewById(R.id.time);
        TextView apTextView = (TextView) convertView.findViewById(R.id.ampm);
        TextView title = (TextView) convertView.findViewById(R.id.timeline_event_name);
        TextView time = (TextView) convertView.findViewById(R.id.timeline_event_time);
        LinearLayout timelineevent = (LinearLayout) convertView.findViewById(R.id.timeline_event);

        TimelineItem timelineItem = timelineItemList.get(position);

        timeTextView.setText(timelineItem.getTime());
        apTextView.setText(timelineItem.getAP());

        switch (timelineItem.getColor()) {
            case ("RED") : timelineevent.setBackgroundColor(Color.parseColor("#FA7683")); break;
            case ("ORANGE") : timelineevent.setBackgroundColor(Color.parseColor("#FDB483")); break;
            case ("YELLOW") : timelineevent.setBackgroundColor(Color.parseColor("#FDF17C")); break;
            case ("GREEN") : timelineevent.setBackgroundColor(Color.parseColor("#7CEB70")); break;
            case ("BLUE") : timelineevent.setBackgroundColor(Color.parseColor("#A4DAFE")); break;
            case ("PURPLE") : timelineevent.setBackgroundColor(Color.parseColor("#DFCAFE")); break;
            case ("WHITE") : timelineevent.setBackgroundColor(Color.WHITE); break;
        }

        title.setText(timelineItem.getTitle());
        time.setText(timelineItem.getTimeEvent());

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;   //i==position
    }

    @Override
    public Object getItem(int i) {
        return timelineItemList.get(i);
    }

    public void addItem(String time, String ap, String title, String color, String timeEvent){

        if (time.equals("0")) timelineItemList.clear();
        else {
            TimelineItem item = new TimelineItem();

            item.setTime(time);
            item.setAP(ap);
            item.setTitle(title);
            item.setColor(color);
            item.setTimeEvent(timeEvent);

            timelineItemList.add(item);
        }
        tListCnt = timelineItemList.size();
        this.notifyDataSetChanged();

    }

}
