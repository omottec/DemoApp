package com.omottec.demoapp.db;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class GoodDbV1Upgrade implements DbUpgrade {
    @Override
    public void onUpgrade(Database db) {
        db.execSQL("alter table GOOD add column DESC text");
    }
}
