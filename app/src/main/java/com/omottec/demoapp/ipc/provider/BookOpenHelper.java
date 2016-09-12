package com.omottec.demoapp.ipc.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by qinbingbing on 9/11/16.
 */
public class BookOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "book.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_BOOK = "book";
    public static final String TABLE_USER = "user";
    public static final String BOOK_COLUMN_NAME = "name";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_SEX = "sex";


    private static final String CREATE_TABLE_BOOK = "CREATE TABLE IF NOT EXISTS " + TABLE_BOOK
            + "(" + BaseColumns._ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + BOOK_COLUMN_NAME + " TEXT)";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER
            + "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_COLUMN_NAME + " TEXT,"
            + USER_COLUMN_SEX + " INTEGER)";

    public BookOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(CREATE_TABLE_BOOK);
            db.execSQL(CREATE_TABLE_USER);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }
}
