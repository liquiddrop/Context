package com.curtis.context;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Curtis on 8/4/2018.
 */
@Dao
public interface historical_event_dao {

    @Query("SELECT * FROM historical_event_table ORDER BY year ASC")
    LiveData<List<historical_event>> getAll();

    //@Query("SELECT * FROM historical_event_table where first_name LIKE  :firstName AND last_name LIKE :lastName")
    //User findByName(String firstName, String lastName);

    @Query("SELECT * FROM historical_event_table where importance LIKE  :importance ORDER BY year ASC")
    LiveData<List<historical_event>> findByImportance(int importance);

    @Query("SELECT * FROM historical_event_table where location_country LIKE  :country ORDER BY year ASC")
    LiveData<List<historical_event>> findByCountry(String country);

    @Query("SELECT * FROM historical_event_table where location_city LIKE  :city ORDER BY year ASC")
    LiveData<List<historical_event>> findByCity(String city);

    @Query("SELECT COUNT(*) from historical_event_table")
    int countUsers();

    @Insert
    void insertAll(historical_event... new_events);

    @Delete
    void delete(historical_event delete_event);
}
