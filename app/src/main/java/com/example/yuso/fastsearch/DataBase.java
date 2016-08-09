package com.example.yuso.fastsearch;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DataBase extends SQLiteOpenHelper {
    public static String TAG = "DataBase";
    public static final String DATABASE   = "content.db";
    public static final String ELEMENT_ID = "Id";
    public static final String CONNECTED  = "Connected";
    public static final String NAME       = "Name";
    public static final String NAME_RTM   = "NameRTM";
    public static final String LOCATION   = "LocationHardware";

    public DataBase(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public List<String> getConnectedList() {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select Connected from station_motors";
        Cursor res =  db.rawQuery(query, null);

        for ( int i = 0; i < res.getCount(); i++ ) {
            res.moveToNext();
            result.add(new String(res.getString(res.getColumnIndex(CONNECTED))));
        }
        db.close();
        res.close();

        return result;
    }

    public List<String> getNameList() {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select Name from station_motors";
        Cursor res =  db.rawQuery(query, null);

        for ( int i = 0; i < res.getCount(); i++ ) {
            res.moveToNext();
            result.add(new String(res.getString(res.getColumnIndex(NAME))));
        }
        db.close();
        res.close();

        return result;
    }


    public List<String> getRTMList() {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select NameRTM from station_motors";
        Cursor res =  db.rawQuery(query, null);

        for ( int i = 0; i < res.getCount(); i++ ) {
            res.moveToNext();
            result.add(new String(res.getString(res.getColumnIndex(NAME_RTM))));
        }
        db.close();
        res.close();

        return result;
    }

    public List<String> getLocationList() {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select LocationHardware from station_motors";
        Cursor res =  db.rawQuery(query, null);

        for ( int i = 0; i < res.getCount(); i++ ) {
            res.moveToNext();
            result.add(new String(res.getString(res.getColumnIndex(LOCATION))));
        }
        db.close();
        res.close();

        return result;
    }

    public List<String> getValuesListByConnection(String value) {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from station_motors where Connected = \"" + value + "\"";
        Cursor res =  db.rawQuery(query, null);

        res.moveToNext();
        result.add(new String(res.getString(res.getColumnIndex(CONNECTED))));
        result.add(new String(res.getString(res.getColumnIndex(NAME))));
        result.add(new String(res.getString(res.getColumnIndex(NAME_RTM))));
        result.add(new String(res.getString(res.getColumnIndex(LOCATION))));

        db.close();
        res.close();

        return result;
    }

    public List<String> getValuesListByName(String value) {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from station_motors where Name = \"" + value + "\"";
        Cursor res =  db.rawQuery(query, null);

        res.moveToNext();
        result.add(new String(res.getString(res.getColumnIndex(CONNECTED))));
        result.add(new String(res.getString(res.getColumnIndex(NAME))));
        result.add(new String(res.getString(res.getColumnIndex(NAME_RTM))));
        result.add(new String(res.getString(res.getColumnIndex(LOCATION))));

        db.close();
        res.close();

        return result;
    }

    public List<String> getValuesListByRTM(String value) {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from station_motors where NameRTM = \"" + value + "\"";
        Cursor res =  db.rawQuery(query, null);

        res.moveToNext();
        result.add(new String(res.getString(res.getColumnIndex(CONNECTED))));
        result.add(new String(res.getString(res.getColumnIndex(NAME))));
        result.add(new String(res.getString(res.getColumnIndex(NAME_RTM))));
        result.add(new String(res.getString(res.getColumnIndex(LOCATION))));

        db.close();
        res.close();

        return result;
    }

    public List<String> getValuesListLocation(String value) {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from station_motors where LocationHardware = \"" + value + "\"";
        Cursor res =  db.rawQuery(query, null);

        res.moveToNext();
        result.add(new String(res.getString(res.getColumnIndex(CONNECTED))));
        result.add(new String(res.getString(res.getColumnIndex(NAME))));
        result.add(new String(res.getString(res.getColumnIndex(NAME_RTM))));
        result.add(new String(res.getString(res.getColumnIndex(LOCATION))));

        db.close();
        res.close();

        return result;
    }
}

