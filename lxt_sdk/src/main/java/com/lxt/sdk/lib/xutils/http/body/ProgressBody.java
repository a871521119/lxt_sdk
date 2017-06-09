package com.lxt.sdk.lib.xutils.http.body;


import com.lxt.sdk.lib.xutils.http.ProgressHandler;

/**
 * Created by wyouflf on 15/8/13.
 */
public interface ProgressBody extends RequestBody {
    void setProgressHandler(ProgressHandler progressHandler);
}
