package com.buahbatu.sabang17.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by maakbar on 3/16/17.
 */

public class Business implements Serializable {
    @Exclude
    public String key;

    public String name;
    public String desc;
    public String price;

    public Business() {
        // Default constructor required for calls to DataSnapshot.getValue(Business.class)
    }

    public Business(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }


    public static Business createBusinessObject (DataSnapshot dataSnapshot){
        Business business = dataSnapshot.getValue(Business.class);
        business.key = dataSnapshot.getKey();
        return business;
    }
}
