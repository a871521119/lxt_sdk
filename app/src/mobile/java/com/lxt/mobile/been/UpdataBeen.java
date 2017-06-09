package com.lxt.mobile.been;

import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/31 17:23
 * @description :
 */
public class UpdataBeen extends BaseBeen {
    String download_address;
    int update;

    public String getDownload_address() {
        return download_address;
    }

    public void setDownload_address(String download_address) {
        this.download_address = download_address;
    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }
}
