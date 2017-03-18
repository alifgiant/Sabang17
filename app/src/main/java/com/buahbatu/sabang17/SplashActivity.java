package com.buahbatu.sabang17;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private void moveToMainActivity(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (task.isSuccessful()) {
                        moveToMainActivity();
                    }else {
                        Log.w(TAG, "signInAnonymously", task.getException());
                        Toast.makeText(SplashActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            moveToMainActivity();
        }
    }
}