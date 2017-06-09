package com.alibaba.sdk.android.mns.model.serialize;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
import org.w3c.dom.Node;

import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlUtil {
    private static TransformerFactory transFactory = TransformerFactory.newInstance();


    public static void output(Node node, String encoding,
                              OutputStream outputStream) throws TransformerException {
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty("encoding", encoding);

        DOMSource source = new DOMSource();
        source.setNode(node);

        StreamResult result = new StreamResult();
        result.setOutputStream(outputStream);

        transformer.transform(source, result);
    }

    public static String xmlNodeToString(Node node, String encoding)
            throws TransformerException {
        Transformer transformer = transFactory.newTransformer();
        transformer.setOutputProperty("encoding", encoding);
        StringWriter strWtr = new StringWriter();

        DOMSource source = new DOMSource();
        source.setNode(node);
        StreamResult result = new StreamResult(strWtr);
        transformer.transform(source, result);
        return strWtr.toString();

    }
}
