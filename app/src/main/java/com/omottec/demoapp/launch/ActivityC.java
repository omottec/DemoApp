package com.omottec.demoapp.launch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.omottec.demoapp.R;

/**
 * Created by omottec on 11/04/2019.
 */

public class ActivityC extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        TextView tv = findViewById(R.id.tv);
        tv.setText("ActivityC");
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityC.this, ActivityA.class);
                startActivity(intent);
            }
        });
    }
}
