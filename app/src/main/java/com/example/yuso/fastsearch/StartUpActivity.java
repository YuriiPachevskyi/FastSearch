package com.example.yuso.fastsearch;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StartUpActivity extends AppCompatActivity implements MenuFragment.OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    private MenuFragment menuFrameLayout;
    private DetailFragment detailFragment;
    private DataBase       dataBase;
    private STATE_LIST stateFlag = STATE_LIST.NAME_RTM;

    int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dataBase = new DataBase(getApplicationContext());

        orientation = getResources().getConfiguration().orientation;
        menuFrameLayout = new MenuFragment();
        detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_fragment);
        try {
            DataBaseCopyist copyist = new DataBaseCopyist(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if( orientation == Configuration.ORIENTATION_PORTRAIT ){
            if ( savedInstanceState == null ) {
                ft.add(R.id.fragment_container, menuFrameLayout);
            }
        } else {
            ft.add(R.id.menu_fragment, menuFrameLayout);
        }
        ft.commit();
    }

    @Override
    public void onItemSelected(String str) {
        Log.e(TAG, str);

        if( detailFragment != null ){
            detailFragment.updateView(str, dataBase, stateFlag);
        }

        if( orientation == Configuration.ORIENTATION_PORTRAIT ) {
            getWindow().findViewById(R.id.fragment_container).setVisibility(View.GONE);
            getWindow().findViewById(R.id.detail_fragment).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activitytest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case (R.id.search_by_rtm):;
                menuFrameLayout.changeSearchList(stateFlag = STATE_LIST.NAME_RTM, dataBase);
                return true;
            case (R.id.search_by_connection):
                menuFrameLayout.changeSearchList(stateFlag = STATE_LIST.CONNECTION, dataBase);
                return true;
            case (R.id.search_by_name):
                menuFrameLayout.changeSearchList(stateFlag = STATE_LIST.NAME, dataBase);
                return true;
            case (R.id.search_by_location):
                menuFrameLayout.changeSearchList(stateFlag = STATE_LIST.LOCATION, dataBase);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getWindow().findViewById(R.id.fragment_container).getVisibility() == View.VISIBLE) {
            this.finish();
        } else {
            getWindow().findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
            getWindow().findViewById(R.id.detail_fragment).setVisibility(View.GONE);
        }
    }


    public static enum STATE_LIST {
        CONNECTION,
        NAME,
        NAME_RTM,
        LOCATION
    }
}

