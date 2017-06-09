package com.lxt.mobile.been;


import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 14:14
 * @description :
 */
public class MyClassBeen extends BaseBeen {


    /**
     * LessonId : 2a3c3dcc144211e7a3e500163e106fb7
     * TeacherId : d0f30e6ca1d711e6bff900163e033e10
     * teacherName : gaoyujie
     * teacherImage : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/teacher/1478186027805591.jpg
     * studentName : test
     * StudentId : 460f235e135a11e79acb00163e106fb7
     * StartDate : 2017-03-29
     * StartTime : 2017-03-29 15:00:00
     * TimeRegion : 15:00
     * endTime : 2017-03-29 15:25:00
     * ServerTime : 2017-03-29 13:59:45
     * BookIngImag : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/teachingmaterial/148634929799150746.jpg
     * vertical_pic : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/teachingmaterial/148634929711667088.jpg
     * SpecialtyTitle : Happy Halloween
     * SpecialtyEnTitle : null
     * SumCount : 1
     * lesson_guid : 7aa75152141f11e781bf00163e106fb7
     * Count : 1
     * maxevole : 0
     * bookId : 184
     */
    private String LessonId;
    private String TeacherId;
    private String teacherName;
    private String teacherImage;
    private String studentName;
    private String StudentId;
    private String StartDate;
    private String StartTime;
    private String TimeRegion;
    private String endTime;
    private String ServerTime;
    private String BookIngImag;
    private String vertical_pic;
    private String SpecialtyTitle;
    private String SpecialtyEnTitle;
    private int SumCount;
    private String lesson_guid;
    private int Count;
    private int maxevole;
    private int bookId;
    //算出活动结束时间和当前系统时间的时间差，用ms为单位
    private long countTime;
    // 如"42天2时58分20秒"
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getCountTime() {
        return countTime;
    }

    public void setCountTime(long countTime) {
        this.countTime = countTime;
    }

    public String getLessonId() {
        return LessonId;
    }

    public void setLessonId(String LessonId) {
        this.LessonId = LessonId;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String TeacherId) {
        this.TeacherId = TeacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherImage() {
        return teacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String StudentId) {
        this.StudentId = StudentId;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String StartDate) {
        this.StartDate = StartDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getTimeRegion() {
        return TimeRegion;
    }

    public void setTimeRegion(String TimeRegion) {
        this.TimeRegion = TimeRegion;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getServerTime() {
        return ServerTime;
    }

    public void setServerTime(String ServerTime) {
        this.ServerTime = ServerTime;
    }

    public String getBookIngImag() {
        return BookIngImag;
    }

    public void setBookIngImag(String BookIngImag) {
        this.BookIngImag = BookIngImag;
    }

    public String getVertical_pic() {
        return vertical_pic;
    }

    public void setVertical_pic(String vertical_pic) {
        this.vertical_pic = vertical_pic;
    }

    public String getSpecialtyTitle() {
        return SpecialtyTitle;
    }

    public void setSpecialtyTitle(String SpecialtyTitle) {
        this.SpecialtyTitle = SpecialtyTitle;
    }

    public String getSpecialtyEnTitle() {
        return SpecialtyEnTitle;
    }

    public void setSpecialtyEnTitle(String SpecialtyEnTitle) {
        this.SpecialtyEnTitle = SpecialtyEnTitle;
    }

    public int getSumCount() {
        return SumCount;
    }

    public void setSumCount(int SumCount) {
        this.SumCount = SumCount;
    }

    public String getLesson_guid() {
        return lesson_guid;
    }

    public void setLesson_guid(String lesson_guid) {
        this.lesson_guid = lesson_guid;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public int getMaxevole() {
        return maxevole;
    }

    public void setMaxevole(int maxevole) {
        this.maxevole = maxevole;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
