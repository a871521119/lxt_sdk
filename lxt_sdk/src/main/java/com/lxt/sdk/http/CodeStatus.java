package com.lxt.sdk.http;

/**
 * Created by LiWenJiang on 2017/5/24.
 * 请求网络返回的Code状态码
 */

public class CodeStatus {

    public static final String CODE_SN001 = "SN001";//内部错误

    public static final String CODE_SN002 = "SN002";//请求超时

    public static final String CODE_SN003 = "SN003";//版本错误

    public static final String CODE_SN004 = "SN004";//网络请求失败 建议重新登录！

    public static final String CODE_SN005 = "SN005";//签名错误或登录过期

    public static final String CODE_SN006 = "SN006";//没有权限(没有任何请求参数)

    public static final String CODE_SN007 = "SN007";//用户不存在 请检测

    public static final String CODE_SN008 = "SN008";//其他设备登录 请检测

    public static final String CODE_SN009 = "SN009";//登录过期 请重新登录


}
