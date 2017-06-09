package com.alibaba.sdk.android.mns.model.serialize;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class BaseXMLSerializer<T> {

    protected static DocumentBuilderFactory factory = DocumentBuilderFactory
            .newInstance();

    private static ThreadLocal<DocumentBuilder> sps = new ThreadLocal<DocumentBuilder>();

    protected DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilder db = sps.get();
        if (db == null) {
            db = factory.newDocumentBuilder();
            sps.set(db);
        }
        return db;
    }
}
