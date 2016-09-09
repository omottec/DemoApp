// IBookManager.aidl
package com.omottec.demoapp.aidl;

// Declare any non-default types here with import statements
import com.omottec.demoapp.aidl.Book;
import com.omottec.demoapp.aidl.IOnBookAddedListener;

interface IBookManager {
    List<Book> getBooks();

    void addBook(in Book book);

    void registerListener(IOnBookAddedListener listener);

    void unregisterListener(IOnBookAddedListener listener);
}
