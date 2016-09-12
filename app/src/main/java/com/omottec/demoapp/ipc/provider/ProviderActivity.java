package com.omottec.demoapp.ipc.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.aidl.Book;
import com.omottec.demoapp.ipc.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinbingbing on 9/12/16.
 */
public class ProviderActivity extends FragmentActivity {
    private TextView mTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_text);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("ProviderActivity");
        new AsyncTask<Void, Void, List<Book>>() {

            @Override
            protected List<Book> doInBackground(Void... params) {
                Uri uri = Uri.parse("content://com.omottec.demoapp.provider/book");
                ContentValues values = new ContentValues();
                values.put(BaseColumns._ID, 4);
                values.put(BookOpenHelper.BOOK_COLUMN_NAME, "Effective Java");
                ProviderActivity.this.getContentResolver().insert(uri, values);
                Cursor cursor = ProviderActivity.this.getContentResolver().query(uri, null, null, null, null);
                List<Book> books = new ArrayList<Book>();
                if (cursor != null) {
                    try {
                        while (cursor.moveToNext()) {
                            int bookId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                            String bookName = cursor.getString(cursor.getColumnIndex(BookOpenHelper.BOOK_COLUMN_NAME));
                            books.add(new Book(bookId, bookName));
                        }
                    } finally {
                        cursor.close();
                    }
                }
                return books;
            }

            @Override
            protected void onPostExecute(List<Book> books) {
                Log.d(Tag.IPC_PROVIDER, "books:" + books);
            }
        }.execute();

        new AsyncTask<Void, Void, List<User>>() {

            @Override
            protected List<User> doInBackground(Void... params) {
                Uri uri = Uri.parse("content://com.omottec.demoapp.provider/user");
                Cursor cursor = ProviderActivity.this.getContentResolver().query(uri, null, null, null, null);
                List<User> users = new ArrayList<User>();
                if (cursor != null) {
                    try {
                        while (cursor.moveToNext()) {
                            int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                            String name = cursor.getString(cursor.getColumnIndex(BookOpenHelper.USER_COLUMN_NAME));
                            int sex = cursor.getInt(cursor.getColumnIndex(BookOpenHelper.USER_COLUMN_SEX));
                            users.add(new User(id, name, sex == 1));
                        }
                    } finally {
                        cursor.close();
                    }
                }
                return users;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                Log.d(Tag.IPC_PROVIDER, "users:" + users);
            }
        }.execute();
    }

}
