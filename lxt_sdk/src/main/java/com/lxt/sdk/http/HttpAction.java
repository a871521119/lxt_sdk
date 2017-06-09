package com.lxt.sdk.http;
import java.util.HashMap;
import java.util.Map;

/**
 * 乐学通网络接口
 * Created by LiWenJiang on 2017/5/5.
 */

final class HttpAction extends BaseAction{

    private HttpPost post;

    public HttpAction(HttpPost post){
        this.post = post;
    }

    /**
     * 请求每日言谚语
     * @param version 版本号
     * @param group_guid 集团ID
     */
    public void getProverb(String version,String group_guid){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.VERSION, version);
        map.put(LxtParameters.Key.GROUP_GUID, group_guid);
        post.requestPost(LxtParameters.Action.PROVERB,map, getCallBackListener());
    }

    /**
     * 获取集团下学校ID
     * @param username 账号
     * @param password 密码
     * @param groupGuid 集团ID
     */
    public void lxt_getSchoolGuid(String username,String password,String groupGuid){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.PHONE, username);
        map.put(LxtParameters.Key.PASSWORD, password);
        map.put(LxtParameters.Key.GROUP_GUID, groupGuid);
        post.requestPost(LxtParameters.Action.GET_SCHOOL_GUID,map, getCallBackListener());
    }

    /**
     * 登录
     * @param username 账号
     * @param password 密码
     * @param school_guid 学校ID
     */
    public void lxt_login(String username,String password,String school_guid){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.PHONE, username);
        map.put(LxtParameters.Key.PASSWORD, password);
        map.put(LxtParameters.Key.REGISTRATION_ID, "2");
        map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
        post.requestPost(LxtParameters.Action.LOGIN,map, getCallBackListener());
    }

    /**
     * 登出
     * @param guid 学生ID
     * @param token 令牌
     */
    public void lxt_loginOut(String guid,String token){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TOKEN,token);
        post.requestPost(LxtParameters.Action.LOGINOUT,map,getCallBackListener(),true);

    }
    /**
     * 获取学生个人信息
     * @param guid 学生ID
     * @param token 令牌
     */
    public void lxt_getStudentInfo(String guid,String token){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TOKEN,token);
        post.requestPost(LxtParameters.Action.MYMESSAGE,map,getCallBackListener(),true);
    }

    /**
     * 修改学生个人信息
     * @param guid 学生ID
     * @param school_guid 学校ID
     * @param tel  电话号码
     * @param likename 昵称
     * @param sex      性别  1 男 2 女
     * @param birthtime 生日
     * @param email     邮箱
     * @param token     令牌
     */
    public void lxt_updateStudentInfo(String guid,String school_guid,String tel,String likename,String sex,
                                      String birthtime,String email,String token){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TOKEN,token);
        map.put(LxtParameters.Key.NAME, likename);
        map.put(LxtParameters.Key.SEX, sex);
        map.put(LxtParameters.Key.EMAIL, email);
        map.put(LxtParameters.Key.BIRTHTIME, birthtime);
        map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
        map.put(LxtParameters.Key.TEL, tel);
        post.requestPost(LxtParameters.Action.UPDATEMESSAGE,map,getCallBackListener(),true);
    }

    /**
     * 课程列表
     * @param guid  学生ID
     * @param school_guid 学校ID
     * @param token 令牌
     */
    public void lxt_getMyCourse(String guid, String school_guid,String token){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TOKEN,token);
        map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
        post.requestPost(LxtParameters.Action.MYLESSONLIST,map,getCallBackListener(),true);
    }

    /**
     * 课程进度
     * @param guid 学生ID
     * @param course_guid 商品ID
     * @param classType 课程类型
     * @param lesson_guid 套餐ID
     * @param token 令牌
     */
    public void lxt_getStudentCourseLessonInfo(String guid, String course_guid, String classType, String lesson_guid,String token){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TOKEN,token);
        map.put(LxtParameters.Key.COURSE_GUID, course_guid);
        map.put(LxtParameters.Key.TYPE, classType);
        map.put(LxtParameters.Key.LESSON_GUID, lesson_guid);
        post.requestPost(LxtParameters.Action.PACKAGESETBACKS,map,getCallBackListener(),true);
    }

    /**
     * 课程记录
     * @param guid 学生ID
     * @param token 令牌
     * @param page   当前页数
     * @param pageSize 每页数量
     */
    public void lxt_getCourseRecord(String guid,String page,String pageSize,String token){
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TOKEN,token);
        map.put(LxtParameters.Key.NOWPAGE,page);
        map.put(LxtParameters.Key.PAGESIZE,pageSize);
        post.requestPost(LxtParameters.Action.LESSONRECORD,map,getCallBackListener(),true);
    }


    /**
     * 关注教师
     * @param guid 学生ID
     * @param school_guid 学校ID
     * @param teacher_guid 教师ID
     * @param token  令牌
     */
  public void lxt_teacherCollection(String guid, String school_guid,String  teacher_guid,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.TEACHER_GUID, teacher_guid);
      post.requestPost(LxtParameters.Action.FOLLOW,map,getCallBackListener(),true);
  }

    /**
     *关注教师列表
     * @param guid 学生ID
     * @param school_guid 学校ID
     * @param lesson_guid 课程的id,如果lessonguid为空则显示全部关注的教师，否则是当前课程级别的老师
     * @param token 令牌
     */
  public void lxt_getTeacherCollectionList(String guid, String school_guid, String lesson_guid,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.LESSON_GUID, lesson_guid);
      post.requestPost(LxtParameters.Action.FOLLOWLIST,map,getCallBackListener(),true);
  }

    /**
     * 成长记录
     * @param guid 学生ID
     * @param school_guid 学校ID
     * @param page 当前页（每一个页码对应一个套餐课程）
     * @param token
     */
  public void lxt_getStudentCurve(String guid, String school_guid,String page,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.NOWPAGE,page);
      post.requestPost(LxtParameters.Action.GROWUPRECORD,map,getCallBackListener(),true);
  }

    /**
     * 教师列表
     * @param guid 学生ID
     * @param lesson_guid 课程ID
     * @param school_guid 学校ID
     * @param dateUnix  日期时间戳
     * @param shortTime 取值范围1-28不限则传空
     * @param sex       1男2女默认0
     * @param follow    1关注0不关注  默认0
     * @param page     页数
     * @param token    令牌
     */
  public void lxt_getTeacherList(String guid, String lesson_guid,  String school_guid, String dateUnix,
                                 String shortTime, String sex, String follow,String page,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.NOWPAGE,page);
      map.put(LxtParameters.Key.SEX,sex);
      map.put(LxtParameters.Key.LONGTIME, dateUnix);
      map.put(LxtParameters.Key.LESSON_GUID, lesson_guid);
      map.put(LxtParameters.Key.SHORTTIME, shortTime);
      map.put(LxtParameters.Key.FOLLOW, follow);
      post.requestPost(LxtParameters.Action.TEACHERLIST,map,getCallBackListener(),true);
  }

    /**
     * 进入教室
     * @param guid 学生ID
     * @param bespeak_guid 约课ID
     * @param school_guid  学校ID
     * @param token       令牌
     */
  public void lxt_getClassConfig(String guid, String bespeak_guid, String school_guid,String deployType,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.BESPEAK_GUID, bespeak_guid);
      map.put("context","android");
      map.put("deployType",deployType);
      post.requestPost(LxtParameters.Action.CLASSROOM,map,getCallBackListener(),true);
  }

    /**
     * 删除课程记录
     * @param guid 学生ID
     * @param bespeak_guid 约课ID
     * @param token   令牌
     */
  public void lxt_delLessonRecord(String guid, String bespeak_guid, String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.BESPEAK_GUID, bespeak_guid);
      post.requestPost(LxtParameters.Action.DELLESSONRECORD,map,getCallBackListener(),true);
  }

    /**
     * 修改密码
     * @param guid 学生ID
     * @param school_guid 学校ID
     * @param oldpwd 旧密码
     * @param newpwd 新密码
     * @param token 令牌
     */
  public void lxt_updatePassword(String guid, String school_guid,String oldpwd, String newpwd,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.STUDENT_GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.PASSWORD, oldpwd);
      map.put(LxtParameters.Key.NEWPASSWORD, newpwd);
      post.requestPost(LxtParameters.Action.UPDATEMEPASSWORD,map,getCallBackListener(),true);
  }

    /**
     * 教师评价
     * @param guid  学生ID
     * @param school_guid 学校ID
     * @param lesson_guid 课程ID
     * @param teacherId   教师ID
     * @param star        星级
     * @param conent      评价内容
     * @param tags        评价标签数组
     * @param token       令牌
     */
  public void lxt_teacherComment(String guid, String school_guid,String lesson_guid,
                                 String teacherId,String star,String conent,String[] tags,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.STUDENT_GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.LESSON_GUID, lesson_guid);
      map.put(LxtParameters.Key.TEACHER_GUID, teacherId);
      map.put(LxtParameters.Key.GRADE, star);
      map.put(LxtParameters.Key.CONTENT, conent);
      map.put(LxtParameters.Key.TAG, tags);
      post.requestPost(LxtParameters.Action.SETTEACHERCOMMENT,map,getCallBackListener(),true);
  }

    /**
     * 学生课程列表
     * @param guid 学校ID
     * @param school_guid 学校ID
     * @param page        当前页数
     * @param pageSize    每页数量
     * @param token       令牌
     */
  public void lxt_getStudentCourseList(String guid, String school_guid,
                                       String page,String pageSize,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.STUDENT_GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.NOWPAGE,page);
      map.put(LxtParameters.Key.PAGESIZE,pageSize);
      post.requestPost(LxtParameters.Action.GET_MYLESSIONLIST,map,getCallBackListener(),true);
  }

    /**
     * 获取教师评价列表
     * @param teacher_guid 教师ID
     * @param page          当前页数
     * @param pageSize      每页数量
     * @param token         令牌
     */
  public void lxt_getTeacherCommentList(String guid,String teacher_guid,String page, String pageSize,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.TEACHER_GUID,teacher_guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.GUID,guid);
      map.put(LxtParameters.Key.NOWPAGE,page);
      map.put(LxtParameters.Key.PAGESIZE,pageSize);
      post.requestPost(LxtParameters.Action.GETTEACHERCOMMENT,map,getCallBackListener(),true);
  }

    /**
     * 获取指定教师的排课详情
     * @param guid          学生ID
     * @param teacher_guid  教师ID
     * @param school_guid   学校ID
     * @param token         令牌
     */
  public void lxt_getTeacherCourse(String guid, String teacher_guid, String school_guid,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.TEACHER_GUID,teacher_guid);
      map.put(LxtParameters.Key.STUDENT_GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      post.requestPost(LxtParameters.Action.GETTEACHERCOURSE,map,getCallBackListener(),true);
  }

    /**
     * 获取教师排课时间
     * @param teacher_guid 教师ID
     * @param longTime     时间戳
     * @param token        令牌
     */
    public void lxt_getLessonTime(String guid,String teacher_guid,String longTime,String token) {
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.GUID,guid);
        map.put(LxtParameters.Key.TEACHER_GUID,teacher_guid);
        map.put(LxtParameters.Key.TOKEN,token);
        map.put(LxtParameters.Key.LONGTIME,longTime);
        post.requestPost(LxtParameters.Action.LESSONTIME,map,getCallBackListener(),true);
    }

    /**
     *预约课程
     * @param guid   学生ID
     * @param teacher_guid 教师ID
     * @param school_guid  学校ID
     * @param lesson_list_id  排课表id
     * @param lesson_guid     课程的guid
     * @param book_id
     * @param time          当天的日期0点的时间戳
     * @param time_pot      具体几点钟1: 09:00 􀒅2􀒓 09:30 􀒅3: 10:00􀒅 4􀒓 10:30􀒅 5􀒓 11:00 􀒅6􀒓 11:30􀒅 7􀒓 12:00􀒅 8: 12:30􀒅 9􀒓 13:00 􀒅 10: 13:30, 11:
     *                            14:00,' 12: 14:30, 13: 15:00, 14: 15:30, 15: 16:00, 16: 16:30, 17: 17:00, 18: 17:30, 19: 18:00, 20: 18:30, 21: 19:00, 22: 19:30, 23: 20:00, 24:
     *                            20:30, 25: 21:00 ,26: 21:30, 27: 22:00, 28: 22:30
     * @param type           是否推送 1推送 2不推送
     * @param status         状态1 正常 2取消 默认1
     * @param token          令牌
     */
  public void lxt_bookCourse(String guid, String teacher_guid, String school_guid,
                             String lesson_list_id, String lesson_guid, String book_id,
                             String time, String time_pot, String type, String status,String token){
      Map<String, Object> map = new HashMap<>();
      map.put(LxtParameters.Key.TEACHER_GUID,teacher_guid);
      map.put(LxtParameters.Key.STUDENT_GUID,guid);
      map.put(LxtParameters.Key.TOKEN,token);
      map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
      map.put(LxtParameters.Key.LESSON_LIST_ID, lesson_list_id);
      map.put(LxtParameters.Key.LESSON_GUID, lesson_guid);
      map.put(LxtParameters.Key.BOOK_ID, book_id);
      map.put(LxtParameters.Key.TIME, time);
      map.put(LxtParameters.Key.TIME_POT, time_pot);
      map.put(LxtParameters.Key.TYPE, type);
      map.put(LxtParameters.Key.STATUS, status);
      post.requestPost(LxtParameters.Action.BOOKINGCOURSE,map,getCallBackListener(),true);
  }

    /**
     * 取消预约
     *
     * @param guid
     * @param bespeak_guid
     * @param teacher_guid
     * @param school_guid
     * @param classTimeStamp      课程开始的时间
     * @param type                是否推送 1推送 2不推送
     */
    public  void cancelReservation(String guid, String bespeak_guid, String teacher_guid,
                                        String school_guid, String classTimeStamp, String type,String token) {
        Map<String, Object> map = new HashMap<>();
        map.put(LxtParameters.Key.TOKEN,token);
        map.put(LxtParameters.Key.STUDENT_GUID,guid);
        map.put(LxtParameters.Key.SCHOOL_GUID, school_guid);
        map.put(LxtParameters.Key.BESPEAK_GUID, bespeak_guid);
        map.put(LxtParameters.Key.TEACHER_GUID, teacher_guid);
        map.put(LxtParameters.Key.CLASSTIMESTAMP, classTimeStamp);
        map.put(LxtParameters.Key.TYPE, type);
        post.requestPost(LxtParameters.Action.CANCELRESERVATION,map,getCallBackListener(),true);
    }


}
