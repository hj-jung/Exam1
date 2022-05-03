package com.cookandroid.exam.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cookandroid.exam.Model.CalendarHeader;
import com.cookandroid.exam.Model.Day;
import com.cookandroid.exam.Model.EmptyDay;
import com.cookandroid.exam.Model.RedDay;
import com.cookandroid.exam.R;

import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter {
    private final int EMPTY_TYPE = 0;
    private final int DAY_TYPE = 1;
    private final int DAY_RED_TYPE= 2;
    private final int HEADER_TYPE = 3;

    private List<Object> mCalendarList;

    public CalendarAdapter(List<Object> calendarList){

        mCalendarList = calendarList;
    }

    public void setCalendarList(List<Object> calendarList){
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    //뷰타입 나누기
    @Override
    public int getItemViewType(int position) {
        Object item = mCalendarList.get(position);
        int sun=0;
        if(item instanceof String){
            sun++;
            return EMPTY_TYPE;
        }

        else if(item instanceof Long) return HEADER_TYPE;
        else    {
            if((position-sun+1)%7==0)   return DAY_RED_TYPE;
            return DAY_TYPE;
        }
    }

    //ViewHolder 생성
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if(viewType == EMPTY_TYPE){
            return new EmptyViewHolder(inflater.inflate(R.layout.item_day_empty, parent, false));
        }
        else if(viewType == DAY_TYPE){
            return new DayViewHolder(inflater.inflate(R.layout.item_day, parent, false));
        }
        else if(viewType == DAY_RED_TYPE){
            return new DayRedViewHolder(inflater.inflate(R.layout.item_day_red, parent, false));
        }
        else {
            HeaderViewHolder viewHolder = new HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false));

            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
            params.setFullSpan((true));
            viewHolder.itemView.setLayoutParams(params);

            return viewHolder;
        }
    }

    //데이터 넣기
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);

        if(viewType == EMPTY_TYPE){
            EmptyViewHolder holder = (EmptyViewHolder) viewHolder;
            EmptyDay model = new EmptyDay();
            holder.bind(model);
        }
        else if(viewType == DAY_TYPE){
            DayViewHolder holder = (DayViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            Day model = new Day();
            if(item instanceof Calendar){
                model.setCalendar((Calendar)item);
            }
            holder.bind(model);
        }
        else if(viewType == DAY_RED_TYPE){
            DayRedViewHolder holder = (DayRedViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            RedDay model = new RedDay();
            if(item instanceof Calendar){
                model.setCalendar((Calendar)item);
            }
            holder.bind(model);
        }
        else if(viewType==HEADER_TYPE){
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            Object item = mCalendarList.get(position);
            CalendarHeader model = new CalendarHeader();

            if(item instanceof Long){   //long type의 현재시간
                model.setHeader((Long)item);    //현재시간 넣으면 model에 데이터 들어감
            }
            holder.bind(model); //view에 표시
        }
    }

    @Override
    public int getItemCount() {
        if(mCalendarList!=null){
            return mCalendarList.size();
        }
        return 0;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView itemHeader;

        public HeaderViewHolder(@NonNull View itemView){
            super(itemView);
            initView(itemView);
        }

        public void initView(View v){
            itemHeader = (TextView) v.findViewById(R.id.item_header_title);
        }

        public void bind(CalendarHeader model){
            String header = ((CalendarHeader)model).getHeader();
            itemHeader.setText(header);
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }
        public void initView(View v){

        }
        public void bind(EmptyDay model){

        }
    }

    public class DayViewHolder extends RecyclerView.ViewHolder{
        TextView itemDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }
        public void initView(View v){
            itemDay=(TextView) v.findViewById(R.id.item_day);
        }

        public void bind(Day model){
            String day = ((Day)model).getDay();
            itemDay.setText(day);
        }
    }

    private class DayRedViewHolder extends RecyclerView.ViewHolder{
        TextView itemRedDay;

        public DayRedViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }
        public void initView(View v){
            itemRedDay=(TextView) v.findViewById(R.id.item_red_day);
        }

        public void bind(RedDay model){
            String day = ((RedDay)model).getDay();
            itemRedDay.setText(day);
        }
    }

}