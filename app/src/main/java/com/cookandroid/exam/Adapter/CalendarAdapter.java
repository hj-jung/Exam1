package com.cookandroid.exam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cookandroid.exam.Activity.ScheduleCheckAcitivity;
import com.cookandroid.exam.Activity.ScheduleUpdateActivity;
import com.cookandroid.exam.Fragment.MainFragment;
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
    int sun = 0;


    private static final String TAG = "CalenderAdpater";


    private Context context;
    private List<Object> mCalendarList;
    private int user_id;

    public CalendarAdapter(List<Object> calendarList, int user_id){
        mCalendarList = calendarList;
        this.user_id = user_id;
    }

    public void setCalendarList(List<Object> calendarList){
        mCalendarList = calendarList;
        notifyDataSetChanged();
    }

    //뷰타입 나누기
    @Override
    public int getItemViewType(int position) {
        Object item = mCalendarList.get(position);

        if(item instanceof String){
            sun++;
            return EMPTY_TYPE;
        }
        else if(item instanceof Long) return HEADER_TYPE;
        else    return DAY_TYPE;
        /*일요일 빨간날 표시
        else    {
            if((position - sun)%7==0)   {
                int a = (position-sun) % 7;
                Log.d(TAG, Integer.toString(position));
                Log.d(TAG, Integer.toString(sun));
                return DAY_RED_TYPE;
            }
            return DAY_TYPE;
        }*/
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
            context = parent.getContext();

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

            //리사이클러뷰 아이템 선택
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Log.d("위치확인",""+mCalendarList.get(pos).getClass().toString());
                    Intent intent = new Intent(context, ScheduleCheckAcitivity.class);
                    String key = mCalendarList.get(pos).getClass().toString();
                    if(pos != RecyclerView.NO_POSITION){
                        intent.putExtra("key", key);
                        intent.putExtra("int", pos);
                        intent.putExtra("userID", user_id);
                        context.startActivity(intent);
                    }
                }
            });

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