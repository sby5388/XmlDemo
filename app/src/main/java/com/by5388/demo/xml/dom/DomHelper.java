package com.by5388.demo.xml.dom;

import android.content.Context;
import android.support.annotation.WorkerThread;

import com.by5388.demo.xml.Book;
import com.by5388.demo.xml.BookStore;
import com.by5388.demo.xml.XmlHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Administrator  on 2021/4/15.
 */
public class DomHelper implements XmlHelper {

    @WorkerThread
    @Override
    public void queryXml(Context context, String fileName, ResultCallback callback) {
        try {
            BookStore mBookStore = new BookStore();
            //静态方法获得实例
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //获得解析器
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final InputStream inputStream = getInputStream(context, fileName);
            //把要解析的xml文件读入Dom解析器
            final Document document = documentBuilder.parse(inputStream);
            //获取book的节点列表
            final NodeList nodeList = document.getElementsByTagName("book");
            final int length = nodeList.getLength();
            for (int i = 0; i < length; i++) {
                // TODO: 2021/4/15 类型装换
                final Element element = (Element) nodeList.item(i);
                final Book book = new Book();
                book.mCategory = element.getAttribute("category");
                final NodeList childNodes = element.getChildNodes();
                final int childNodesLength = childNodes.getLength();
                for (int j = 0; j < childNodesLength; j++) {
                    final Node childNode = childNodes.item(j);
                    // TODO: 2021/4/15 判断的原因
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        final Element childElement = (Element) childNode;
                        final String nodeName = childElement.getNodeName();
                        final String nodeValue = childElement.getFirstChild().getNodeValue();
                        switch (nodeName) {
                            case "title":
                                book.mTitle = nodeValue;
                                break;
                            case "price":
                                book.mPrice = nodeValue;
                                break;
                            case "year":
                                book.mYear = nodeValue;
                                break;
                            case "author":
                                // TODO: 2021/4/15 这个不知道是否正确
                                book.mAuthorList.add(nodeValue);
                                break;
                        }
                    }
                }
                mBookStore.addBook(book);
            }

            callback.onSuccess(mBookStore);
            inputStream.close();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            callback.onError(e.getLocalizedMessage());
        }
    }

    private InputStream getInputStream(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }
}
