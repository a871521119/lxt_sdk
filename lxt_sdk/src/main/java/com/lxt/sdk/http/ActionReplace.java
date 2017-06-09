package com.lxt.sdk.http;

import android.text.TextUtils;

import com.lxt.sdk.LXTSDK;

/**
 * Created by LiWenJiang on 2017/5/26.
 */

class ActionReplace {




    /**
     * Action替换
     * @param action
     * @return
     */
    public static String getAction(String action){
        if (LXTSDK.isRepalceAction()){
            if (TextUtils.equals(action,LxtParameters.Action.LOGIN)){
                return Action.LOGIN;
            }else if(TextUtils.equals(action,LxtParameters.Action.LOGINOUT)){
                return Action.LOGINOUT;
            }else if(TextUtils.equals(action,LxtParameters.Action.GET_SCHOOL_GUID)){
                return Action.SCHOOL;
            }else if(TextUtils.equals(action,LxtParameters.Action.MYLESSONLIST)){
                return Action.LESSONLIST;
            }else if(TextUtils.equals(action,LxtParameters.Action.GET_MYLESSIONLIST)){
                return Action.MYLESSIONLIST;
            }else if(TextUtils.equals(action,LxtParameters.Action.CANCELRESERVATION)){
                return Action.CANCELBOOK;
            }else if(TextUtils.equals(action,LxtParameters.Action.LESSONRECORD)){
                return Action.LESSONRECORD;
            }else if(TextUtils.equals(action,LxtParameters.Action.MYMESSAGE)){
                return Action.PERSONINFO;
            }else if(TextUtils.equals(action,LxtParameters.Action.UPDATEMESSAGE)){
                return Action.UPDATEMESSAGE;
            }else if(TextUtils.equals(action,LxtParameters.Action.SETTEACHERCOMMENT)){
                return Action.TEACHERCOMMENT;
            }else if(TextUtils.equals(action,LxtParameters.Action.PACKAGESETBACKS)){
                return Action.PROGRESS;
            }else if(TextUtils.equals(action,LxtParameters.Action.FOLLOW)){
                return Action.FOLLOWTEACHER;
            }else if(TextUtils.equals(action,LxtParameters.Action.FOLLOWLIST)){
                return Action.FOLLOWTEACHERLIST;
            }else if(TextUtils.equals(action,LxtParameters.Action.GROWUPRECORD)){
                return Action.GROWUPRECORD;
            }else if(TextUtils.equals(action,LxtParameters.Action.TEACHERLIST)){
                return Action.TEACHERLIST;
            }else if(TextUtils.equals(action,LxtParameters.Action.CLASSROOM)){
                return Action.ENTERCLASSROOM;
            }else if(TextUtils.equals(action,LxtParameters.Action.UPDATEMEPASSWORD)){
                return Action.UPDATEPASSWORD;
            }else if(TextUtils.equals(action,LxtParameters.Action.GETTEACHERCOMMENT)){
                return Action.TEACHERCOMMENTLIST;
            }else if(TextUtils.equals(action,LxtParameters.Action.GETTEACHERCOURSE)){
                return Action.TEACHERCOURSE;
            }else if(TextUtils.equals(action,LxtParameters.Action.BOOKINGCOURSE)){
                return Action.BOOKCOURSE;
            }
        }
        return action;
    }
}
