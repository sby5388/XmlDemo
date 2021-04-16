package com.by5388.demo.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2021/4/15.
 */
public class Book {
    public String mTitle;
    public String mYear;
    public String mPrice;
    public List<String> mAuthorList = new ArrayList<>();
    public String mCategory;

    @Override
    public String toString() {
        return "Book{" +
                "mTitle='" + mTitle + '\'' +
                ", mYear='" + mYear + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mAuthorList=" + mAuthorList +
                ", mCategory='" + mCategory + '\'' +
                '}';
    }
}
