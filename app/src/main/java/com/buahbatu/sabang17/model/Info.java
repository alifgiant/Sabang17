package com.buahbatu.sabang17.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Info implements Serializable{
    @Exclude
    public String key;

    public String title;
    public String date_start;
    public String date_end;
    public String desc;
    public String organizer;
    public String time_start;
    public String time_end;

    Info() {
        // Default constructor required for calls to DataSnapshot.getValue(Info.class)
    }

    public Info(String title, String date_start, String date_end, String desc, String organizer, String time_start, String time_end) {
        this.title = title;
        this.date_start = date_start;
        this.date_end = date_end;
        this.desc = desc;
        this.organizer = organizer;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    public static Info createInfoObject (DataSnapshot dataSnapshot){
        Info info = dataSnapshot.getValue(Info.class);
        info.key = dataSnapshot.getKey();
        return info;
    }
}
