package com.omottec.demoapp.ipc;

import android.os.Parcel;
import android.os.Parcelable;

import com.omottec.demoapp.aidl.Book;

import java.io.Serializable;

/**
 * Created by qinbingbing on 9/8/16.
 */
public class User implements Parcelable, Serializable {
    public int id;
    public String name;
    public boolean male;
    public Book book;

    public User() {
    }

    public User(int id, String name, boolean male) {
        this.id = id;
        this.name = name;
        this.male = male;
    }

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        male = in.readInt() == 1;
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(male ? 1 : 0);
        dest.writeParcelable(book, 0  );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return String.format("User{id:%d, name:%s, male:%s, book:%s, hashCode:%s}",
                id, name, male, book, hashCode());
    }
}
