package com.buahbatu.sabang17;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.buahbatu.sabang17.firebase.CustomChildEventListener;
import com.buahbatu.sabang17.fragments.CriticFragment;
import com.buahbatu.sabang17.model.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CriticDetailActivity extends AppCompatActivity{
    private static final String TAG = CriticDetailActivity.class.getSimpleName();
    private static final int rowDistance = 20;

    private int showedItem = 8;

    // views
    private RecyclerView mMessageRecycler;
    private EditText mCriticMessage;

    private List<Message> messageList;

    // Database reference
    DatabaseReference conversationRef;
    FirebaseUser user;

    private void populateChat(int stop){
        conversationRef.orderByChild("timestamp").addChildEventListener(new CustomChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onMessageAdded: "+dataSnapshot.getKey());

                // input new message
                Message message = Message.createMessageObject(dataSnapshot);
                messageList.add(message);

                // notify data
                mMessageRecycler.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onMessageChanged: "+dataSnapshot.getKey());

                // get critic data
                Message message = Message.createMessageObject(dataSnapshot);

                for (Message data : messageList) {
                    if (TextUtils.equals(data.key, message.key)){
                        Log.i(TAG, "onMessageSame found");
                        data.messageSource = message.messageSource;
                        data.text= message.text;
                        data.timestamp = message.timestamp;
                        mMessageRecycler.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onMessageRemoved: "+dataSnapshot.getKey());

                // get critic data
                String key = dataSnapshot.getKey();
                int selected = -1;

                for (int i = 0; i < messageList.size(); i++) {
                    Message data = messageList.get(i);
                    if (TextUtils.equals(data.key, key)){
                        Log.i(TAG, "onCriticSame found");
                        selected = i;
                        break;
                    }
                }

                if (selected != -1){
                    messageList.remove(selected);
                    mMessageRecycler.getAdapter().notifyItemRemoved(selected);
                }
            }
        });
    }

    private View.OnClickListener submitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(mCriticMessage.getText())){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String message = mCriticMessage.getText().toString();
                if (user != null) {
                    conversationRef.push().setValue(new Message(user.getUid(), message)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(CriticDetailActivity.this, "Pesan Terkirim", Toast.LENGTH_SHORT).show();
                                mCriticMessage.setText("");
                            }
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic_detail);

        messageList = new ArrayList<>();

        // find views
        TextView mCriticSenderNameText = (TextView) findViewById(R.id.critic_subject_text);
        mMessageRecycler = (RecyclerView) findViewById(R.id.critic_messages_recycler);
        mCriticMessage = (EditText)findViewById(R.id.critic_message);
        ImageButton mCriticSubmitButton = (ImageButton) findViewById(R.id.critic_submit);

        // setup views
        String conversationSenderName = getIntent().getStringExtra(CriticFragment.CONVERSATION_NAME_KEY);
        String conversationSenderOrigin = getIntent().getStringExtra(CriticFragment.CONVERSATION_ORIGIN_KEY);
        mCriticSenderNameText.setText(String.format(Locale.US, "%s - %s",
                conversationSenderName, conversationSenderOrigin));
        mCriticSubmitButton.setOnClickListener(submitClickListener);

        // setup adapter
        mMessageRecycler.setAdapter(new MessageRecyclerAdapter());
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // setup database
        String conversationID = getIntent().getStringExtra(CriticFragment.CONVERSATION_KEY);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            conversationRef = FirebaseDatabase.getInstance()
                    .getReference(getString(R.string.conversation_key)).child(conversationID);

        /*MASIH PERLU PERBAIKAN*/
        populateChat(showedItem);
        showedItem += rowDistance;  // move row distance
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        MessageViewHolder(View itemView) {
            super(itemView);
            messageText = (TextView)itemView.findViewById(R.id.message_text);
        }
    }

    private class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder>{
        @Override
        public int getItemViewType(int position) {
            if (TextUtils.equals(messageList.get(position).messageSource, user.getUid())){
                return Message.SELF;
            } else{
                return Message.OTHER;
            }
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == Message.SELF){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_self, parent, false);
            }else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_other, parent, false);
            }
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {
            holder.messageText.setText(messageList.get(position).text);
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }
    }
}
