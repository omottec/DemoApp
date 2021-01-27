package com.omottec.demoapp.ipc.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.omottec.demoapp.Tag;
import com.omottec.logger.Logger;

/**
 * Created by qinbingbing on 9/11/16.
 */
public class BookProvider extends ContentProvider {
    private Context mContext;
    private SQLiteDatabase mBookDb;
    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String AUTHORITY = "com.omottec.demoapp.provider";
    public static final int BOOK_CODE = 1;
    public static final int USER_CODE = 2;

    static {
        mUriMatcher.addURI(AUTHORITY, BookOpenHelper.TABLE_BOOK, BOOK_CODE);
        mUriMatcher.addURI(AUTHORITY, BookOpenHelper.TABLE_USER, USER_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.d(Tag.IPC_PROVIDER, "BookProvider.onCreate");
        Logger.logThread(Tag.IPC_PROVIDER);
        mContext = getContext();
        initDb();
        return true;
    }

    private void initDb() {
        mBookDb = new BookOpenHelper(mContext).getWritableDatabase();
        mBookDb.beginTransaction();
        try {
            mBookDb.execSQL("delete from " + BookOpenHelper.TABLE_BOOK);
            mBookDb.execSQL("delete from " + BookOpenHelper.TABLE_USER);
            mBookDb.execSQL("insert into " + BookOpenHelper.TABLE_BOOK
                    + " values (2, 'Think In Java')");
            mBookDb.execSQL("insert into " + BookOpenHelper.TABLE_BOOK
                    + " values (3, 'Core Java')");
            mBookDb.execSQL("insert into " + BookOpenHelper.TABLE_USER
                    + " values (2, 'Daniel Bond', 1)");
            mBookDb.execSQL("insert into " + BookOpenHelper.TABLE_USER
                    + " values (3, 'Jason Bourne', 1)");
            mBookDb.execSQL("insert into " + BookOpenHelper.TABLE_USER
                    + " values (4, 'Ethan Hunt', 1)");
            mBookDb.setTransactionSuccessful();
        } finally {
            mBookDb.endTransaction();
        }

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(Tag.IPC_PROVIDER, "query");
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table))
            throw new IllegalArgumentException("unsupported uri:" + uri);
        return mBookDb.query(table, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        Log.d(Tag.IPC_PROVIDER, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(Tag.IPC_PROVIDER, "insert");
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table))
            throw new IllegalArgumentException("unsupported uri:" + uri);
        long rowId = mBookDb.insert(table, null, values);
        if (rowId > -1)
            mContext.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(Tag.IPC_PROVIDER, "delete");
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table))
            throw new IllegalArgumentException("unsupported uri:" + uri);
        int count = mBookDb.delete(table, selection, selectionArgs);
        if (count > 0)
            mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(Tag.IPC_PROVIDER, "update");
        String table = getTableByUri(uri);
        if (TextUtils.isEmpty(table))
            throw new IllegalArgumentException("unsupported uri:" + uri);
        int count = mBookDb.update(table, values, selection, selectionArgs);
        if (count > 0)
            mContext.getContentResolver().notifyChange(uri, null);
        return count;
    }

    private String getTableByUri(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case BOOK_CODE:
                return BookOpenHelper.TABLE_BOOK;
            case USER_CODE:
                return BookOpenHelper.TABLE_USER;
            default:
                return null;
        }
    }
}
