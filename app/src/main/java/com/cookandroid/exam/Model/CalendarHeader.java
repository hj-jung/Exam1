package com.cookandroid.exam.Model;

import com.cookandroid.exam.Util.DataUtil;

public class CalendarHeader extends ViewModel {

    String header;
    long mCurrentTime;

    public CalendarHeader() {
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(long time) {

        String value = DataUtil.getDate(time, DataUtil.HEADER_FORMAT);
        this.header = value;

    }

    public void setHeader(String header) {

        this.header = header;

    }
}
