package com.cookandroid.mysonge.Util;

import android.os.Parcel;
import android.os.Parcelable;

public class RoutineData implements Parcelable {

    public Integer routineId;
    public String routineName;
    public String routineTime;
    public String rourintContext;
    public boolean routineAchieve;

    public RoutineData(Integer routineId, String routineName, String routineTime, String routineContext, Boolean routineAchive) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.routineTime = routineTime;
        this.rourintContext = routineContext;
        this.routineAchieve = routineAchive;
    }

    protected RoutineData(Parcel in) {
        routineId = in.readInt();
        routineName = in.readString();
        routineTime = in.readString();
        rourintContext = in.readString();
        routineAchieve = in.readByte() != 0;
    }

    public static final Creator<RoutineData> CREATOR = new Creator<RoutineData>() {
        @Override
        public RoutineData createFromParcel(Parcel in) {
            return new RoutineData(in);
        }

        @Override
        public RoutineData[] newArray(int size) {
            return new RoutineData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(routineId);
        parcel.writeString(routineName);
        parcel.writeString(routineTime);
        parcel.writeString(rourintContext);
        parcel.writeByte((byte) (routineAchieve ? 1 : 0));
    }
}
