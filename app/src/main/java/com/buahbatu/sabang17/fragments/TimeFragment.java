package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buahbatu.sabang17.InfoDetailActivity;
import com.buahbatu.sabang17.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeFragment extends Fragment {

    private CompactCalendarView mCalendarView;
    private RecyclerView mEventRecycler;
    private TextView mEventMonthText;

    // variable holder
    private List<Event> eventList;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    private CompactCalendarView.CompactCalendarViewListener viewListener = new CompactCalendarView.CompactCalendarViewListener() {
        @Override
        public void onDayClick(Date dateClicked) {

        }

        @Override
        public void onMonthScroll(Date firstDayOfNewMonth) {
            mEventMonthText.setText(dateFormatForMonth.format(firstDayOfNewMonth));
        }
    };

    public TimeFragment() {
        // Required empty public constructor
    }

    public static TimeFragment newInstance() {
        return new TimeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        eventList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            eventList.add(new Event(Color.GREEN, 1433701251000L, "Some extra data that I want to store."));
        }

        // Inflate the layout for this fragment, and find views
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        mEventMonthText= (TextView) view.findViewById(R.id.event_month);
        mCalendarView = (CompactCalendarView)view.findViewById(R.id.calendar_event);
        mEventRecycler = (RecyclerView) view.findViewById(R.id.event_recycler);

        // setup calendar view
        mEventMonthText.setText(dateFormatForMonth.format(mCalendarView.getFirstDayOfCurrentMonth()));
        mCalendarView.setListener(viewListener);

        // setup recycler
        mEventRecycler.setAdapter(new TimeRecyclerAdapter());
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    class TimeViewHolder extends RecyclerView.ViewHolder{
        public TimeViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class TimeRecyclerAdapter extends RecyclerView.Adapter<TimeViewHolder>{
        @Override
        public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
            return new TimeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TimeViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), InfoDetailActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }
    }
}
