package com.buahbatu.sabang17.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Critic {
    public String name;
    public String origin;
    public String contact;
    public String message;

    @Exclude
    public String key;

    public Critic() {
        // Default constructor required for calls to DataSnapshot.getValue(Critic.class)
    }

    public Critic(String name, String origin, String contact, String message) {
        this.name = name;
        this.origin = origin;
        this.contact = contact;
        this.message = message;
    }

    public static Critic createCriticObject (DataSnapshot dataSnapshot){
        Critic critic = dataSnapshot.getValue(Critic.class);
        critic.key = dataSnapshot.getKey();
        return critic;
    }
}
