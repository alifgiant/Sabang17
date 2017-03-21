package com.buahbatu.sabang17.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

public class SabangEvent extends Info {
    @Exclude
    public String eventType;


    public SabangEvent() {
        // Default constructor required for calls to DataSnapshot.getValue(SabangEvent.class)
    }

    public SabangEvent(String title, String date_start, String date_end, String desc, String organizer, String time_start, String time_end) {
        super(title, date_start, date_end, desc, organizer, time_start, time_end);
    }

    public static SabangEvent createEventObject (DataSnapshot dataSnapshot){
        SabangEvent event = dataSnapshot.getValue(SabangEvent.class);
        String[] keySplit = dataSnapshot.getKey().split("_");
        event.eventType= keySplit[0];
        event.key = keySplit[1];
        return event;
    }
}
