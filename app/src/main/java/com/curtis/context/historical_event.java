package com.curtis.context;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Curtis on 8/4/2018.
 */
@Entity(tableName = "historical_event_table")
public class historical_event {

        @PrimaryKey()
        public int _id;
        @ColumnInfo(name = "date_string")
        public String mDate_string;
        @ColumnInfo(name = "year")
        public int mYear;
        @ColumnInfo(name = "month")
        public int mMonth;
        @ColumnInfo(name = "day")
        public int mDay;
        @ColumnInfo(name = "location_country")
        public String mCountry;
        @ColumnInfo(name = "location_state")
        public String mState;
        @ColumnInfo(name = "location_city")
        public String mCity;
        @ColumnInfo(name = "location_building")
        public String mBuilding;
        @ColumnInfo(name = "importance")
        public int mImportance;
        @ColumnInfo(name = "summary")
        public String mSummary;
        @ColumnInfo(name = "full_summary")
        public String mFull_Summary;
        @ColumnInfo(name = "picture")
        public String mPicture;
        @ColumnInfo(name = "link")
        public String mLink;

        public historical_event(@NonNull String summary) {this.mSummary = summary;}
        /*
        public int get_id() {
                return _id;
        }
        public void set_id(int _id) {
                this._id = _id;
        }
*/
        /*

        //Full date
        public String getmDate_string() {
                return mDate_string;
        }
        public void setDate_string(String date_string) {
                this.mDate_string = date_string;
        }
        //Year
        public int getmYear() {
                return mYear;
        }
        public void setmYear(int year) {
                this.mYear = year;
        }
        //Month
        public int getmMonth() {
                return mMonth;
        }
        public void setmDate_string(int month) { this.mMonth = month; }
        //Day
        public int getmDay() {
                return mDay;
        }
        public void setmDay(int day) {
                this.mDay = day;
        }
        //location_country
        public String getmCountry() {
                return mCountry;
        }
        public void setmCountry(String location_country) {
                this.mCountry = location_country;
        }
        //location_state
        public String getmState() {
                return mState;
        }
        public void setmState(String location_state) {
                this.mState = location_state;
        }
        //location_city
        public String getmCity() {
                return mCity;
        }
        public void setmCity(String location_city) {
                this.mCity = location_city;
        }
        //location_building
        public String getmBuilding() {
                return mBuilding;
        }
        public void setmBuilding(String location_building) {
                this.mBuilding = location_building;
        }
        //importance
        public String getmImportance() {
                return mImportance;
        }
        public void setmImportance(String importance) {
                this.mImportance = importance;
        }
        //summary
        public String getmSummary() {
                return mSummary;
        }
        public void setmSummary(String summry) {
                this.mSummary = summry;
        }
        //full_summery
        public String getmFull_Summary() {
                return mFull_Summary;
        }
        public void setmFull_Summary(String full_summary) {
                this.mFull_Summary = full_summary;
        }
        //picutre
        public String getmPicture() {
                return mPicture;
        }
        public void setmPicture(String picture) {
                this.mPicture = picture;
        }
        //link
        public String getmLink() {
                return mLink;
        }
        public void setmLink(String link) {
                this.mLink = link;
        }
        */
}
