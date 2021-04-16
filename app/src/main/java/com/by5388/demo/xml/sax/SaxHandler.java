package com.by5388.demo.xml.sax;

import android.util.Log;

import com.by5388.demo.xml.Book;
import com.by5388.demo.xml.BookStore;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Administrator  on 2021/4/15.
 */
public class SaxHandler extends DefaultHandler {
    public static final String TAG = "SaxHelper";
    private BookStore mBookStore;
    private Book mTempBook;
    private String mTagName;
    private boolean mLog = false;


    public SaxHandler() {
    }

    public BookStore getBookStore() {
        return mBookStore;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        mBookStore = new BookStore();
    }

    /**
     * 读到一个开始标签时调用,第二个参数为标签名,最后一个参数为属性数组
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (mLog) {
            Log.d(TAG, "startElement: uri = " + uri);
            Log.d(TAG, "startElement: localName = " + localName);
            Log.d(TAG, "startElement: qName = " + qName);
            Log.d(TAG, "startElement: attributes = " + attributes);
        }

        if (localName.equals("book")) {
            mTempBook = new Book();
            mTempBook.mCategory = attributes.getValue("category");
        }
        this.mTagName = localName;
    }

    /**
     * 读到到内容,第一个参数为字符串内容,后面依次为起始位置与长度
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (mTagName != null) {
            final String data = new String(ch, start, length);
            switch (mTagName) {
                case "title":
                    mTempBook.mTitle = data;
                    break;
                case "price":
                    mTempBook.mPrice = data;
                    break;
                case "year":
                    mTempBook.mYear = data;
                    break;
                case "author":
                    mTempBook.mAuthorList.add(data);
                    break;
            }
        }
    }

    /**
     * 处理元素结束时触发,这里将对象添加到结合中
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (mLog) {
            Log.d(TAG, "endElement: uri = " + uri);
            Log.d(TAG, "endElement: localName = " + localName);
            Log.d(TAG, "endElement: qName = " + qName);
        }
        if (localName.equals("book")) {
            mBookStore.addBook(mTempBook);
        }
        mTagName = null;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        //结束
    }
}
