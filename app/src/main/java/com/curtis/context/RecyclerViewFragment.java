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


import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager}
 */
public class RecyclerViewFragment extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    public RecyclerView mRecyclerView;
    public TextView mEmptyView;
    protected HistoricalEventListAdapter adapter;
    protected LinearLayoutManager mLayoutManager;
    protected HistoricalEventViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getContext().deleteDatabase("historical_events_database.db");
        Log.i(TAG, "Entering onCreate");
        viewModel = ViewModelProviders.of(this).get(HistoricalEventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "Entering onCreateView");
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
            Log.i(TAG, "There we no arguments to the bundle ");

            viewModel.getEventsWithImportance(5).observe(this, new Observer<List<historical_event>>() {
                @Override
                public void onChanged(@Nullable final List<historical_event> events) {
                    // Update the cached copy of the words in the adapter.
                    Log.i(TAG, "In get events with importance");
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
            Log.i(TAG, "There were arguenmts passed into the fragment ");
            String searchString;
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
                viewModel.getmCityEvents(searchString).observe(this, new Observer<List<historical_event>>() {
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
            Log.i(TAG, "The arguenmt is " + searchString);
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
        //mRecyclerView.smoothScrollToPosition(new_position);
        //((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(new_position,0);
        //((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPosition(new_position);
        //((LinearLayoutManager) mRecyclerView.getLayoutManager()).smoothScrollToPosition(mRecyclerView,null,new_position);
        //mLayoutManager.scrollToPositionWithOffset(new_position,0);
    }
}
