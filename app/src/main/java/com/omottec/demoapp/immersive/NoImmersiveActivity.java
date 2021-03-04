package com.omottec.demoapp.immersive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.omottec.demoapp.R;

/**
 * Created by qinbingbing on 12/07/2018.
 */

public class NoImmersiveActivity extends AppCompatActivity {

    public static void show(Context context) {
        Intent intent = new Intent(context, NoImmersiveActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_no_immersive);
    }
}
