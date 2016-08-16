package com.example.yuso.fastsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ArrayAdapter;


public class MenuFragment extends Fragment {

    private  OnItemSelectedListener listener;
    private  ArrayAdapter<String> itemsAdapter;
    private DataBase dataBase;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener){      // context instanceof YourActivity
            this.listener = (OnItemSelectedListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PizzaMenuFragment.OnItemSelectedListener");
        }

        Log.i("DEBUG", "Fragment - onAttach()");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBase = new DataBase((Context) listener);

        itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataBase.getRTMList());

        Log.i("DEBUG", "Fragment - onCreate()");
    }


    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
//        void onPizzaItemSelected(int position);
    }
}
