package com.lxt.sdk.lib.xutils.http.body;

import android.net.Uri;
import android.text.TextUtils;


import com.lxt.sdk.lib.xutils.common.util.KeyValue;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class UrlEncodedParamsBody implements RequestBody {

    private byte[] content;
    private String charset = "UTF-8";

    public UrlEncodedParamsBody(List<KeyValue> params, String charset) throws IOException {
        if (!TextUtils.isEmpty(charset)) {
            this.charset = charset;
        }
        StringBuilder contentSb = new StringBuilder();
        if (params != null) {
            for (KeyValue kv : params) {
                String name = kv.key;
                String value = kv.getValueStr();
                if (!TextUtils.isEmpty(name) && value != null) {
                    if (contentSb.length() > 0) {
                        contentSb.append("&");
                    }
                    contentSb.append(Uri.encode(name, this.charset))
                            .append("=")
                            .append(Uri.encode(value, this.charset));
                }
            }
        }

        this.content = contentSb.toString().getBytes(this.charset);
    }

    @Override
    public long getContentLength() {
        return content.length;
    }

    @Override
    public void setContentType(String contentType) {
    }

    @Override
    public String getContentType() {
        return "application/lxt-www-form-urlencoded;charset=" + charset;
    }

    @Override
    public void writeTo(OutputStream sink) throws IOException {
        sink.write(this.content);
        sink.flush();
    }
}
