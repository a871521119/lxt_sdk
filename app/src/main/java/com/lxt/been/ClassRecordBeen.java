package com.lxt.been;


import com.lxt.base.BaseBeen;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 16:18
 * @description :
 */
public class ClassRecordBeen extends BaseBeen {


    private int count;
    private List<ClassRecordBeen> message;
    private int comment;
    private String bespeak_guid;
    private String lesson_guid;
    private String teacher_guid;
    private String teacherName;
    private String teacherPic;
    private int startTime;
    private String teachingMaterialPic;
    private String teachingMaterialVerticalPic;
    private String bookName;
    private String grade;

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getBespeak_guid() {
        return bespeak_guid;
    }

    public void setBespeak_guid(String bespeak_guid) {
        this.bespeak_guid = bespeak_guid;
    }

    public String getLesson_guid() {
        return lesson_guid;
    }

    public void setLesson_guid(String lesson_guid) {
        this.lesson_guid = lesson_guid;
    }

    public String getTeacher_guid() {
        return teacher_guid;
    }

    public void setTeacher_guid(String teacher_guid) {
        this.teacher_guid = teacher_guid;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPic() {
        return teacherPic;
    }

    public void setTeacherPic(String teacherPic) {
        this.teacherPic = teacherPic;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getTeachingMaterialPic() {
        return teachingMaterialPic;
    }

    public void setTeachingMaterialPic(String teachingMaterialPic) {
        this.teachingMaterialPic = teachingMaterialPic;
    }

    public String getTeachingMaterialVerticalPic() {
        return teachingMaterialVerticalPic;
    }

    public void setTeachingMaterialVerticalPic(String teachingMaterialVerticalPic) {
        this.teachingMaterialVerticalPic = teachingMaterialVerticalPic;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ClassRecordBeen> getMessage() {
        return message;
    }

    public void setMessage(List<ClassRecordBeen> message) {
        this.message = message;
    }
}
