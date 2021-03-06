/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.curtis.context;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager}
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    private boolean extraDebug=false;

    public RecyclerView mRecyclerView;
    public TextView mEmptyView;
    protected HistoricalEventListAdapter adapter;
    protected LinearLayoutManager mLayoutManager;
    protected HistoricalEventViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(HistoricalEventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mEmptyView = (TextView) rootView.findViewById(R.id.empty_list_text);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new HistoricalEventListAdapter(inflater);
        Bundle extras = this.getArguments();
        //If there are no arguments assume the want the whole list
        if(extras == null) {
            if(extraDebug) {
                Log.i(TAG, "There we no arguments to the bundle ");
            }

            viewModel.getEventsWithImportance(5).observe(this, new Observer<List<historical_event>>() {
                @Override
                public void onChanged(@Nullable final List<historical_event> events) {
                    // Update the cached copy of the words in the adapter.
                    adapter.setmEvents(events);
                    if(events.isEmpty())
                    {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                        Log.i(TAG, "Important events is empty!");
                    }
                }
            });
        }
        else
        {
            String searchString;
            int searchYear;
            if(extras.containsKey("Country")){
                searchString = extras.getString("Country");
                viewModel.getCountryEvents(searchString).observe(this, new Observer<List<historical_event>>() {
                    @Override
                    public void onChanged(@Nullable final List<historical_event> events) {
                        // Update the cached copy of the words in the adapter.
                        adapter.setmEvents(events);
                        if(events.isEmpty())
                        {
                            mEmptyView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.i(TAG, "Country list is empty");
                        }
                    }
                });
            }
            else if(extras.containsKey("City")){
                searchString = extras.getString("City");
                viewModel.getCityEvents(searchString).observe(this, new Observer<List<historical_event>>() {
                    @Override
                    public void onChanged(@Nullable final List<historical_event> events) {
                        // Update the cached copy of the words in the adapter.
                        adapter.setmEvents(events);
                        if(events.isEmpty())
                        {
                            mEmptyView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.i(TAG, "City list is empty");
                        }
                    }
                });
            }
            else if(extras.containsKey("Year")){
                searchString = "Year " + extras.getString("Year");
                //change string to int and see if it is bc which turns it to a negative year
                if(searchString.toLowerCase().contains("bc"))
                {
                    searchYear = -Integer.parseInt(searchString.replaceAll("[^0-9]", ""));
                }
                else
                {
                    searchYear = Integer.parseInt(searchString.replaceAll("[^0-9]", ""));
                }
                viewModel.getYearEvents(searchYear).observe(this, new Observer<List<historical_event>>() {
                    @Override
                    public void onChanged(@Nullable final List<historical_event> events) {
                        // Update the cached copy of the words in the adapter.
                        adapter.setmEvents(events);
                        if(events.isEmpty())
                        {
                            mEmptyView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.i(TAG, "City list is empty");
                        }
                    }
                });
            }
            else if(extras.containsKey("Event")){
                searchString = extras.getString("Event");
                viewModel.getSpecificEvents(searchString).observe(this, new Observer<List<historical_event>>() {
                    @Override
                    public void onChanged(@Nullable final List<historical_event> events) {
                        // Update the cached copy of the words in the adapter.
                        adapter.setmEvents(events);
                        if(events.isEmpty())
                        {
                            mEmptyView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            Log.i(TAG, "City list is empty");
                        }
                    }
                });
            }
            else {
                searchString = "There was no input!";
            }
            if(extraDebug) {
                Log.i(TAG, "The argument is " + searchString);
            }
        }
        // Set HistoricalEventListAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    public int searchForClosestEvent(int year){
        int position = 0;
        position = adapter.findClosestEventByYear(year);
        return position;
    }

    public void setListPosition(int new_position)
    {
        mRecyclerView.scrollToPosition(new_position);
    }
}
