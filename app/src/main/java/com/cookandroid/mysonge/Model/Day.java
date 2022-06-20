package com.cookandroid.mysonge.Model;

import com.cookandroid.mysonge.Util.DataUtil;

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

