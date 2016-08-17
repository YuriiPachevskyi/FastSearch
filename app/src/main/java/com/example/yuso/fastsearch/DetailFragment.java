package com.example.yuso.fastsearch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 8/16/16.
 */
public class DetailFragment extends Fragment {
    private TextView motorConnectedText;
    private TextView motorNameText;
    private TextView motorNameRTMText;
    private TextView motorLocationText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        motorConnectedText = (TextView) view.findViewById(R.id.motor_connection);
        motorNameText = (TextView) view.findViewById(R.id.motor_name);
        motorNameRTMText = (TextView) view.findViewById(R.id.motor_name_RTM);
        motorLocationText = (TextView) view.findViewById(R.id.motor_location);
    }

    public void updateView(String value, DataBase dataBase, StartUpActivity.STATE_LIST stateFlag) {
        List<String> resultList = new ArrayList<String>();;

        if (stateFlag == StartUpActivity.STATE_LIST.NAME_RTM) {
            resultList = dataBase.getValuesListByRTM(value);
        } else if (stateFlag == StartUpActivity.STATE_LIST.CONNECTION) {
            resultList = dataBase.getValuesListByConnection(value);
        } else if (stateFlag == StartUpActivity.STATE_LIST.NAME) {
            resultList = dataBase.getValuesListByName(value);
        } else if (stateFlag == StartUpActivity.STATE_LIST.LOCATION) {
            resultList = dataBase.getValuesListLocation(value);
        }

        motorConnectedText.setText(resultList.get(0));
        motorNameText.setText(resultList.get(1));
        motorNameRTMText.setText(resultList.get(2));
        motorLocationText.setText(resultList.get(3));
    }
}
