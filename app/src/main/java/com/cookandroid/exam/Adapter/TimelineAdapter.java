package com.cookandroid.exam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.exam.Util.TimelineItem;
import com.cookandroid.exam.R;

import java.util.ArrayList;

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

        TimelineItem timelineItem = timelineItemList.get(position);

        timeTextView.setText(timelineItem.getTime());
        apTextView.setText(timelineItem.getAP());

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

    public void addItem(String time, String ap){
        TimelineItem item = new TimelineItem();

        item.setTime(time);
        item.setAP(ap);

        timelineItemList.add(item);
    }

}
