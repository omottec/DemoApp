package com.omottec.demoapp.db;

import com.omottec.demoapp.dao.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class V2Update implements DbUpgrade {
    @Override
    public void onUpgrade(Database db) {
        UserDao.createTable(db, false);
    }
}
