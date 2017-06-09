package com.lxt.been;


import com.lxt.base.BaseBeen;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/30 10:09
 * @description :
 */
public class TeacherBean extends BaseBeen {


    /**
     * teacher_guid : 9a1fa9d8e13111e6881500163e2eb842
     * gender : 1
     * birthtime : 702057600
     * nationality : United Kingdom
     * firstname : Allan
     * pic : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/teacher/1487226292370084.png
     * follow : 0
     * introduce : It's not worth doing something unless someone, somewhere, would much rather you weren't. So I became a teacher. Words are fun. There is an almost endless supply of them, they are cheap and you can throw them at people from a safe distance. Why did I become a teacher? It lets me travel the world and along the way I can share with students the way to see language, as a tool that can take you many places and frequently get you in a lot of trouble as well as subsequently helping you get out of it.
     * exp : 2
     * count : 3
     * type : English
     * startTime : 1490869800
     * lesson_list_id : 37676
     * book_id : 85
     * score : 5
     */

    private String teacher_guid;
    private int gender;
    private int birthtime;
    private String nationality;
    private String firstname;
    private String pic;
    private int follow;
    private String introduce;
    private int exp;
    private int count;
    private String type;
    private int startTime;
    private int lesson_list_id;
    private int book_id;
    private float score;
    private List<TeacherBean> teacher;

    public String getTeacher_guid() {
        return teacher_guid;
    }

    public void setTeacher_guid(String teacher_guid) {
        this.teacher_guid = teacher_guid;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(int birthtime) {
        this.birthtime = birthtime;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getLesson_list_id() {
        return lesson_list_id;
    }

    public void setLesson_list_id(int lesson_list_id) {
        this.lesson_list_id = lesson_list_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<TeacherBean> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<TeacherBean> teacher) {
        this.teacher = teacher;
    }
}
