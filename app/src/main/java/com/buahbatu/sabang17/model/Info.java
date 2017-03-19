package com.buahbatu.sabang17.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Info implements Serializable{
    @Exclude
    public String key;

    public String title;
    public String startDate;
    public String endDate;
    public String desc;
    public String organizer;
    public String startTime;
    public String endTime;

    public Info() {
        // Default constructor required for calls to DataSnapshot.getValue(Info.class)
    }

    public Info(String title, String startDate, String endDate, String desc, String organizer, String startTime, String endTime) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.desc = desc;
        this.organizer = organizer;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Info createInfoObject (DataSnapshot dataSnapshot){
        Info info = dataSnapshot.getValue(Info.class);
        info.key = dataSnapshot.getKey();
        return info;
    }
}
