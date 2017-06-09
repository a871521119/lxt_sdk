package com.lxt.been;

import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/19 11:37
 * @description :
 */
public class UpdataInfo extends BaseBeen{

    /**
     * guid : cce9123e3ada11e7a60200163e106fb7
     * sex : 1
     * email :
     * tel : 13333333333
     * name : ！！student
     * school_guid : 19c603b8352d11e7b3d500163e106fb7
     * birthtime :
     */

    private String guid;
    private String sex;
    private String email;
    private String tel;
    private String name;
    private String school_guid;
    private String birthtime;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool_guid() {
        return school_guid;
    }

    public void setSchool_guid(String school_guid) {
        this.school_guid = school_guid;
    }

    public String getBirthtime() {
        return birthtime;
    }

    public void setBirthtime(String birthtime) {
        this.birthtime = birthtime;
    }
}
