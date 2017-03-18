package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buahbatu.sabang17.InfoDetailActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.model.Info;

import java.util.ArrayList;
import java.util.List;

public class InfoChildFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private RecyclerView mInfoRecycler;
    private String mInfoId;

    private List<Info> infoList;

    public InfoChildFragment() {
        // Required empty public constructor
    }

    public static InfoChildFragment newInstance(String infoId) {
        InfoChildFragment fragment = new InfoChildFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, infoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInfoId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info_child, container, false);
        infoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            infoList.add(new Info());
        }

        // handle different info
        switch (mInfoId){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
        }

        // setup recycler
        mInfoRecycler = (RecyclerView)root.findViewById(R.id.info_child_recycler);
        mInfoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mInfoRecycler.setAdapter(new InfoChildAdapter());

        // use mInfoId
        return root;
    }

    class InfoChildViewHolder extends RecyclerView.ViewHolder {
        InfoChildViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class InfoChildAdapter extends RecyclerView.Adapter<InfoChildViewHolder>{
        @Override
        public InfoChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
            return new InfoChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(InfoChildViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), InfoDetailActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }
    }
}
