package com.omottec.demoapp.gson;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 21/06/2017.
 */

public class GsonFragment extends Fragment {
    private TextView mJsonTv;
    private TextView mGsonObjectTv;
    private TextView mGenericTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_gson, container, false);
        mJsonTv = (TextView) view.findViewById(R.id.tv_json);
        mGsonObjectTv = (TextView) view.findViewById(R.id.tv_gson_object);
        mGenericTv = view.findViewById(R.id.tv_gson_generic);
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

        String skuStr = "{\"code\":0,\"data\":{\"skuid\":111,\"name\":\"阿克苏苹果\",\"price\":10.99}}";
        Response<GoodsSku> response = new Gson().fromJson(skuStr,
                new TypeToken<Response<GoodsSku>>(){}.getType());
        GoodsSku goodsSku = response.data;
        mGenericTv.setText(goodsSku.toString());

    }
}
