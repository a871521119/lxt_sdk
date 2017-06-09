package com.lxt.sdk.http;

import android.text.TextUtils;

import com.lxt.sdk.LXTSDK;
import com.lxt.sdk.listener.CallBackListener;
import com.lxt.sdk.listener.LxtCallBack;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.sdk.util.LogUtil;
import com.lxt.sdk.util.LxtConfig;
import com.lxt.sdk.util.PhoneInfo;
import com.lxt.sdk.util.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Post请求
 */
public class HttpPost extends LxtHttpManger {

    /**
     * POST请求
     * @param action              请求的动作
     * @param param               参数
     * @param netCallBack         回调
     * @param otherParam          其他参数
     */
    public void requestPost(final String action, Map<String, Object> param, final CallBackListener netCallBack, Object... otherParam) {

        final Map<String, Object> params = getParams(action,param,otherParam);
       // LogUtil.i(action + "参数->" + params);
       // LogUtil.i("参数->" + params);

        post(LxtParameters.getUrl()+ action, params, new LxtCallBack() {
            @Override
            public void onResponse(String result) {
            LogUtil.i(ActionReplace.getAction(action) + " onResponse  result:" + result);
                try {
                    if(!TextUtils.isEmpty(result)){
                        if(netCallBack != null){
                            JSONObject json = new JSONObject(result);
                            String code = json.optString("ServerNo");
                            if (TextUtils.equals("SN200",code)){
                                netCallBack.onSuccessed(ActionReplace.getAction(action),result);
                            }else {
                                netCallBack.onFailed(ActionReplace.getAction(action),result);
                            }
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
                LogUtil.e(ActionReplace.getAction(action) + " onFail  result:" + result);
                if (netCallBack != null){
                    netCallBack.onErrored(ActionReplace.getAction(action),params,getFailResult(result));
                }
            }
        });
    }

    private String getFailResult(String result){
        if(result != null ){
            if(LXTSDK.isRepalceAction()){
                if(result.contains("No route to host")){
                    return "No route to host";
                }
            }
            return result;

        }
        return "未知错误";
    }


    /**
     * 获取公共参数
     * @param action 要访问的接口
     * @param param  当前接口参数
     * @param otherParam 用于标识是否需要验证Token和Guid
     * @return
     */
    private Map<String,Object> getParams(String action,Map<String, Object> param,Object... otherParam){
        boolean whetherSignature = false;//是否验证
        boolean ident = true;//标识是否添加公共参数
        if (otherParam != null && otherParam.length != 0) {
            whetherSignature = (boolean) otherParam[0];
            if (otherParam.length>1){
                ident = (boolean) otherParam[1];
            }
        }
        return getRequestParams(param, action,whetherSignature,ident);
    }

    /**
     * 公共请求参数
     * @param param  当前接口的参数
     * @param action 要访问的接口
     * @param identifier 是否添加公共参数
     * @param whetherSignature 是否验证token和guid
     * @return
     */
    private  Map<String, Object> getRequestParams(Map<String, Object> param,String action,  boolean whetherSignature,boolean identifier) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (identifier) {
                String _token = "";
                String _guid = "1";
                if (whetherSignature) {
                    if(param.containsKey(LxtParameters.Key.TOKEN)){
                        _token = (String) param.get(LxtParameters.Key.TOKEN);
                        param.remove(LxtParameters.Key.TOKEN);//token不作为参数请求数据
                    }
                    if(param.containsKey(LxtParameters.Key.GUID)){
                        _guid = (String) param.get(LxtParameters.Key.GUID);
                    }
                    if (param.containsKey(LxtParameters.Key.STUDENT_GUID)){
                        _guid = (String) param.get(LxtParameters.Key.STUDENT_GUID);
                    }

                }
                String time = "";
                if (LxtConfig.SERVERTIME == 0L) {
                    time = TimeUtil.getTime();
                } else {
                    time = TimeUtil.getTime();
                    // time = ((Constants_SDK.SERVERTIME + SystemClock.elapsedRealtime() - Constants_SDK.KAIJITIME) / 1000) + "";
                }
                map.put("param", JsonUtils.toJson(param));
                map.put("signatures", MD5.MD5(action + time + _guid + map.get("param") + NewToken.getToken(_token, whetherSignature)));
                map.put("time", time);
                map.put("guid", _guid);
                map.put("version", "" + PhoneInfo.getVersion(LXTSDK.getAppContext()));
                map.put("deviceid", PhoneInfo.getUniqueID(LXTSDK.getAppContext()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }



}
