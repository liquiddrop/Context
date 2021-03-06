package com.curtis.context;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Curtis on 8/12/2018.
 */

public class HistoricalEventViewModel extends AndroidViewModel {
    private static final String TAG = "HistoricalEventViewModel";

    private HistoricalEventRepository mRepository;

    private LiveData<List<historical_event>> mAllEvents;

    //private LoadUserCallback mLoadUserCallback;
/*
    private LoadUserCallback createLoadUserCallback() {
        return new LoadUserCallback() {
            @Override
            public void onUserLoaded(LiveData<List<historical_event>> mLoadedEvents) {
                if (mLoadedEvents != null) {
                    mAllEvents = mLoadedEvents;
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "No data available.");
            }
        };
    }
*/
    public HistoricalEventViewModel (Application application) {
        super(application);
        //mLoadUserCallback = createLoadUserCallback();
        mRepository = new HistoricalEventRepository(application);
        mAllEvents = mRepository.getAllEvents();
    }

    LiveData<List<historical_event>> getmAllEvents() { return mAllEvents; }

    LiveData<List<historical_event>> getEventsWithImportance(int importance) {
        return mRepository.getEventsWithImportance(importance);
    }

    LiveData<List<historical_event>> getCountryEvents(String search) {
        return mRepository.getCountryEvents(search);
    }

    LiveData<List<historical_event>> getCityEvents(String search) {
        return mRepository.getCityEvents(search);
    }

    LiveData<List<historical_event>> getYearEvents(int searchYear) {
        return mRepository.getYearEvents(searchYear);
    }

    LiveData<List<historical_event>> getSpecificEvents(String search) {
        return mRepository.getSpecificEvents(search);
    }

    LiveData<List<String>> getAllCountries() {
        return mRepository.getAllCountries();
    }

    LiveData<List<String>> getAllCities() {
        return mRepository.getAllCities();
    }

    public void insert(historical_event event) { mRepository.insert(event); }
}
