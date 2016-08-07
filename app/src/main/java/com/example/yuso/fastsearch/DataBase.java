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
    public static final String DATABASE       = "content.db";
    public static final String ELEMENT_ID     = "Id";
    public static final String STATION_MARK   = "StationMark";
    public static final String CONNECTED      = "Connected";
    public static final String RUSSIAN_MARK   = "RussianMark";

    public DataBase(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


    public List<String> getMotorsList() {
        List<String> result = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select StationMark from motors";
        Cursor res =  db.rawQuery(query, null);

        for ( int i = 0; i < res.getCount(); i++ ) {
            res.moveToNext();
            result.add(new String(res.getString(res.getColumnIndex(STATION_MARK))));
        }
        db.close();
        res.close();

        return result;
    }

//    public List<String> getFullTagsList() {
//        List<String> result = new ArrayList<String>();
//
//        for ( int i = 3; i > 0; i-- ) {
//            SQLiteDatabase db = this.getReadableDatabase();
//            String query = "select Content from tag where PriorityLevel = " + i;
//            Cursor res =  db.rawQuery(query, null);
//
//            for ( int j = 0; j < res.getCount(); j++ ) {
//                res.moveToNext();
//                result.add(new String(res.getString(res.getColumnIndex(CONTENT))));
//            }
//            db.close();
//            res.close();
//        }
//
//        return result;
//    }
}

