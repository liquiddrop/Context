package com.curtis.context;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Curtis on 8/12/2018.
 */

public class HistoricalEventRepository {
    //private AppExecutors mAppExecutors;
    private historical_event_dao mEventDao;
    private LiveData<List<historical_event>> mAllEvents;

    HistoricalEventRepository(Application application){
        //mAppExecutors = appExecutors;
        HistoricalEventDatabase db = HistoricalEventDatabase.getHistoricalEventDatabase(application);
        mEventDao = db.HistoricalDao();
        mAllEvents = mEventDao.getAll();
    }


    LiveData<List<historical_event>> getAllEvents() {
        return mAllEvents;
    }

    LiveData<List<historical_event>> getEventsWithImportance(int importance) {
        return mEventDao.findByImportance(importance);
    }

    LiveData<List<historical_event>> getCountryEvents(String search) {
        return mEventDao.findByCountry(search);
    }

    LiveData<List<historical_event>> getCityEvents(String search) {
        return mEventDao.findByCity(search);
    }

    LiveData<List<historical_event>> getYearEvents(int searchYear) {
        return mEventDao.findByYear(searchYear);
    }

    LiveData<List<historical_event>> getSpecificEvents(String search) {
        return mEventDao.findByEvent(search);
    }

    LiveData<List<String>> getAllCountries() {
        return mEventDao.getAllCountries();
    }

    LiveData<List<String>> getAllCities() {
        return mEventDao.getAllCities();
    }
    /*
    void getAllEvents(LoadUserCallback callback) {
        final WeakReference<LoadUserCallback> loadUserCallback = new WeakReference<>(callback);

        // request the user on the I/O thread
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mAllEvents = mEventDao.getAll();
                // notify on the main thread
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        final LoadUserCallback userCallback = loadUserCallback.get();
                        if (userCallback == null) {
                            return;
                        }
                        if (mAllEvents == null) {
                            userCallback.onDataNotAvailable();
                        } else {
                            userCallback.onUserLoaded(mAllEvents);
                        }
                    }
                });
            }
        });
    }
    */
    public void insert (historical_event hevent) {
        new insertAsyncTask(mEventDao).execute(hevent);
    }

    private static class insertAsyncTask extends AsyncTask<historical_event, Void, Void> {

        private historical_event_dao mAsyncTaskDao;

        insertAsyncTask(historical_event_dao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final historical_event... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
