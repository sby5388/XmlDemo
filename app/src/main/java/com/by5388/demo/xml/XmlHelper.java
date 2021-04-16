package com.by5388.demo.xml;

import android.content.Context;

/**
 * @author Administrator  on 2021/4/15.
 */
public interface XmlHelper {


    void queryXml(Context context, String fileName, ResultCallback callback);


    interface ResultCallback {
        void onSuccess(BookStore bookStore);

        void onError(String error);
    }
}
