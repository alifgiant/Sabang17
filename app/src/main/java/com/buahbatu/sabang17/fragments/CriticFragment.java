package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buahbatu.sabang17.CriticDetailActivity;
import com.buahbatu.sabang17.InputCriticActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.model.Critic;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link CriticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CriticFragment extends Fragment {

    private RecyclerView mCriticRecyclerView;
    private FloatingActionButton mActionButton;
    private List<Critic> criticList;

    public CriticFragment() {
        // Required empty public constructor
    }

    public static CriticFragment newInstance() {
        return new CriticFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_critic, container, false);
        criticList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            criticList.add(new Critic());
        }

        // find views
        mCriticRecyclerView = (RecyclerView) root.findViewById(R.id.critics_recycler);
        mActionButton = (FloatingActionButton) root.findViewById(R.id.critic_add_action);

        // setup recycler
        mCriticRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCriticRecyclerView.setAdapter(new CriticsAdapter());

        // button click
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), InputCriticActivity.class));
            }
        });

        return root;
    }

    class CriticViewHolder extends RecyclerView.ViewHolder{
        CriticViewHolder(View itemView) {
            super(itemView);
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
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), CriticDetailActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return criticList.size();
        }
    }
}
