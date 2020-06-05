package com.curtis.context;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import android.util.Log;
import com.fstyle.library.helper.AssetSQLiteOpenHelperFactory;

/**
 * Created by Curtis on 8/4/2018.
 */

@Database(entities = {historical_event.class}, version = 2, exportSchema = false)
public abstract class HistoricalEventDatabase extends RoomDatabase {

    public static final String TAG = "HistoricalEventDatabase";

    public abstract historical_event_dao HistoricalDao();

    private static HistoricalEventDatabase INSTANCE;

    public static HistoricalEventDatabase getHistoricalEventDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (HistoricalEventDatabase.class){
                if(INSTANCE == null){
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(), HistoricalEventDatabase.class, "historical_events_database.db")
                                    .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                                    .addMigrations(MIGRATION_1_2)
                                    .addMigrations(MIGRATION_2_3)
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.i(TAG, "We are in database migration");
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            Log.i(TAG, "We are in database migration");
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static void destroyInstance() {
            INSTANCE = null;
        }
}
