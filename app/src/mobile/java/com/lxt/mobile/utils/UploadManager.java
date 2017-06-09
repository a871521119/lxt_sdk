package com.lxt.mobile.utils;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.HttpPost;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.sdk.util.JsonUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/19 14:16
 * @description :
 */
public class UploadManager {
    public static UploadManager mUploadManager = null;
    //阿里 oos
    private OSS oss;
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAIaaF5vZKwZpgn";
    private static final String accessKeySecret = "Wg2IJelbaAVNPaoAFtVmBRTga2QrDY";
    private static final String testBucket = "weiyuyan2";
    OnUploadFinishListener onUploadFinishListener;

    public static UploadManager getInstence() {
        synchronized (UploadManager.class) {
            if (mUploadManager == null) {
                mUploadManager = new UploadManager();
            }
        }
        return mUploadManager;
    }

    public UploadManager init(Context mContext) {
        //oos设置
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(mContext, endpoint, credentialProvider, conf);
        return mUploadManager;
    }

    public void upLoadImage(String uploadFilePath) {
        final String imgname = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE).format(new Date());
        String uploadObject = "student/" + imgname + "_.jpg";
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(testBucket, uploadObject, uploadFilePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //阿里云上传成功，准备开始上传本地服务器
                upLoadLoacal(imgname + "_.jpg");
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                //阿里云上传失败
            }
        });
    }

    /**
     * 上传本地服务器
     */
    private void upLoadLoacal(String picUrl) {
        Map<String, Object> map = new HashMap<>();
        map.put("school_guid", SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID));
        map.put("student_guid", SharedPreference.getData(LxtParameters.Key.GUID));
        map.put("pic", picUrl);
        map.put("token", SharedPreference.getData(LxtParameters.Key.TOKEN));
        HttpPost httpPost = new HttpPost();
        httpPost.requestPost("updatePic", map, new CallBackListener() {
            @Override
            public void onSuccessed(String action, String result) {
                String data = JsonUtils.getValue(result, "ResultData");

                if (onUploadFinishListener != null)
                    onUploadFinishListener.onUpLoadSuc(data);
            }

            @Override
            public void onFailed(String action, String result) {
                if (onUploadFinishListener != null)
                    onUploadFinishListener.onUploadFaild();
            }

            @Override
            public void onErrored(String action, Map<String, Object> params, String errormsg) {
                if (onUploadFinishListener != null)
                    onUploadFinishListener.onUploadFaild();
            }
        }, true);
    }

    public void setOnUploadFinishListener(OnUploadFinishListener onUploadFinishListener) {
        this.onUploadFinishListener = onUploadFinishListener;
    }


    public interface OnUploadFinishListener {
        /**
         * 上传成功回调
         *
         * @param url
         */
        public void onUpLoadSuc(String url);

        /**
         * 上传失败回调
         */

        public void onUploadFaild();
    }


}
