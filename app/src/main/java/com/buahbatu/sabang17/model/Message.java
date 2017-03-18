package com.buahbatu.sabang17.model;

import java.util.Random;

/**
 * Created by maakbar on 3/17/17.
 */

public class Message {
    public static int SELF = 0;
    public static int OTHER = 1;

    private int viewType;

    public Message() {
        Random random = new Random();
        this.viewType = random.nextInt(2);;
    }

    public int getViewType() {
        return viewType;
    }
}