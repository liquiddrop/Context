package com.curtis.context;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.Iterator;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Curtis on 8/12/2018.
 */

public class HistoricalEventListAdapter extends RecyclerView.Adapter<HistoricalEventListAdapter.HistoricalEventViewHolder> {
    private static final String TAG = "HistoricalEventAdapter";
    private boolean extraDebug=false;

    class HistoricalEventViewHolder extends RecyclerView.ViewHolder {
        private final TextView historicalItemView;
        public TimelineView mTimelineView;

        private HistoricalEventViewHolder(View itemView, int viewType, final Context context){
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
            // Define click listener for the ViewHolder's View.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    if(extraDebug) {
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                    historical_event current_event = mEvents.get(getAdapterPosition());
                    //check for blank entry
                    if(current_event.mSummary.isEmpty() || (current_event.mSummary.compareTo(" ")==0) || (current_event.mSummary.compareTo("")==0))
                    {
                        return;
                    }
                    Intent myIntent = new Intent(context, popUp.class);
                    myIntent.putExtra("SUMMARY", current_event.mSummary );
                    myIntent.putExtra("DATE", current_event.mDate_string );
                    myIntent.putExtra("CITY", current_event.mCity);
                    myIntent.putExtra("COUNTRY", current_event.mCountry);
                    myIntent.putExtra("FULL_SUMMARY", current_event.mFull_Summary);
                    if(current_event.mPicture != null) {
                        myIntent.putExtra("PICTURE_PATH", current_event.mPicture);
                    }
                    if(current_event.mPicture != null) {
                        myIntent.putExtra("LINK", current_event.mLink);
                    }
                    context.startActivity(myIntent);
                }
            });
            historicalItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<historical_event> mEvents; //cached copy of events

    HistoricalEventListAdapter(LayoutInflater inflater) {mInflater = inflater;}

    @Override
    public HistoricalEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.text_row_item, parent, false);
        return new HistoricalEventViewHolder(itemView, viewType,parent.getContext());
    }

    @Override
    public void onBindViewHolder(HistoricalEventViewHolder holder, int position) {
        if(extraDebug) {
            Log.d(TAG, "Element " + position + " set.");
        }
        if (mEvents != null) {
            historical_event current = mEvents.get(position);
            if(current.mSummary.isEmpty() || (current.mSummary.compareTo(" ")==0) || (current.mSummary.compareTo("")==0))
            {
                holder.mTimelineView.setMarkerSize(0);
                holder.historicalItemView.setText("");
                if(extraDebug) {
                    Log.d(TAG, "Element " + position + " set blank.");
                }
            }
            else{
                holder.mTimelineView.setMarkerSize(120);
                holder.historicalItemView.setText(current.mDate_string + " " + current.mSummary);
                if(extraDebug) {
                    Log.d(TAG, "Element " + position + " set to " + current.mDate_string);
                }
            }
            //holder.historicalItemView.setText(current.Field2);
        } else {
            // Covers the case of data not being ready yet.
            holder.historicalItemView.setText("No Events Please download database");
        }
    }

    void setmEvents(List<historical_event> hEvents){
        if(hEvents != null)
        {
            mEvents = hEvents;
            addEmptyEventsToSpace();
            notifyDataSetChanged();
        }
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mEvents != null)
            return mEvents.size();
        else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    public int findClosestEventByYear(int year)
    {
        int count = 0;
        if(mEvents == null)
        {
            return count;
        }
        Iterator<historical_event> iterator = mEvents.iterator();
        while (iterator.hasNext()) {
            historical_event event = iterator.next();
            if (year <= event.mYear) {
                return count;
            }
            count++;
        }
        return count;
    }

    //this is a function to add empty events to create spacing
    public void addEmptyEventsToSpace()
    {
        historical_event empty = new historical_event(" ");
        int empty_added=0, time_gap_in_hundreds=0, start_size=0;
        if(mEvents == null || mEvents.size()==1)
        {
            return;
        }
        if(extraDebug) {
            Log.d(TAG, "Start size " + mEvents.size());
        }
        start_size = mEvents.size();
        for(int i=0; i < start_size-1; i++)
        {
            if(extraDebug) {
                Log.d(TAG, "trying to access " + (i + 1 + empty_added) + " and " + (i + empty_added) + " and empty added is " + empty_added);
            }
            //calculate the time difference in hundred of years
            time_gap_in_hundreds = abs(mEvents.get((i+1)+empty_added).mYear - mEvents.get((i)+empty_added).mYear) / 100;
            if(extraDebug) {
                Log.d(TAG, "year 1 is " + mEvents.get((i + 1) + empty_added).mYear + " and year 2 is " + mEvents.get((i) + empty_added).mYear);
                Log.d(TAG, "Time gap in hundreds is " + time_gap_in_hundreds);
            }
            if(time_gap_in_hundreds >= 1)
            {
                //upper bound the ammount of empty space added
                if(time_gap_in_hundreds > 5)
                {
                    time_gap_in_hundreds = 5;
                }
                for(int j=0; j < time_gap_in_hundreds; j++)
                {
                    mEvents.add(((i+1)+empty_added), empty);
                }
                empty_added+=time_gap_in_hundreds;
                if(extraDebug) {
                    Log.d(TAG, "amount added is " + time_gap_in_hundreds + " and new size is " + mEvents.size());
                }
            }
        }
    }
}
