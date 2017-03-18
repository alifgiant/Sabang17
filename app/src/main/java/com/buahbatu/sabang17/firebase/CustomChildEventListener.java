package com.buahbatu.sabang17.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Extend an interface, remove un-needed event
 */

public abstract class CustomChildEventListener implements ChildEventListener {
    @Override
    public abstract void onChildAdded(DataSnapshot dataSnapshot, String previousChildName);

    @Override
    public abstract void onChildChanged(DataSnapshot dataSnapshot, String previousChildName);

    @Override
    public abstract void onChildRemoved(DataSnapshot dataSnapshot);

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
