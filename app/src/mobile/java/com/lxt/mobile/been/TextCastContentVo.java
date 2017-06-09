package com.lxt.mobile.been;

import java.io.Serializable;

/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 高明宇
 * @create-time : 2016/11/3 18:21
 * @description : 文字聊天内容实体类
 */
public class TextCastContentVo implements Serializable {
    private String teacherTime;
    private String studentTime;
    private String teacherContent;
    private String studnetContent;
    private String teacherImage;
    //谁发布的文字内容 true 教师，false 学生。
    private boolean contentState;

    public TextCastContentVo() {
    }

    public TextCastContentVo(String teacherTime, String studentTime, String teacherImage, String teacherContent, String studnetContent, boolean contentState) {
        this.teacherTime = teacherTime;
        this.studentTime = studentTime;
        this.teacherContent = teacherContent;
        this.studnetContent = studnetContent;
        this.contentState = contentState;
        this.teacherImage = teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getTeacherImage() {

        return teacherImage;
    }

    public String getTeacherTime() {
        return teacherTime;
    }

    public String getStudentTime() {
        return studentTime;
    }

    public String getTeacherContent() {
        return teacherContent;
    }

    public String getStudnetContent() {
        return studnetContent;
    }

    public boolean isContentState() {
        return contentState;
    }

    public void setTeacherTime(String teacherTime) {
        this.teacherTime = teacherTime;
    }

    public void setStudentTime(String studentTime) {
        this.studentTime = studentTime;
    }

    public void setTeacherContent(String teacherContent) {
        this.teacherContent = teacherContent;
    }

    public void setStudnetContent(String studnetContent) {
        this.studnetContent = studnetContent;
    }

    @Override
    public String toString() {
        return "TextCastContentVo{" +
                "teacherTime='" + teacherTime + '\'' +
                ", studentTime='" + studentTime + '\'' +
                ", teacherContent='" + teacherContent + '\'' +
                ", studnetContent='" + studnetContent + '\'' +
                ", contentState=" + contentState +
                '}';
    }
}
