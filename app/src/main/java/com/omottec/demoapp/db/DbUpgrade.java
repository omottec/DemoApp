package com.omottec.demoapp.db;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public interface DbUpgrade {
    void onUpgrade(Database db);
}
