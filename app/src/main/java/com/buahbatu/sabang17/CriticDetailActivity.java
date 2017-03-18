package com.buahbatu.sabang17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.buahbatu.sabang17.model.Message;

import java.util.ArrayList;
import java.util.List;

public class CriticDetailActivity extends AppCompatActivity {

    private TextView mCriticSubjectText;
    private RecyclerView mCriticRecycler;
    private EditText mCriticMessage;
    private ImageButton mCriticSubmit;

    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic_detail);

        messageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messageList.add(new Message()); // masih dirandom
        }

        // find views
        mCriticSubjectText= (TextView) findViewById(R.id.critic_subject_text);
        mCriticRecycler = (RecyclerView) findViewById(R.id.critic_messages_recycler);
        mCriticMessage = (EditText)findViewById(R.id.critic_message);
        mCriticSubmit = (ImageButton) findViewById(R.id.critic_submit);

        // setup adapter
        mCriticRecycler.setAdapter(new MessageRecyclerAdapter());
        mCriticRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    class MessageViewHolder extends RecyclerView.ViewHolder{
        MessageViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageViewHolder>{
        @Override
        public int getItemViewType(int position) {
            return messageList.get(position).getViewType();
        }

        @Override
        public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            if (viewType == Message.SELF){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_self, parent, false);
            }else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_response_other, parent, false);
            }
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MessageViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }
    }
}
