package com.cookandroid.exam.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Routine {
        @SerializedName("achieve")
        @Expose
        private Boolean achieve;
        @SerializedName("context")
        @Expose
        private String context;
        @SerializedName("friday")
        @Expose
        private Boolean friday;
        @SerializedName("monday")
        @Expose
        private Boolean monday;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("routineTime")
        @Expose
        private String routineTime;
        @SerializedName("saturday")
        @Expose
        private Boolean saturday;
        @SerializedName("sunday")
        @Expose
        private Boolean sunday;
        @SerializedName("thursday")
        @Expose
        private Boolean thursday;
        @SerializedName("tuesday")
        @Expose
        private Boolean tuesday;
        @SerializedName("wednesday")
        @Expose
        private Boolean wednesday;

        public Boolean getAchieve() {
            return achieve;
        }

        public void setAchieve(Boolean achieve) {
            this.achieve = achieve;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public Boolean getFriday() {
            return friday;
        }

        public void setFriday(Boolean friday) {
            this.friday = friday;
        }

        public Boolean getMonday() {
            return monday;
        }

        public void setMonday(Boolean monday) {
            this.monday = monday;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRoutineTime() {
            return routineTime;
        }

        public void setRoutineTime(String routineTime) {
            this.routineTime = routineTime;
        }

        public Boolean getSaturday() {
            return saturday;
        }

        public void setSaturday(Boolean saturday) {
            this.saturday = saturday;
        }

        public Boolean getSunday() {
            return sunday;
        }

        public void setSunday(Boolean sunday) {
            this.sunday = sunday;
        }

        public Boolean getThursday() {
            return thursday;
        }

        public void setThursday(Boolean thursday) {
            this.thursday = thursday;
        }

        public Boolean getTuesday() {
            return tuesday;
        }

        public void setTuesday(Boolean tuesday) {
            this.tuesday = tuesday;
        }

        public Boolean getWednesday() {
            return wednesday;
        }

        public void setWednesday(Boolean wednesday) {
            this.wednesday = wednesday;
        }

        public Routine(Boolean achieve, String context, boolean friday, boolean monday, String name, String routineTime, boolean saturday, boolean sunday, boolean thursday, boolean tuesday, boolean wednesday) {
            this.achieve = achieve;
            this.context = context;
            this.friday = friday;
            this.monday = monday;
            this.name = name;
            this.routineTime = routineTime;
            this.saturday = saturday;
            this.sunday = sunday;
            this.thursday = thursday;
            this.tuesday = tuesday;
            this.wednesday = wednesday;
        }

}
