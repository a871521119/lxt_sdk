package com.alibaba.sdk.android.mns.model.serialize;

import com.alibaba.sdk.android.mns.common.MNSConstants;
import com.alibaba.sdk.android.mns.model.QueueMeta;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/1/25 13:44
 * @description :
 */
public class QueueMetaSerializer extends XMLSerializer<QueueMeta> {

    @Override
    public String serialize(QueueMeta obj, String encoding)
            throws Exception {
        Document doc = getDocumentBuilder().newDocument();

        Element root = doc.createElementNS(MNSConstants.DEFAULT_XML_NAMESPACE, MNSConstants.QUEUE_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, MNSConstants.DELAY_SECONDS_TAG,
                obj.getDelaySeconds(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, MNSConstants.VISIBILITY_TIMEOUT,
                obj.getVisibilityTimeout(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, MNSConstants.MAX_MESSAGE_SIZE_TAG,
                obj.getMaxMessageSize(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, MNSConstants.MESSAGE_RETENTION_PERIOD_TAG,
                obj.getMessageRetentionPeriod(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, MNSConstants.POLLING_WAITSECONDS_TAG,
                obj.getPollingWaitSeconds(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateBooleanContentElement(doc, MNSConstants.LOGGING_ENABLED_TAG,
                obj.getLoggingEnabled(), null);
        if (node != null) {
            root.appendChild(node);
        }

        return XmlUtil.xmlNodeToString(doc, encoding);
    }

}
