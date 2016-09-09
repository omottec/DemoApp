// IOnBookAddedListener.aidl
package com.omottec.demoapp.aidl;

// Declare any non-default types here with import statements
import com.omottec.demoapp.aidl.Book;

interface IOnBookAddedListener {
    void onBookAdded(in Book book);
}
