package com.lxt.sdk.http;

import com.lxt.sdk.listener.CallBackListener;

/**
 * 乐学通接口类
 * Created by LiWenJiang on 2017/5/5.
 */

public class LxtHttp {

    private static HttpAction http;

    private static LxtHttp instance;

    private LxtHttp() {
    }

    /**
     * 单例构造方法
     */
    public static LxtHttp getInstance() {
        if (instance == null) {
            synchronized (LxtHttp.class) {
                if (instance == null) {
                    instance = new LxtHttp();
                    http = new HttpAction(new HttpPost());
                }
            }
        }
        return instance;
    }

    /**
     * 每日谚语
     * @param version
     * @param groupGuid
     */
    public void getProverb(String version, String groupGuid) {
        http.getProverb(version, groupGuid);
    }
    /**
     * 获取集团下学校ID
     * @param username  账号
     * @param password  密码
     * @param groupGuid 集团ID
     */
    public void lxt_getSchoolGuid(String username, String password, String groupGuid) {
        http.lxt_getSchoolGuid(username, password, groupGuid);
    }

    /**
     * 登录
     * @param username   账号
     * @param password   密码
     * @param schoolGuid 学校ID
     */
    public void lxt_login(String username, String password, String schoolGuid) {
        http.lxt_login(username, password, schoolGuid);
    }

    /**
     * 登出
     * @param guid  学生ID
     * @param token 令牌
     */
    public void lxt_loginOut(String guid, String token) {
        http.lxt_loginOut(guid, token);
    }

    /**
     * 获取学生个人信息
     * @param guid  学生ID
     * @param token 令牌
     */
    public void lxt_getStudentInfo(String guid, String token) {
        http.lxt_getStudentInfo(guid, token);
    }

    /**
     * 修改学生个人信息
     *
     * @param guid        学生ID
     * @param school_guid 学校ID
     * @param tel         电话号码
     * @param likename    昵称
     * @param sex         性别  1-男  2-女
     * @param birthtime   生日
     * @param email       邮箱
     * @param token       令牌
     */
    public void lxt_updateStudentInfo(String guid, String school_guid, String tel, String likename, String sex,
                                      String birthtime, String email, String token) {
        http.lxt_updateStudentInfo(guid, school_guid, tel, likename, sex, birthtime, email, token);
    }

    /**
     * 课程列表
     *
     * @param guid        学生ID
     * @param school_guid 学校ID
     * @param token       令牌
     */
    public void lxt_getMyCourse(String guid, String school_guid, String token) {
        http.lxt_getMyCourse(guid, school_guid, token);
    }

    /**
     * 课程进度
     *
     * @param guid        学生ID
     * @param course_guid 商品ID
     * @param classType   课程类型
     * @param lesson_guid 套餐ID
     * @param token       令牌
     */
    public void lxt_getStudentCourseLessonInfo(String guid, String course_guid, String classType, String lesson_guid, String token) {
        http.lxt_getStudentCourseLessonInfo(guid, course_guid, classType, lesson_guid, token);
    }

    /**
     * 课程记录
     *
     * @param guid     学生ID
     * @param token    令牌
     * @param page     当前页数
     * @param pageSize 每页数量
     */
    public void lxt_getCourseRecord(String guid, String page, String pageSize, String token) {
        http.lxt_getCourseRecord(guid, page, pageSize, token);
    }

    /**
     * 关注教师
     *
     * @param guid         学生ID
     * @param school_guid  学校ID
     * @param teacher_guid 教师ID
     * @param token        令牌
     */
    public void lxt_teacherCollection(String guid, String school_guid, String teacher_guid, String token) {
        http.lxt_teacherCollection(guid, school_guid, teacher_guid, token);
    }

    /**
     * 关注教师列表
     *
     * @param guid        学生ID
     * @param school_guid 学校ID
     * @param lesson_guid 课程Id，如果lessonguid为空则显示全部关注的教师，否则显示当前课程级别的教师
     * @param token       令牌
     */
    public void lxt_getTeacherCollectionList(String guid, String school_guid, String lesson_guid, String token) {
        http.lxt_getTeacherCollectionList(guid, school_guid, lesson_guid, token);
    }

    /**
     * 成长记录
     *
     * @param guid        学生ID
     * @param school_guid 学校ID
     * @param page        当前页（每一个页码对应一个套餐课程）
     * @param token
     */
    public void lxt_getStudentCurve(String guid, String school_guid, String page, String token) {
        http.lxt_getStudentCurve(guid, school_guid, page, token);
    }

    /**
     * 教师列表
     * @param guid        学生ID
     * @param lesson_guid 课程ID
     * @param school_guid 学校ID
     * @param dateUnix    日期0点时间戳
     * @param shortTime   取值范围1-28，不限则传空
     * @param sex         不限-0  男-1 女-2
     * @param follow      默认-0  关注-1 不关注-0
     * @param page        页数
     * @param token       令牌
     */
    public void lxt_getTeacherList(String guid, String lesson_guid, String school_guid, String dateUnix,
                                   String shortTime, String sex, String follow, String page, String token) {
        http.lxt_getTeacherList(guid, lesson_guid, school_guid, dateUnix, shortTime, sex, follow, page, token);
    }

