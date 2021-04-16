package com.by5388.demo.xml.pull;

import android.content.Context;
import android.util.Log;

import com.by5388.demo.xml.Book;
import com.by5388.demo.xml.BookStore;
import com.by5388.demo.xml.XmlHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator  on 2021/4/15.
 * Android内置的偏好设置也是用这个来解析与生成xml的
 */
public class PullHelper implements XmlHelper {
    public static final String TAG = "PullHelper";


    private InputStream getInputStream(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    @Override
    public void queryXml(Context context, String fileName, ResultCallback callback) {
        try {
            //获取实例
            final XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            final XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            //设置输入流与编码
            final InputStream inputStream = getInputStream(context, fileName);
            xmlPullParser.setInput(inputStream, "UTF-8");
            BookStore bookStore = null;
            Book book = null;
            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        bookStore = new BookStore();
                        break;
                    case XmlPullParser.START_TAG: {
                        final String name = xmlPullParser.getName();
                        switch (name) {
                            case "book":
                                book = new Book();
                                book.mCategory = xmlPullParser.getAttributeValue(0);
                                break;
                            case "title":
                                if (book != null) {
                                    //获取节点内容
                                    book.mTitle = xmlPullParser.nextText();
                                }
                                break;
                            case "price":
                                if (book != null) {
                                    book.mPrice = xmlPullParser.nextText();
                                }
                                break;
                            case "year":
                                if (book != null) {
                                    book.mYear = xmlPullParser.nextText();
                                }
                                break;
                            case "author":
                                if (book != null) {
                                    book.mAuthorList.add(xmlPullParser.nextText());
                                }
                                break;
                            default:
                                Log.d(TAG, "queryXml: unknown xmlTag = " + name);
                                break;
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:
                        final String name = xmlPullParser.getName();
                        if ("book".equals(name)) {
                            if (bookStore != null && book != null) {
                                bookStore.addBook(book);
                                book = null;
                            }
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }

            callback.onSuccess(bookStore);
            inputStream.close();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            callback.onError(e.getLocalizedMessage());
        }
    }
}
