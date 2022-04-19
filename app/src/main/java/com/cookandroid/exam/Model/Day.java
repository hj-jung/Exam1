package com.cookandroid.exam.Model;

import android.text.format.DateUtils;

import com.cookandroid.exam.Util.DataUtil;

import java.util.Calendar;
public class Day extends ViewModel{
    String day;

    public Day(){

    }

    public String getDay(){
        return day;
    }

    public void setDay(String day){
        this.day =day;
    }

    public void setCalendar(Calendar calendar){
        day = DataUtil.getDate(calendar.getTimeInMillis(), DataUtil.DAY_FORMAT);
    }
}

