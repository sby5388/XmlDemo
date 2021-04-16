package com.by5388.demo.xml.sax;

import android.content.Context;

import com.by5388.demo.xml.BookStore;
import com.by5388.demo.xml.XmlHelper;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author Administrator  on 2021/4/15.
 */
public class SaxHelper implements XmlHelper {
    @Override
    public void queryXml(Context context, String fileName, ResultCallback callback) {
        try {
            //获取文件资源建立输入流对象
            final InputStream inputStream = getInputStream(context, fileName);
            //创建XML解析处理器
            final SaxHandler saxHandler = new SaxHandler();
            //得到SAX解析工厂
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            //创建xml解析器
            final SAXParser saxParser = factory.newSAXParser();
            //将xml解析处理器分配给解析器,对文档进行解析,将事件发送给处理器
            saxParser.parse(inputStream, saxHandler);
            inputStream.close();
            final BookStore bookStore = saxHandler.getBookStore();
            callback.onSuccess(bookStore);
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError(e.getLocalizedMessage());
        }
    }

    private InputStream getInputStream(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }
}
