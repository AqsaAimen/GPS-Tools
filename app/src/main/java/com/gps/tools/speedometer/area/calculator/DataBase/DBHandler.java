package com.gps.tools.speedometer.area.calculator.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.gps.tools.speedometer.area.calculator.Model.SpeedoMeterHistory;
import com.gps.tools.speedometer.area.calculator.Model.Trip;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tripDetails.db";
    private static final String TABLE_HISTORY = "history";
    private static final String KEY_ID = "id";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_END_TIME = "endTime";
    private static final String KEY_END_DATE = "endDate";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_TIME_TO_DRIVE = "timeToDrive";
    private static final String KEY_MAX_SPEED = "maxSpeed";
    private static final String KEY_AVG_SPEED = "avgSpeed";
    private static final String KEY_START_LAT = "startLat";
    private static final String KEY_START_LNG = "startLng";
    private static final String KEY_END_LAT = "endLat";
    private static final String KEY_END_LNG = "endLng";

    private static final String TABLE_TRIP = "trip";
    private static final String KEY_TRIP_ID = "tripID";
    private static final String KEY_limit = "speedLimit";
    private static final String KEY_destination = "destination";


    public DBHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_START_TIME + " TEXT, "
                + KEY_START_DATE + " TEXT, "
                + KEY_END_TIME + " TEXT, "
                + KEY_END_DATE + " TEXT, "
                + KEY_DISTANCE + " TEXT, "
                + KEY_TIME_TO_DRIVE + " TEXT, "
                + KEY_MAX_SPEED + " TEXT, "
                + KEY_AVG_SPEED + " TEXT, "
                + KEY_START_LAT + " TEXT, "
                + KEY_START_LNG + " TEXT, "
                + KEY_END_LAT + " TEXT, "
                + KEY_END_LNG + " TEXT" + ")";
        String CREATE_TRIP_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_TRIP + "("
                + KEY_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_limit + " TEXT, "
                + KEY_destination + " TEXT" + ")";
        try {
            sqLiteDatabase.execSQL(CREATE_HISTORY_TABLE);
            sqLiteDatabase.execSQL(CREATE_TRIP_TABLE);
        } catch (SQLException e) {

        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);

        onCreate(sqLiteDatabase);

    }

    public void addTrip(Trip trip) {
        Log.e("db insertion", "in insert function");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_limit, trip.getSpeedLimit());
        values.put(KEY_destination, trip.getDestination());
        try {
            db.insert(TABLE_TRIP, null, values);
            db.close();
        } catch (SQLException e) {

        }
    }

    public ArrayList<Trip> getAllTrips() {

        ArrayList<Trip> trips = new ArrayList<Trip>();

        String selectQuery = "SELECT  * FROM " + TABLE_TRIP;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trip item = new Trip(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2));

                trips.add(item);
            } while (cursor.moveToNext());
        }

        return trips;
    }

    public void deleteRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, KEY_ID + "=" + id, null);
    }

    public void deleteRowFromTrip(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRIP, KEY_TRIP_ID + "=" + id, null);
    }

    public ArrayList<SpeedoMeterHistory> getAllHistory() {

        ArrayList<SpeedoMeterHistory> historyList = new ArrayList<SpeedoMeterHistory>();

        String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SpeedoMeterHistory item = new SpeedoMeterHistory(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12));

                historyList.add(item);
            } while (cursor.moveToNext());
        }

        return historyList;
    }

    public void addHistory(SpeedoMeterHistory speedoMeterHistory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_START_TIME, speedoMeterHistory.getStartTime());
        values.put(KEY_START_DATE, speedoMeterHistory.getStartDate());
        values.put(KEY_END_TIME, speedoMeterHistory.getEndTime());
        values.put(KEY_END_DATE, speedoMeterHistory.getEndDate());
        values.put(KEY_DISTANCE, speedoMeterHistory.getDistance());
        values.put(KEY_TIME_TO_DRIVE, speedoMeterHistory.getTimeToDrive());
        values.put(KEY_MAX_SPEED, speedoMeterHistory.getMaxSpeed());
        values.put(KEY_AVG_SPEED, speedoMeterHistory.getAvgSpeed());
        values.put(KEY_START_LAT, speedoMeterHistory.getStartLat());
        values.put(KEY_START_LNG, speedoMeterHistory.getStartLng());
        values.put(KEY_END_LAT, speedoMeterHistory.getEndLat());
        values.put(KEY_END_LNG, speedoMeterHistory.getEndLng());
        try {
            db.insert(TABLE_HISTORY, null, values);
            db.close();
        } catch (SQLException e) {

        }
    }


}
