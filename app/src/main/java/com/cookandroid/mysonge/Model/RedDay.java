package com.cookandroid.mysonge.Model;

import com.cookandroid.mysonge.Util.DataUtil;

import java.util.Calendar;

public class RedDay extends ViewModel{
    String day;

    public RedDay(){

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
