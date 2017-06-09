package com.lxt.sdk.lib.xutils.http.body;

import java.io.IOException;
import java.io.OutputStream;


public interface RequestBody {

    long getContentLength();

    void setContentType(String contentType);

    String getContentType();

    void writeTo(OutputStream out) throws IOException;
}
