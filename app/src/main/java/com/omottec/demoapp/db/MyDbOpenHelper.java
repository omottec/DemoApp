package com.omottec.demoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.omottec.demoapp.dao.NoteDao;
import com.omottec.demoapp.dao.UserDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class MyDbOpenHelper extends DatabaseOpenHelper {
    public static final String TAG = "MyDbOpenHelper";
    public static final String DB_NAME = "my";
    public static final int DB_VERSION = 3;
    private static final SortedMap<Integer, DbUpgrade> ALL_UPGRADES = new TreeMap<>();
    static {
        ALL_UPGRADES.put(1, new V1Update());
        ALL_UPGRADES.put(2, new V2Update());
    }

    public MyDbOpenHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }

    public MyDbOpenHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(Database db) {
        Log.d(TAG, "onCreate Database");
        createAllTables(db, false);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("onUpgrade oldVersion:%d, newVersion:%d", oldVersion, newVersion));
        SortedMap<Integer, DbUpgrade> upgrades = ALL_UPGRADES.subMap(oldVersion, newVersion);
        executeUpgrades(db, upgrades);
    }

    private void executeUpgrades(Database db, SortedMap<Integer, DbUpgrade> upgrades) {
        if (upgrades.isEmpty()) return;
        Collection<DbUpgrade> values = upgrades.values();
        try {
            db.beginTransaction();
            for (DbUpgrade upgrade : values)
                upgrade.onUpgrade(db);
            db.setTransactionSuccessful();
        } catch (Throwable t) {
            t.printStackTrace();
            dropAllTables(db, true);
            onCreate(db);
        } finally {
            db.endTransaction();
        }
    }

    private void createAllTables(Database db, boolean ifNotExists) {
        NoteDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
    }

    private void dropAllTables(Database db, boolean ifExists) {
        NoteDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
    }
}
