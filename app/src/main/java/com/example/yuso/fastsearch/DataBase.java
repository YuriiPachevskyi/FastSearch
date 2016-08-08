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

    public String getConnectionByRTM(String rtm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query      = "select Connected from station_motors where NameRTM = \"" + rtm + "\"";
        String result     = new String();
        Cursor res        =  db.rawQuery(query, null);

        if ( res.getCount() == 0 ) {
            db.close();
            res.close();

            return result;
        }

        res.moveToNext();
        result = new String(res.getString(res.getColumnIndex(CONNECTED)));
        db.close();
        res.close();

        return result;
    }

    public String getNameByRTM(String rtm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query      = "select Name from station_motors where NameRTM = \"" + rtm + "\"";
        String result     = new String();
        Cursor res        =  db.rawQuery(query, null);

        if ( res.getCount() == 0 ) {
            db.close();
            res.close();

            return result;
        }

        res.moveToNext();
        result = new String(res.getString(res.getColumnIndex(NAME)));
        db.close();
        res.close();

        return result;
    }

    public String getLocationByRTM(String rtm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query      = "select LocationHardware from station_motors where NameRTM = \"" + rtm + "\"";
        String result     = new String();
        Cursor res        =  db.rawQuery(query, null);

        if ( res.getCount() == 0 ) {
            db.close();
            res.close();

            return result;
        }

        res.moveToNext();
        result = new String(res.getString(res.getColumnIndex(LOCATION)));
        db.close();
        res.close();

        return result;
    }
}

