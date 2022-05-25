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

    private Context context;
    private List<TimelineItem> timelineItemList;

    public TimelineAdapter(Context context, ArrayList<TimelineItem> timelineItemList){
        this.context = context;
        this.timelineItemList = timelineItemList;
    }

    @Override
    public int getCount() {
        return timelineItemList.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();


        View v = View.inflate(context,R.layout.item_daily_timeline, null);
        TextView timeTextView = (TextView) v.findViewById(R.id.time);
        TextView apTextView = (TextView) v.findViewById(R.id.ampm);
        TextView title = (TextView) v.findViewById(R.id.daily);

        TimelineItem timelineItem = timelineItemList.get(position);

        timeTextView.setText(timelineItem.getTime());
        apTextView.setText(timelineItem.getAP());
        title.setText(timelineItem.getTitle());
        if(timelineItem.getColor()=="RED")  title.setBackgroundColor(Color.RED);
        if(timelineItem.getColor()=="ORANGE")  title.setBackgroundColor(Color.parseColor("#F0CA00"));
        if(timelineItem.getColor()=="GREEN")  title.setBackgroundColor(Color.GREEN);
        if(timelineItem.getColor()=="BLUE") title.setBackgroundColor(Color.BLUE);
        if(timelineItem.getColor()=="PURPLE")  title.setBackgroundColor(Color.parseColor("#E200CC"));
        if(timelineItem.getColor()=="BLACK")  title.setBackgroundColor(Color.BLACK);
        if(timelineItem.getColor()=="WHITE")  title.setBackgroundColor(Color.WHITE);


        return v;
    }

    @Override
    public long getItemId(int i) {
        return i;   //i==position
    }

    @Override
    public Object getItem(int i) {
        return timelineItemList.get(i);
    }


}
