package com.cookandroid.exam.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.exam.Util.TimelineItem;
import com.cookandroid.exam.R;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends BaseAdapter {

    private ArrayList<TimelineItem> timelineItemList = new ArrayList<TimelineItem>();

    public TimelineAdapter(){

    }

    @Override
    public int getCount() {
        return timelineItemList.size();
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
        TextView title = (TextView) convertView.findViewById(R.id.daily);

        TimelineItem timelineItem = timelineItemList.get(position);

        timeTextView.setText(timelineItem.getTime());
        apTextView.setText(timelineItem.getAP());

        if(timelineItem.getColor()=="RED")  title.setBackgroundColor(Color.RED);
        if(timelineItem.getColor()=="ORANGE")  title.setBackgroundColor(Color.parseColor("#F0CA00"));
        if(timelineItem.getColor()=="GREEN")  title.setBackgroundColor(Color.GREEN);
        if(timelineItem.getColor()=="BLUE") title.setBackgroundColor(Color.BLUE);
        if(timelineItem.getColor()=="PURPLE")  title.setBackgroundColor(Color.parseColor("#E200CC"));
        if(timelineItem.getColor()=="BLACK")  title.setBackgroundColor(Color.BLACK);
        if(timelineItem.getColor()=="WHITE")  title.setBackgroundColor(Color.WHITE);

        title.setText(timelineItem.getTitle());

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

    public void addItem(String time, String ap, String title, String color){
        TimelineItem item = new TimelineItem();

        item.setTime(time);
        item.setAP(ap);
        item.setTitle(title);
        item.setColor(color);

        timelineItemList.add(item);
    }

}
