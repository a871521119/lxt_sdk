package com.lxt.sdk.http;

import static com.lxt.sdk.LXTSDK.app;

/**
 * 参数
 */
public class LxtParameters {
    public static int TESTMOD;//0 开发模式（dev） 1测试模式 2正式模式 4特殊添加视频的测试模式
    public static String HTTP;
    //  public static String URL = "https://app.test.4000669696.com/";
    public static String GROUP_GUID = "";//集团id


    public static String getUrl() {
        return url;
    }

    private static String url = "https://app.test.4000669696.com/";
    // private static String url = "http://app.s1.test.4000669696.com/";//SDK接口

    // private static String url = "http://wyytv.test.4000669696.com/";
    public static class Key {
        public static String PHONE = "phone";//电话号码
        public static String PASSWORD = "password";//密码
        public static String SCHOOL_GUID = "school_guid";//学校guid
        public static String GROUP_GUID = "group_guid";//集团ID
        public static String REGISTRATION_ID = "registration_id";//极光推送标识
        public static String VERSION = "version";
        public static String NAME = "name";
        public static String SEX = "sex";
        public static String EMAIL = "email";
        public static String BIRTHTIME = "birthtime";
        public static String TEL = "tel";
        public static String COURSE_GUID = "course_guid";//课程ID
        public static String SHARED_USER_INFO = "user";//用户信息
        public static String TOKEN = "token";
        public static String GUID = "guid";//学生guid
        public static String STUDENT_GUID = "student_guid";//
        public static String TEACHER_GUID = "teacher_guid";//教师ID
        public static String TYPE = "type";//课程类型
        public static String LESSON_GUID = "lesson_guid";//套餐ID
        public static String NOWPAGE = "nowPage";//当前页数
        public static String PAGESIZE = "pageSize";//每页的列表数量
        public static String LONGTIME = "longTime";//日期时间戳
        public static String SHORTTIME = "shortTime";//时间段
        public static String FOLLOW = "follow";//关注
        public static String BESPEAK_GUID = "bespeak_guid";
        public static String NEWPASSWORD = "newpassword";//新密码
        public static String GRADE = "grade";//
        public static String TAG = "tag";
        public static String CONTENT = "content";
        public static String LESSON_LIST_ID = "lesson_list_id";//排课表ID
        public static String BOOK_ID = "book_id";//课本ID
        public static String TIME = "time";
        public static String TIME_POT = "time_pot";
        public static String STATUS = "status";
        public static String CLASSTIMESTAMP = "classTimeStamp";
        public static String LOGINSTYLE = "loginStyle";//是否登录 1表示已登录0表示未登陆
        public static String PIC = "pic";//
        public static String CLASSCOUNT = "classcount";//
        public static String SCHOOLDATALIST = "schoolDataList";//校区列表信息
        public static String CLASSNAME = "className";//课程的名称
        public static String CLASSTYPE = "classType";//课程的类别
    }

    public static class Action {
        public static String PROVERB = "proverb";//每日谚语
        public static String CHECKVERSION = "checkVersion";//检测版本更新
        public static String LOGIN = "login";//登录
        public static String LOGINOUT = "loginOut";//登出
        public static String GET_SCHOOL_GUID = "getSchoolGuid";//获取学校Guid
        public static String MYLESSONLIST = "myLessonList";//预约上课
        public static String GET_MYLESSIONLIST = "getMyLessionList";//我的课程
        public static String CANCELRESERVATION = "cancelReservation";//取消预约
        public static String LESSONRECORD = "lessonRecord";//课程记录
        public static String MYMESSAGE = "myMessage";//个人信息
        public static String UPDATEMESSAGE = "updateMessage";//修改个人信息
        public static String SETTEACHERCOMMENT = "setTeacherComment";//教学质量评价
        public static String PACKAGESETBACKS = "packageSetbacks";//课程进度
        public static String FOLLOW = "follow";//关注教师
        public static String FOLLOWLIST = "followList";//关注列表
        public static String GROWUPRECORD = "growUpRecord";//成长记录
        public static String TEACHERLIST = "teacherList";//教师列表
        public static String CLASSROOM = "phoneBaiJiaYun";//进入教室
        public static String DELLESSONRECORD = "delLessonRecord";//删除约课记录
        public static String UPDATEMEPASSWORD = "updateMePassword";//修改密码
        public static String GETTEACHERCOMMENT = "getTeacherComment";//获取教师评价列表
        public static String GETTEACHERCOURSE = "getTeacherCourse";//获取指定教师排课列表
        public static String BOOKINGCOURSE = "bookingCourse";//预约课程
        public static String LESSONTIME = "lessonTime";//教师排课时间
    }

}
