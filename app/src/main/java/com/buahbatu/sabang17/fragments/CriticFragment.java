package com.buahbatu.sabang17.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buahbatu.sabang17.CriticDetailActivity;
import com.buahbatu.sabang17.InputCriticActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.firebase.CustomChildEventListener;
import com.buahbatu.sabang17.model.Critic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CriticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CriticFragment extends Fragment {

    public static final String CONVERSATION_KEY = "key";
    public static final String CONVERSATION_NAME_KEY = "name";
    public static final String CONVERSATION_ORIGIN_KEY = "origin";

    private static final String TAG = CriticFragment.class.getSimpleName();
    private static final int rowDistance = 8;

    private int showedItem = 8;

    private RecyclerView mCriticRecyclerView;
    private List<Critic> criticList;

    // Database reference
    DatabaseReference criticsRef;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            criticsRef = FirebaseDatabase.getInstance()
                    .getReference(getString(R.string.critic_key)).child(user.getUid());
    }

    public CriticFragment() {
        // Required empty public constructor
    }

    public static CriticFragment newInstance() {
        return new CriticFragment();
    }

    private void populateData (int stop){
        Log.i(TAG, "populateData begin: ");

        criticsRef.orderByChild("message").limitToFirst(rowDistance).addChildEventListener(new CustomChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onCriticChildAdded: "+dataSnapshot.getKey());

                // input new critics
                Critic critic = Critic.createCriticObject(dataSnapshot);
                criticList.add(critic);

                // notify data
                mCriticRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onCriticChildChanged: "+dataSnapshot.getKey());

                // get critic data
                Critic critic = Critic.createCriticObject(dataSnapshot);

                for (Critic data : criticList) {
                    if (TextUtils.equals(data.key, critic.key)){
                        Log.i(TAG, "onCriticSame found");
                        data.name = critic.name;
                        data.origin= critic.origin;
                        data.contact = critic.contact;
                        data.message= critic.message;
                        mCriticRecyclerView.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onCriticChildRemoved: "+dataSnapshot.getKey());

                // get critic data
                String key = dataSnapshot.getKey();
                int selected = -1;

                for (int i = 0; i < criticList.size(); i++) {
                    Critic data = criticList.get(i);
                    if (TextUtils.equals(data.key, key)){
                        Log.i(TAG, "onCriticSame found");
                        selected = i;
                        break;
                    }
                }

                if (selected != -1){
                    criticList.remove(selected);
                    mCriticRecyclerView.getAdapter().notifyItemRemoved(selected);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_critic, container, false);
        criticList = new ArrayList<>();

        // find views
        mCriticRecyclerView = (RecyclerView) root.findViewById(R.id.critics_recycler);
        FloatingActionButton mAddActionButton = (FloatingActionButton) root.findViewById(R.id.critic_add_action);

        // setup recycler
        mCriticRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCriticRecyclerView.setAdapter(new CriticsAdapter());

        // button click
        mAddActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InputCriticActivity.class));
            }
        });

        /*MASIH PERLU PERBAIKAN*/
        populateData(showedItem);
        showedItem += rowDistance;  // move row distance

        return root;
    }

    class CriticViewHolder extends RecyclerView.ViewHolder{
        TextView criticMessage;
        TextView criticName;
        TextView criticOrigin;
        CriticViewHolder(View itemView) {
            super(itemView);
            criticMessage = (TextView)itemView.findViewById(R.id.critic_message);
            criticName = (TextView)itemView.findViewById(R.id.critic_name);
            criticOrigin = (TextView)itemView.findViewById(R.id.critic_origin);
        }
    }

    private class CriticsAdapter extends RecyclerView.Adapter<CriticViewHolder>{
        @Override
        public CriticViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View criticItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_critics, parent, false);
            return new CriticViewHolder(criticItem);
        }

        @Override
        public void onBindViewHolder(CriticViewHolder holder, int position) {

            final Critic data = criticList.get(position);
            // setup view data
            holder.criticMessage.setText(data.message);
            holder.criticName.setText(data.name);
            holder.criticOrigin.setText(data.origin);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent moveIntent = new Intent(getContext(), CriticDetailActivity.class);
                    moveIntent.putExtra(CONVERSATION_KEY, data.key);
                    moveIntent.putExtra(CONVERSATION_NAME_KEY, data.name);
                    moveIntent.putExtra(CONVERSATION_ORIGIN_KEY, data.origin);
                    startActivity(moveIntent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return criticList.size();
        }
    }
}
