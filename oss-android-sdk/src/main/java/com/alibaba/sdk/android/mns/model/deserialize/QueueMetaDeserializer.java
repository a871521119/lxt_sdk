package com.alibaba.sdk.android.mns.model.deserialize;

import com.alibaba.sdk.android.mns.model.QueueMeta;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;

import okhttp3.Response;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/1/25 13:44
 * @description :
 */
public class QueueMetaDeserializer extends AbstractQueueMetaDeserializer<QueueMeta> {

    @Override
    public QueueMeta deserialize(Response response) throws Exception {
        try {
            String responseBody = response.body().string();
            DocumentBuilder builder = getDocumentBuilder();
            InputSource is = new InputSource(new StringReader(responseBody));
            Document doc = builder.parse(is);

            Element root = doc.getDocumentElement();

            return parseQueueMeta(root);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
