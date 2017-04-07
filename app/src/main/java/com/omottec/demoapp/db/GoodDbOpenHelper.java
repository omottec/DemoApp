package com.omottec.demoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.omottec.demoapp.dao.GoodDao;
import com.omottec.demoapp.dao.NoteDao;
import com.omottec.demoapp.dao.UserDao;

import org.greenrobot.greendao.database.Database;

import java.util.SortedMap;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class GoodDbOpenHelper extends AbsDbOpenHelper {
    public static final String DB_NAME = "good";
    public static final int DB_VERSION = 2;

    public GoodDbOpenHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }

    public GoodDbOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    protected void onCreateAllUpgrades(SortedMap<Integer, DbUpgrade> allUpgrades) {
        allUpgrades.put(1, new GoodDbV1Upgrade());
    }

    @Override
    protected void createAllTables(Database db, boolean ifNotExists) {
        GoodDao.createTable(db, ifNotExists);
    }

    @Override
    protected void dropAllTables(Database db, boolean ifExists) {
        GoodDao.dropTable(db, ifExists);
    }
}
