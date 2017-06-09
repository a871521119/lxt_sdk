package com.alibaba.sdk.android.mns.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/1/25 13:44
 * @description :
 */
public class PagingListResult<T> implements Serializable {
    private String marker;
    private List<T> result = new ArrayList<T>();

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

}
