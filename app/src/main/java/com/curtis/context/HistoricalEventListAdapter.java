package com.curtis.context;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.util.Log;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Curtis on 8/12/2018.
 */

public class HistoricalEventListAdapter extends RecyclerView.Adapter<HistoricalEventListAdapter.HistoricalEventViewHolder> {
    private static final String TAG = "HistoricalEventAdapter";

    class HistoricalEventViewHolder extends RecyclerView.ViewHolder {
        private final TextView historicalItemView;
        public TimelineView mTimelineView;

        private HistoricalEventViewHolder(View itemView, int viewType, final Context context){
            super(itemView);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
            // Define click listener for the ViewHolder's View.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    historical_event current_event = mEvents.get(getAdapterPosition());
                    Intent myIntent = new Intent(context, popUp.class);
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
        Log.d(TAG, "Element " + position + " set.");
        if (mEvents != null) {
            historical_event current = mEvents.get(position);
            holder.historicalItemView.setText(current.mDate_string + " " + current.mSummary);
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
}
