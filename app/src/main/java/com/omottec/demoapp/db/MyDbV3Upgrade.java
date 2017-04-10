package com.omottec.demoapp.db;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qinbingbing on 10/04/2017.
 */

public class MyDbV3Upgrade implements DbUpgrade {
    @Override
    public void onUpgrade(Database db) {
        db.execSQL("alter table NOTE add column TYPE text");
    }
}