    /**
     * 进入教室
     *
     * @param guid         学生ID
     * @param bespeak_guid 约课ID
     * @param school_guid  学校ID
     * @param type  LP_DEPLOY_TEST 测试
     * @param token        令牌
     */
    public void lxt_getClassConfig(String guid, String bespeak_guid, String school_guid,String type, String token) {
        http.lxt_getClassConfig(guid, bespeak_guid, school_guid,type, token);
    }

//    /**
//     * 删除课程记录
//     *
//     * @param guid         学生ID
//     * @param bespeak_guid 约课ID
//     * @param token        令牌
//     */
//    public void lxt_delLessonRecord(String guid, String bespeak_guid, String token) {
//        http.lxt_delLessonRecord(guid, bespeak_guid, token);
//    }

    /**
     * 修改密码
     *
     * @param guid        学生ID
     * @param school_guid 学校ID
     * @param oldpwd      旧密码
     * @param newpwd      新密码
     * @param token       令牌
     */
    public void lxt_updatePassword(String guid, String school_guid, String oldpwd, String newpwd, String token) {
        http.lxt_updatePassword(guid, school_guid, oldpwd, newpwd, token);
    }

    /**
     * 教师评价
     *
     * @param guid        学生ID
     * @param school_guid 学校ID
     * @param lesson_guid 课程ID
     * @param teacherId   教师ID
     * @param star        星级
     * @param conent      评价内容
     * @param tags        评价标签数组
     * @param token       令牌
     */
    public void lxt_teacherComment(String guid, String school_guid, String lesson_guid,
                                   String teacherId, String star, String conent, String[] tags, String token) {
        http.lxt_teacherComment(guid, school_guid, lesson_guid, teacherId, star, conent, tags, token);
    }

    /**
     * 学生课程列表
     * @param guid        学校ID
     * @param school_guid 学校ID
     * @param page        当前页数
     * @param pageSize    每页数量
     * @param token       令牌
     */
    public void lxt_getStudentCourseList(String guid, String school_guid,
                                         String page, String pageSize, String token) {
        http.lxt_getStudentCourseList(guid, school_guid, page, pageSize, token);
    }

    /**
     * 获取教师评价列表
     *
     * @param guid         学生ID
     * @param teacher_guid 教师ID
     * @param page         当前页数
     * @param pageSize     每页数量
     * @param token        令牌
     */
    public void lxt_getTeacherCommentList(String guid, String teacher_guid, String page, String pageSize, String token) {
        http.lxt_getTeacherCommentList(guid, teacher_guid, page, pageSize, token);
    }

    /**
     * 获取指定教师的排课详情
     * @param guid         学生ID
     * @param teacher_guid 教师ID
     * @param school_guid  学校ID
     * @param token        令牌
     */
    public void lxt_getTeacherCourse(String guid, String teacher_guid, String school_guid, String token) {
        http.lxt_getTeacherCourse(guid, teacher_guid, school_guid, token);
    }

    /**
     * 获取排课时间（TV接口）
     * @param guid
     * @param teacher_guid
     * @param longTime
     * @param token
     */
    public void lxt_getLessonTime(String guid,String teacher_guid,String longTime,String token){
        http.lxt_getLessonTime(guid,teacher_guid,longTime,token);

    }

    /**
     * 预约课程
     *
     * @param guid           学生ID
     * @param teacher_guid   教师ID
     * @param school_guid    学校ID
     * @param lesson_list_id 排课表id
     * @param lesson_guid    课程的guid
     * @param book_id
     * @param time           当天的日期0点的时间戳
     * @param time_pot       具体几点钟1: 09:00 􀒅2􀒓 09:30 􀒅3: 10:00􀒅 4􀒓 10:30􀒅 5􀒓 11:00 􀒅6􀒓 11:30􀒅 7􀒓 12:00􀒅 8: 12:30􀒅 9􀒓 13:00 􀒅 10: 13:30, 11:
     *                       14:00,' 12: 14:30, 13: 15:00, 14: 15:30, 15: 16:00, 16: 16:30, 17: 17:00, 18: 17:30, 19: 18:00, 20: 18:30, 21: 19:00, 22: 19:30, 23: 20:00, 24:
     *                       20:30, 25: 21:00 ,26: 21:30, 27: 22:00, 28: 22:30
     * @param type           是否推送  推送-1  不推送-0
     * @param status         状态  1-正常 2-取消
     * @param token          令牌
     */
    public void lxt_bookCourse(String guid, String teacher_guid, String school_guid,
                               String lesson_list_id, String lesson_guid, String book_id,
                               String time, String time_pot, String type, String status, String token) {
        http.lxt_bookCourse(guid, teacher_guid, school_guid, lesson_list_id, lesson_guid, book_id, time, time_pot, type, status, token);
    }

    /**
     * 取消预约
     * @param guid
     * @param bespeak_guid
     * @param teacher_guid
     * @param school_guid
     * @param classTimeStamp 课程开始的时间
     * @param type           是否推送 1推送 2不推送
     */
    public void lxt_cancelReservation(String guid, String bespeak_guid, String teacher_guid,
                                      String school_guid, String classTimeStamp, String type, String token) {
        http.cancelReservation(guid, bespeak_guid, teacher_guid, school_guid, classTimeStamp, type, token);
    }

    /**
     * 设置网络请求回调
     * @param callBackListener
     */
    public void setCallBackListener(CallBackListener callBackListener) {
        http.setCallBackListener(callBackListener);
    }


}
