package com.lxt.been;

import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/25 11:05
 * @description :
 */
public class ConmmentBeen extends BaseBeen{
    /**
     * Remark : rrreee
     * CreateTime : 2017-05-22 18:05:25
     * Sum : 5
     * student_name : NickName
     * Gender : 2
     * StudentImage : http://weiyuyan2.oss-cn-beijing.aliyuncs.com/student/20170522160437_.jpg
     */

    private String Remark;
    private String CreateTime;
    private int Sum;
    private String student_name;
    private int Gender;
    private String StudentImage;

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getSum() {
        return Sum;
    }

    public void setSum(int Sum) {
        this.Sum = Sum;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public String getStudentImage() {
        return StudentImage;
    }

    public void setStudentImage(String StudentImage) {
        this.StudentImage = StudentImage;
    }
}
