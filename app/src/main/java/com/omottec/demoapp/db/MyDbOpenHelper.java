package com.omottec.demoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.omottec.demoapp.dao.Note;
import com.omottec.demoapp.dao.NoteDao;
import com.omottec.demoapp.dao.UserDao;

import org.greenrobot.greendao.database.Database;

import java.util.SortedMap;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class MyDbOpenHelper extends AbsDbOpenHelper {
    public static final String DB_NAME = "my";
    public static final int DB_VERSION = 3;

    public MyDbOpenHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }

    public MyDbOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    protected void onCreateAllUpgrades(SortedMap<Integer, DbUpgrade> allUpgrades) {
        allUpgrades.put(1, new V1Update());
        allUpgrades.put(2, new V2Update());
    }

    @Override
    protected void createAllTables(Database db, boolean ifNotExists) {
        NoteDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
    }

    @Override
    protected void dropAllTables(Database db, boolean ifExists) {
        NoteDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
    }
}
