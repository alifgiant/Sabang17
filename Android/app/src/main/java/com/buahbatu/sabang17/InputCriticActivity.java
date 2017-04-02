package com.buahbatu.sabang17;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.buahbatu.sabang17.model.Critic;
import com.buahbatu.sabang17.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputCriticActivity extends AppCompatActivity {

    private TextInputEditText mCriticName;
    private TextInputEditText mCriticOrigin;
    private TextInputEditText mCriticContact;
    private TextInputEditText mCriticMessage;

    // database critics reference
    DatabaseReference criticsRef;
    DatabaseReference conversationRef;

    View.OnClickListener onSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // reset text input error
            mCriticName.setError(null);
            mCriticOrigin.setError(null);
            mCriticContact.setError(null);
            mCriticMessage.setError(null);

            // check empty
            TextInputEditText emptyTextContainer;
            if (TextUtils.isEmpty(mCriticName.getText())){
                emptyTextContainer = mCriticName;
            }else if (TextUtils.isEmpty(mCriticOrigin.getText())){
                emptyTextContainer = mCriticOrigin;
            }else if (TextUtils.isEmpty(mCriticContact.getText())){
                emptyTextContainer = mCriticContact;
            }else if (TextUtils.isEmpty(mCriticMessage.getText())){
                emptyTextContainer = mCriticMessage;
            }else {
                emptyTextContainer = null;  // all field is filled
            }

            if (emptyTextContainer == null){  // send message
                String name = mCriticName.getText().toString();
                String origin = mCriticOrigin.getText().toString();
                String contact = mCriticContact.getText().toString();
                String message = mCriticMessage.getText().toString();

                // save message
                saveMessage(name, origin, contact, message);

                // reset text input, ready to receive new input
                mCriticName.setText("");
                mCriticOrigin.setText("");
                mCriticContact.setText("");
                mCriticMessage.setText("");
            }else {
                emptyTextContainer.setError(getString(R.string.critic_field_empty));
            }
        }
    };

    private void saveMessage(String name, String origin, String contact, final String message){
        // user reference
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Critic critic = new Critic(name, origin, contact, message);
            final DatabaseReference remoteData = criticsRef.child(user.getUid()).push();
            remoteData.setValue(critic).addOnCompleteListener(InputCriticActivity.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(InputCriticActivity.this, "Pesan telah terkirim",
                                Toast.LENGTH_SHORT).show();

                        Message conversationMessage = new Message(user.getUid(), message);
                        conversationRef.child(remoteData.getKey()).push().setValue(conversationMessage);
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_critic);

        // find views
        mCriticName = (TextInputEditText) findViewById(R.id.critic_name);
        mCriticOrigin = (TextInputEditText) findViewById(R.id.critic_origin);
        mCriticContact = (TextInputEditText) findViewById(R.id.critic_contact);
        mCriticMessage = (TextInputEditText) findViewById(R.id.critic_message);
        AppCompatButton mCriticSubmit = (AppCompatButton) findViewById(R.id.critic_submit);

        // setup
        criticsRef = FirebaseDatabase.getInstance().getReference(getString(R.string.critic_key));
        conversationRef = FirebaseDatabase.getInstance().getReference(getString(R.string.conversation_key));
        mCriticSubmit.setOnClickListener(onSubmitListener);
    }
}
