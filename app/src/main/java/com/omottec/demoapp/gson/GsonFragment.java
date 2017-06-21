package com.omottec.demoapp.gson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 21/06/2017.
 */

public class GsonFragment extends Fragment {
    private TextView mJsonTv;
    private TextView mGsonObjectTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_gson, container, false);
        mJsonTv = (TextView) view.findViewById(R.id.tv_json);
        mGsonObjectTv = (TextView) view.findViewById(R.id.tv_gson_object);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String text = "{\n" +
                "\t\"primaryPrice\": 100,\n" +
                "\t\"primaryPrice1\": 200,\n" +
                "\t\"integerPrice\": 0\n" +
                "}";

        /*String text = "{\n" +
                "\t\"primaryPrice\": 100,\n" +
                "\t\"primaryPrice1\": 200\n" +
                "}";*/

        /*String text = "{\n" +
                "\t\"primaryPrice\": 100\n" +
                "}";*/

        /*String text = "{\n" +
                "\t\"primaryPrice\": 100,\n" +
                "\t\"primaryPrice1\": 0\n" +
                "}";*/
        GsonObject gsonObject = new Gson().fromJson(text, GsonObject.class);
        mJsonTv.setText(text);
        mGsonObjectTv.setText(gsonObject.toString());
    }
}
