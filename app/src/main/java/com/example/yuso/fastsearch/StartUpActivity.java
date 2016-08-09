package com.example.yuso.fastsearch;


import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StartUpActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private SearchView mSearchView;
    private ListView mListView;
    private DataBase dataBase;
    private FrameLayout listFrameLayout;
    private FrameLayout displayFrameLayout;
    private STATE_LIST stateFlag = STATE_LIST.NAME_RTM;
    private TextView motorConnectedText;
    private TextView motorNameText;
    private TextView motorNameRTMText;
    private TextView motorLocationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview_filter);

        try {
            DataBaseCopyist copyist = new DataBaseCopyist(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataBase = new DataBase(getApplicationContext());

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, dataBase.getRTMList()));
        mListView.setTextFilterEnabled(true);
        listFrameLayout = (FrameLayout) findViewById(R.id.search_frame_layout);
        displayFrameLayout = (FrameLayout) findViewById(R.id.display_frame_layout);
        motorConnectedText = (TextView) findViewById(R.id.motor_connection);
        motorNameText = (TextView) findViewById(R.id.motor_name);
        motorNameRTMText = (TextView) findViewById(R.id.motor_name_RTM);
        motorLocationText = (TextView) findViewById(R.id.motor_location);

        setupSearchView();
        setOnItemClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activitytest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        listFrameLayout.setVisibility(View.VISIBLE);
        displayFrameLayout.setVisibility(View.GONE);

        switch (id) {
            case (R.id.search_by_rtm):
                stateFlag = STATE_LIST.NAME_RTM;
                mListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, dataBase.getRTMList()));
                return true;
            case (R.id.search_by_connection):
                stateFlag = STATE_LIST.CONNECTION;
                mListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, dataBase.getConnectedList()));
                return true;
            case (R.id.search_by_name):
                stateFlag = STATE_LIST.NAME;
                mListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, dataBase.getNameList()));
                return true;
            case (R.id.search_by_location):
                stateFlag = STATE_LIST.LOCATION;
                mListView.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, dataBase.getLocationList()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (listFrameLayout.getVisibility() == View.VISIBLE) {
            this.finish();
        } else {
            listFrameLayout.setVisibility(View.VISIBLE);
            displayFrameLayout.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String value = (String) arg0.getAdapter().getItem(arg2);
                List<String> resultList = new ArrayList<String>();;

                if (stateFlag == STATE_LIST.NAME_RTM) {
                    resultList = dataBase.getValuesListByRTM(value);
                } else if (stateFlag == STATE_LIST.CONNECTION) {
                    resultList = dataBase.getValuesListByConnection(value);
                } else if (stateFlag == STATE_LIST.NAME) {
                    resultList = dataBase.getValuesListByName(value);
                } else if (stateFlag == STATE_LIST.LOCATION) {
                    resultList = dataBase.getValuesListLocation(value);
                }

                motorConnectedText.setText(resultList.get(0));
                motorNameText.setText(resultList.get(1));
                motorNameRTMText.setText(resultList.get(2));
                motorLocationText.setText(resultList.get(3));
                listFrameLayout.setVisibility(View.GONE);
                displayFrameLayout.setVisibility(View.VISIBLE);
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

    public static enum STATE_LIST {
        CONNECTION,
        NAME,
        NAME_RTM,
        LOCATION
    }
}

