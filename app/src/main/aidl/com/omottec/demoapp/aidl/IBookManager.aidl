// IBookManager.aidl
package com.omottec.demoapp.aidl;

// Declare any non-default types here with import statements
import com.omottec.demoapp.aidl.Book;
import com.omottec.demoapp.aidl.IOnBookAddedListener;

interface IBookManager {
    List<Book> getBooks();

    void addBookIn(in Book book);

    void addBookOut(out Book book);

    void addBookInOut(inout Book book);

    void registerListener(IOnBookAddedListener listener);

    void unregisterListener(IOnBookAddedListener listener);
}
