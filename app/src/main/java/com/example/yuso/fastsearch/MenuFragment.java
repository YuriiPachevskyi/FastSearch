package com.example.yuso.fastsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


public class MenuFragment extends Fragment implements SearchView.OnQueryTextListener {

    private  OnItemSelectedListener listener;
    private  ArrayAdapter<String> itemsAdapter;
    private ListView   mListView;
    private SearchView mSearchView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener) {
            this.listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MenuFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,
                new DataBase((Context) listener).getRTMList());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, parent, false);

        mListView   = (ListView) view.findViewById(R.id.list_view);
        mSearchView = (SearchView) view.findViewById(R.id.search_view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ListView lvItems = (ListView) view.findViewById(R.id.list_view);
        lvItems.setAdapter(itemsAdapter);
        mListView.setTextFilterEnabled(true);
        setupSearchView();

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemSelected((String) parent.getAdapter().getItem(position));
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public void changeSearchList(StartUpActivity.STATE_LIST stateFlag, DataBase dataBase) {
        if ( dataBase == null ) {
            Log.e("+++", "nulllllllllllllll");
        }
        if ( stateFlag == StartUpActivity.STATE_LIST.NAME_RTM ) {
            mListView.setAdapter(new ArrayAdapter<String>((Context) listener,
                    android.R.layout.simple_list_item_1, dataBase.getRTMList()));
        } else if ( stateFlag == StartUpActivity.STATE_LIST.CONNECTION ) {
            mListView.setAdapter(new ArrayAdapter<String>((Context) listener,
                    android.R.layout.simple_list_item_1, dataBase.getConnectedList()));
        } else if ( stateFlag == StartUpActivity.STATE_LIST.NAME) {
            mListView.setAdapter(new ArrayAdapter<String>((Context) listener,
                    android.R.layout.simple_list_item_1, dataBase.getNameList()));
        } else if ( stateFlag == StartUpActivity.STATE_LIST.LOCATION ) {
            mListView.setAdapter(new ArrayAdapter<String>((Context) listener,
                    android.R.layout.simple_list_item_1, dataBase.getLocationList()));
        }
    }

    private void setupSearchView() {
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("Search");
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String str);
    }
}
