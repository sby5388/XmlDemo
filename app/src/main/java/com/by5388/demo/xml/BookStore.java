package com.by5388.demo.xml;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2021/4/15.
 */
public class BookStore {
    public List<Book> mBookList;

    public BookStore() {
        mBookList = new ArrayList<>();
    }

    public void addBook(Book book) {
        this.mBookList.add(book);
    }

    @Override
    @NonNull
    public String toString() {
        return "BookStore{" +
                "mBookList=" + mBookList +
                '}';
    }
}
