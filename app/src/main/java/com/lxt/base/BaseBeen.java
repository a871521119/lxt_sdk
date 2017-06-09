package com.lxt.base;

import java.io.Serializable;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/28 14:52
 * @description : 实体基类：实现序列化
 */
public class BaseBeen implements Serializable {

    public String serverTime;//服务器时间

    public String ServerNo;//数据请求编号

    public String action;//请求接口

    public Object result;//实体数据

}

