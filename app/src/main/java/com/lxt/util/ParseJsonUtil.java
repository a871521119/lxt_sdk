package com.lxt.util;

import android.text.TextUtils;

import com.lxt.base.BaseBeen;
import com.lxt.been.BookTime;
import com.lxt.been.BookingClassBeen;
import com.lxt.been.ClassRecordBeen;
import com.lxt.been.EnterClass;
import com.lxt.been.MyClassBeen;
import com.lxt.been.OrderClassDetailBeen;
import com.lxt.been.PersonalBeen;
import com.lxt.been.ProverbBeen;
import com.lxt.been.TeacherBean;
import com.lxt.been.TeatureAppraiseBeen;
import com.lxt.been.UpdataInfo;
import com.lxt.been.UserBeen;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.sdk.util.LogUtil;


/**
 * Created by LiWenJiang on 2017/5/9.
 */

public class ParseJsonUtil {


    /**
     * 解析JSON
     * @param json
     * @return
     */
    public static BaseBeen parse(String action,String json){

        BaseBeen baseBeen = (BaseBeen) JsonUtils.fromJson(json,BaseBeen.class);
        baseBeen.action = action;
        String data = JsonUtils.getValue(json,"ResultData");
        LogUtil.e("ResultData:"+data);
        if (data.indexOf("{") == 0){
            baseBeen.result = JsonUtils.fromJson(data,getBeen(action));
        }else if(data.indexOf("[") == 0){
            baseBeen.result = JsonUtils.fromJsonArray(data,getBeen(action));
        }else{
            baseBeen.result = data;
        }

        return baseBeen;
    }

    private static Class<?> getBeen(String action){
        if (TextUtils.equals(action, LxtParameters.Action.LOGIN)){
            return UserBeen.class;//用户
        }else if(TextUtils.equals(action, LxtParameters.Action.MYLESSONLIST)){
            return BookingClassBeen.class;//约课
        }else if(TextUtils.equals(action,LxtParameters.Action.GET_MYLESSIONLIST)
                || TextUtils.equals(action,LxtParameters.Action.CANCELRESERVATION)){
            return MyClassBeen.class;//我的课程
        }else if(TextUtils.equals(action,LxtParameters.Action.LESSONRECORD)){
            return ClassRecordBeen.class;//课程记录
        }else if(TextUtils.equals(action, LxtParameters.Action.MYMESSAGE)){//个人中心
            return PersonalBeen.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.TEACHERLIST)){//教师列表
            return TeacherBean.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.LESSONTIME)){
            return BookTime.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.UPDATEMESSAGE)){
            return UpdataInfo.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.CLASSROOM)){
            return EnterClass.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.GETTEACHERCOMMENT)){
            return TeatureAppraiseBeen.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.PROVERB)){
            return ProverbBeen.class;
        }else if(TextUtils.equals(action,LxtParameters.Action.PACKAGESETBACKS)){
            return OrderClassDetailBeen.class;
        }
        return BaseBeen.class;
    }

}
