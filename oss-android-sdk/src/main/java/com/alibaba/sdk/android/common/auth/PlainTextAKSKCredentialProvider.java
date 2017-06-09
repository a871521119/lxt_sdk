package com.alibaba.sdk.android.common.auth;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/1/25 13:44
 * @description :
 */
public class PlainTextAKSKCredentialProvider extends CredentialProvider {
    private String accessKeyId;
    private String accessKeySecret;

    /**
     * 用阿里云提供的AccessKeyId， AccessKeySecret构造一个凭证提供器
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    public PlainTextAKSKCredentialProvider(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId.trim();
        this.accessKeySecret = accessKeySecret.trim();
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
