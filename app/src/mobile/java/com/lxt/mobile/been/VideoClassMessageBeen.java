package com.lxt.mobile.been;

import com.lxt.base.BaseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 高明宇
 * @create-time : 2016/11/3 18:21
 * @description : 文字聊天内容实体类
 */
public class VideoClassMessageBeen extends BaseBeen {
    private String creatTime;//创建时间
    private String content;//创建内容
    private String headImage;//头像
    String userId;//发言者的userId
    int isStudent = 0;//是否为学生0学生1老师

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public int getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(int isStudent) {
        this.isStudent = isStudent;
    }
}
