package com.lxt.been;


import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 12:00
 * @description :
 */
public class BookingClassBeen extends BaseBeen {
    private int YKstatus;
    private String lesson_guid;
    private int bespeakCount;
    private int bespeakNo;
    private String evolve;
    private String type;
    private String startTime;
    private String endTime;
    private String book_id;
    private String course_guid;
    private int section;
    private String name;
    private int score;
    private int nolesson;
    private String verticalPic;
    private String transversePic;
    private int ServerTime;
    private String package_name;//课程进度横图
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public int getYKstatus() {
        return YKstatus;
    }

    public void setYKstatus(int YKstatus) {
        this.YKstatus = YKstatus;
    }

    public String getLesson_guid() {
        return lesson_guid;
    }

    public void setLesson_guid(String lesson_guid) {
        this.lesson_guid = lesson_guid;
    }

    public int getBespeakCount() {
        return bespeakCount;
    }

    public void setBespeakCount(int bespeakCount) {
        this.bespeakCount = bespeakCount;
    }

    public int getBespeakNo() {
        return bespeakNo;
    }

    public void setBespeakNo(int bespeakNo) {
        this.bespeakNo = bespeakNo;
    }

    public String getEvolve() {
        return evolve;
    }

    public void setEvolve(String evolve) {
        this.evolve = evolve;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getCourse_guid() {
        return course_guid;
    }

    public void setCourse_guid(String course_guid) {
        this.course_guid = course_guid;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNolesson() {
        return nolesson;
    }

    public void setNolesson(int nolesson) {
        this.nolesson = nolesson;
    }

    public String getVerticalPic() {
        return verticalPic;
    }

    public void setVerticalPic(String verticalPic) {
        this.verticalPic = verticalPic;
    }

    public String getTransversePic() {
        return transversePic;
    }

    public void setTransversePic(String transversePic) {
        this.transversePic = transversePic;
    }

    public int getServerTime() {
        return ServerTime;
    }

    public void setServerTime(int ServerTime) {
        this.ServerTime = ServerTime;
    }

}
