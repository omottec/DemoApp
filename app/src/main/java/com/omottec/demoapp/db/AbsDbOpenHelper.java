package com.omottec.demoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public abstract class AbsDbOpenHelper extends DatabaseOpenHelper {
    public static final String TAG = "AbsDbOpenHelper";
    private SortedMap<Integer, DbUpgrade> allUpgrades = new TreeMap<>();
    private volatile boolean allUpgradesCreated = false;

    private void createAllUpgrades() {
        onCreateAllUpgrades(allUpgrades);
        allUpgradesCreated = true;
    };

    protected abstract void onCreateAllUpgrades(SortedMap<Integer, DbUpgrade> allUpgrades);

    public AbsDbOpenHelper(Context context, String name, int version) {
        super(context, name, version);
    }

    public AbsDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(Database db) {
        Log.d(TAG, "onCreate Database");
        createAllTables(db, false);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("onUpgrade oldVersion:%d, newVersion:%d", oldVersion, newVersion));
        if (allUpgradesCreated == false) createAllUpgrades();
        SortedMap<Integer, DbUpgrade> upgrades = allUpgrades.subMap(oldVersion, newVersion);
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

    protected abstract void createAllTables(Database db, boolean ifNotExists);

    protected abstract void dropAllTables(Database db, boolean ifExists);
}
