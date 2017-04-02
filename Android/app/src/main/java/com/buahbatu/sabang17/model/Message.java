package com.buahbatu.sabang17.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

public class Message {
    @Exclude
    public static final int SELF = 0;
    @Exclude
    public static final int OTHER = 1;
    @Exclude
    public String key;

    public String messageSource;
    public String text;
    public String timestamp;


    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    public Message(String messageSource, String text) {
        this.messageSource = messageSource;
        this.text = text;

        /*Temporary timestamp format, remove after FIREBASE FUNCTION GENERATED*/
        Long tsLong = System.currentTimeMillis();
        this.timestamp = tsLong.toString();
    }

    public static Message createMessageObject(DataSnapshot dataSnapshot){
        Message message = dataSnapshot.getValue(Message.class);
        message.key = dataSnapshot.getKey();
        return message;
    }
}