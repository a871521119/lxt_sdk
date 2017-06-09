package com.lxt.baijiayun;

import com.baijiahulian.livecore.context.LPError;
import com.baijiahulian.livecore.context.LiveRoom;
import com.baijiahulian.livecore.models.imodels.ILoginConflictModel;
import com.baijiahulian.livecore.models.imodels.IMessageModel;
import com.baijiahulian.livecore.models.imodels.IUserInModel;
import com.baijiahulian.livecore.models.imodels.IUserModel;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/5 10:59
 * @description :
 */
public interface LiveView {
    /**
     * 进入房间成功回调
     */
    void onEnterRoomSuc(LiveRoom liveRoom);
    /**
     * 进入房间失败回调
     */
    void onEnterRoomFaild(LPError error);
    /**
     * 教师连接成功
     */
    void onTeatureConnect(String teatuerUserId);
    /**
     * 教师关闭了摄像头
     */
    void teatureCloseCamara(boolean isWarnUser);
    /**
     * 教师打开了摄像头
     */
    void teatureOpenCamara(boolean isWarnUser);


    /**
     * 教师连接错误回掉
     */
    void onLiveRoomErro(LPError lpError);
    /**
     * 进入教室上课回调
     */
    void onClassBegin();
    /**
     * 下课回调
     */
    void onClassOver();
    /**
     * 用户列表回调
     */
    void userLists(List<IUserModel> iUserModel);
    /**
     * 被踢下线
     */
    void logout(ILoginConflictModel iLoginConflictModel);
    /**
     * 被禁言是回调
     */
    void onNoVoice(Boolean aBoolean);
    /**
     * 用户退出（主要是老师的推出）
     */
    void onUserOut(String userId);
    /**
     * 用户进入（主要是老师的进入）
     */
    void onUserin(IUserInModel iUserInModel);
    /**
     * 人数变化时回调
     */
    void onUserNumberChange(Integer integer);
    /**
     * 收到聊天信息时回调
     */
    void onReceiveMessage(IMessageModel iMessageModel);


}
