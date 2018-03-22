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
//        String text = "{\"str\":\"\",\"b\":false}";

        /*String text = "{\n" +
                "\t\"intPrice\": 100,\n" +
                "\t\"intPrice1\": 200\n" +
                "}";*/

        String intText = "{\"intPrice\":0}";

        String integerText = "{\"integerPrice\":0}";

        String stringText = "{\"str\":\"\"}";

        String booleanText = "{\"b\":false}";

        String RefBooleanText = "{\"refBoolean\":false}";

        String phoneList = "{\"data\": {\"phoneList\": [\"18811445061\", \"18811445064\", \"18311045061\", \"18811445063\", \"18811445062\", \"18311045361\"]},\"code\": 0}";

        /*GsonObject gsonObject = new Gson().fromJson(stringText, GsonObject.class);
        mJsonTv.setText(stringText);
        mGsonObjectTv.setText(gsonObject.toString());*/

        ResponseEntity responseEntity = new Gson().fromJson(phoneList, ResponseEntity.class);
        mJsonTv.setText(phoneList);
        mGsonObjectTv.setText(responseEntity.toString());
    }
}
