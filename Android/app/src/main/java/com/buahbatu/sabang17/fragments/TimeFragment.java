package com.buahbatu.sabang17.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.buahbatu.sabang17.InfoDetailActivity;
import com.buahbatu.sabang17.R;
import com.buahbatu.sabang17.firebase.CustomChildEventListener;
import com.buahbatu.sabang17.model.SabangEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeFragment extends Fragment {
    private static final String TAG = TimeFragment.class.getSimpleName();

    private CompactCalendarView mCalendarView;
    private RecyclerView mEventRecycler;
    private TextView mEventMonthText;
    private DatabaseReference calendarRef;

    private Date selectedDate;

    // variable holder
    private List<SabangEvent> dayEventList;
    private List<SabangEvent> allEventList;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonthInteger = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat dateFormatFireBase = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    private CompactCalendarView.CompactCalendarViewListener viewListener = new CompactCalendarView.CompactCalendarViewListener() {
        @Override
        public void onDayClick(Date dateClicked) {
            selectedDate = dateClicked;
            dayEventList.clear();
            mEventRecycler.getAdapter().notifyDataSetChanged();
            List<Event> calendarEvents = mCalendarView.getEvents(selectedDate);
            for (Event event:calendarEvents) {
                addTodayEvent(event);
//                Log.i(TAG, "onDayClick: "+ ((SabangEvent)event.getData()).eventType);
            }
        }

        @Override
        public void onMonthScroll(Date firstDayOfNewMonth) {
            mEventMonthText.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            Log.i(TAG, "onMonthScroll: "+ dateFormatForMonthInteger.format(firstDayOfNewMonth));

            // populate data month
            populateData(dateFormatForMonthInteger.format(firstDayOfNewMonth), false);
        }
    };

    private long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private boolean isExistInList(List<SabangEvent> events, SabangEvent newEvent){
        boolean isFound = false;
        for (SabangEvent sabangEvent : events) {
            if (newEvent != null && TextUtils.equals(sabangEvent.key, newEvent.key)
                    && TextUtils.equals(sabangEvent.eventType, newEvent.eventType)){
                isFound = true;
                break;
            }
        }
        return isFound;
    }

    private void addTodayEvent(Event event){
        String selectedDateString = dateFormatFireBase.format(selectedDate);
        String eventDateString = dateFormatFireBase.format(new Date(event.getTimeInMillis()));
        if (TextUtils.equals(selectedDateString, eventDateString)){
            SabangEvent newEvent = (SabangEvent)event.getData();
            if (!isExistInList(dayEventList, newEvent)){
//            Log.i(TAG, "addTodayEvent: day clicked");
                dayEventList.add(newEvent);
                mEventRecycler.getAdapter().notifyDataSetChanged();
            }
        }
    }

    private void addEventToCalendar(SabangEvent event, boolean isFirstPopulate){
        try {
            // calendar instance
            Calendar calendar = Calendar.getInstance();
            long dayDiff = 0;

            // start date
            Date start = dateFormatFireBase.parse(event.date_start);
            long dateTimeStamp = start.getTime();
            calendar.setTime(start);

            if (!TextUtils.isEmpty(event.date_end)) {
                Date stop = dateFormatFireBase.parse(event.date_end);
                dayDiff = getDateDiff(start, stop, TimeUnit.DAYS);
            }
            for (int i = 0; i <= dayDiff; i++) {
                Event calendarEvent;
                if (TextUtils.equals(event.eventType, getString(R.string.cadre_key))){
                    calendarEvent = new Event(ContextCompat.getColor(getContext(),
                            R.color.colorCadre), dateTimeStamp, event);
                }else{
                    calendarEvent = new Event(ContextCompat.getColor(getContext(),
                            R.color.colorEvent), dateTimeStamp, event);
                }
                mCalendarView.addEvent(calendarEvent, false);
                if (isFirstPopulate) addTodayEvent(calendarEvent);

                // increment date
                calendar.add(Calendar.DATE, 1);
                dateTimeStamp = calendar.getTime().getTime();
            }

        }catch (ParseException ex){
            Log.e(TAG, "onChildAdded: No time start");
        }
    }

    private void populateData(String month, final boolean isFirstPopulate){
        calendarRef.child(month).orderByChild("date_start").addChildEventListener(new CustomChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onEventChildAdded: "+dataSnapshot.getKey());

                // input new critics
                SabangEvent event = SabangEvent.createEventObject(dataSnapshot);
                if (!isExistInList(allEventList, event)) addEventToCalendar(event, isFirstPopulate);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.i(TAG, "onEventChildChanged: "+dataSnapshot.getKey());

                // get critic data
                SabangEvent event = SabangEvent.createEventObject(dataSnapshot);

                for (SabangEvent data : dayEventList) {
                    if (TextUtils.equals(data.key, event.key)){
                        Log.i(TAG, "onEventSame found");
                        data.title = event.title;
                        data.date_start = event.date_start;
                        data.date_end = event.date_end;
                        data.organizer = event.organizer;
                        data.time_start = event.time_start;
                        data.time_end = event.time_end;

                        mEventRecycler.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "onEventChildRemoved: "+dataSnapshot.getKey());

                // get critic data
                String[] keySplit = dataSnapshot.getKey().split("_");
                String key = keySplit[1];
                int selected = -1;

                for (int i = 0; i < dayEventList.size(); i++) {
                    SabangEvent data = dayEventList.get(i);
                    if (TextUtils.equals(data.key, key)){
                        Log.i(TAG, "onCriticSame found");
                        selected = i;
                        break;
                    }
                }

                if (selected != -1){
                    dayEventList.remove(selected);
                    mEventRecycler.getAdapter().notifyItemRemoved(selected);
                }
            }
        });
    }

    public TimeFragment() {
        // Required empty public constructor
    }

    public static TimeFragment newInstance() {
        return new TimeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dayEventList = new ArrayList<>();
        allEventList = new ArrayList<>();
        selectedDate = Calendar.getInstance().getTime();

        // Inflate the layout for this fragment, and find views
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        mEventMonthText= (TextView) view.findViewById(R.id.event_month);
        mCalendarView = (CompactCalendarView)view.findViewById(R.id.calendar_event);
        mEventRecycler = (RecyclerView) view.findViewById(R.id.event_recycler);

        // setup calendar view
        mEventMonthText.setText(dateFormatForMonth.format(selectedDate));
        mCalendarView.setListener(viewListener);

        // setup recycler
        mEventRecycler.setAdapter(new TimeRecyclerAdapter());
        mEventRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        calendarRef = FirebaseDatabase.getInstance().getReference(getString(R.string.calendar_key));

        populateData(dateFormatForMonthInteger.format(selectedDate), true);
        return view;
    }

    class TimeViewHolder extends RecyclerView.ViewHolder{
        TextView itemTime;
        TextView itemTitle;
        TextView itemOrganizer;
        TextView itemType;
        TimeViewHolder(View itemView) {
            super(itemView);
            itemTime = (TextView) itemView.findViewById(R.id.item_time);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemOrganizer = (TextView) itemView.findViewById(R.id.item_organizer);
            itemType = (TextView) itemView.findViewById(R.id.item_type);
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
            final SabangEvent event = dayEventList.get(position);
            String time = event.time_start;
            if (!TextUtils.isEmpty(event.time_end)) time += " - " + event.time_end;

            holder.itemTime.setText(time);
            holder.itemTitle.setText(event.title);
            holder.itemOrganizer.setText(event.organizer);

            if (TextUtils.equals(event.eventType, getString(R.string.event_key))) {
                holder.itemType.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.colorAccent));
                holder.itemType.setText(getString(R.string.title_event));
            }else {
                holder.itemType.setText(getString(R.string.title_cadre));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(getContext(), InfoDetailActivity.class);
                    intent.putExtra(InfoChildFragment.INFO_TYPE_KEY, event.eventType);
                    intent.putExtra(InfoChildFragment.INFO_DATA_KEY, event);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return dayEventList.size();
        }
    }
}
