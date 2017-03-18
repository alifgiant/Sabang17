package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buahbatu.sabang17.BusinessDetailActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.model.Business;

import java.util.ArrayList;
import java.util.List;

public class BusinessFragment extends Fragment {

    private RecyclerView mBusinessRecyclerView;
    private List<Business> businesses;

    public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        return new BusinessFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // find views
        businesses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            businesses.add(new Business());
        }

        View root = inflater.inflate(R.layout.fragment_business, container, false);
        mBusinessRecyclerView = (RecyclerView)root.findViewById(R.id.business_recycler);

        // setup recycler
        mBusinessRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBusinessRecyclerView.setAdapter(new BusinessAdapter());

        return root;
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder{
        BusinessViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class BusinessAdapter extends RecyclerView.Adapter<BusinessViewHolder>{
        @Override
        public BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View businessItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business, parent, false);
            return new BusinessViewHolder(businessItem);
        }

        @Override
        public void onBindViewHolder(BusinessViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), BusinessDetailActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return businesses.size();
        }
    }
}
