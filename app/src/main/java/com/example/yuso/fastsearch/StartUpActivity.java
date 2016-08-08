package com.example.yuso.fastsearch;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class StartUpActivity extends Activity implements SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private SearchView mSearchView;
    private ListView mListView;
    DataBase dataBase;
    private List<String> rtmList;
    private FrameLayout listFrameLayout;
    private FrameLayout displayFrameLayout;

    private TextView motorConnectedText;
    private TextView motorNameText;
    private TextView motorNameRTMText;
    private TextView motorLocationText;


//            <string name="motor_connected">CONNECTED: </string>
//    <string name="motor_name">NAME: </string>
//    <string name="motor_name_RTM">RTM_NAME: </string>
//    <string name="motor_location">LOCATION: </string>
//    </resources>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.searchview_filter);

        try {
            DataBaseCopyist copyist = new DataBaseCopyist(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataBase = new DataBase(getApplicationContext());
        rtmList  =  dataBase.getRTMList();

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, rtmList));
        mListView.setTextFilterEnabled(true);
        listFrameLayout    = (FrameLayout) findViewById(R.id.search_frame_layout);
        displayFrameLayout = (FrameLayout) findViewById(R.id.display_frame_layout);


        motorConnectedText = (TextView) findViewById(R.id.motor_connection);
        motorNameText      = (TextView) findViewById(R.id.motor_name);
        motorNameRTMText   = (TextView) findViewById(R.id.motor_name_RTM);
        motorLocationText  = (TextView) findViewById(R.id.motor_location);



        setupSearchView();
        setOnItemClickListener();
    }

    @Override
    public void onBackPressed(){
        if ( listFrameLayout.getVisibility() == View.VISIBLE ) {
            this.finish();
        } else {
            listFrameLayout.setVisibility(View.VISIBLE);
            displayFrameLayout.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String rtmValue = rtmList.get(arg2);
                dataBase.getConnectionByRTM(rtmValue);
                listFrameLayout.setVisibility(View.GONE);
                displayFrameLayout.setVisibility(View.VISIBLE);
                motorConnectedText.setText(dataBase.getConnectionByRTM(rtmValue));
                motorNameText.setText(dataBase.getNameByRTM(rtmValue));
                motorNameRTMText.setText(rtmValue);
                motorLocationText.setText(dataBase.getLocationByRTM(rtmValue));
            }
        });
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("Search");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}

