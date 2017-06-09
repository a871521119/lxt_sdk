package com.alibaba.sdk.android.mns.model.deserialize;

/**
 *   @copyright : 北京乐学通教育科技有限公司 2017
 *   @creator : bryan
 *   @create-time : 11/24/15 13:34
 *   @description :
 */
import com.alibaba.sdk.android.mns.model.serialize.BaseXMLSerializer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLDeserializer<T> extends BaseXMLSerializer<T> implements Deserializer<T> {

    public String safeGetElementContent(Element root, String tagName,
                                        String defualValue) {
        NodeList nodes = root.getElementsByTagName(tagName);
        if (nodes != null) {
            Node node = nodes.item(0);
            if (node == null) {
                return defualValue;
            } else {
                return node.getTextContent();
            }
        }
        return defualValue;
    }

}
