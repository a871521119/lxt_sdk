package com.lxt.tv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baijiahulian.livecore.context.LPError;
import com.baijiahulian.livecore.context.LiveRoom;
import com.baijiahulian.livecore.models.imodels.ILoginConflictModel;
import com.baijiahulian.livecore.models.imodels.IMessageModel;
import com.baijiahulian.livecore.models.imodels.IUserInModel;
import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.EnterClass;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.LogUtil;
import com.lxt.tv.base.MBaseActivity;
import com.lxt.baijiayun.LiveHelper;
import com.lxt.baijiayun.LiveView;

import java.util.List;

public class VideoClassActivity extends MBaseActivity implements LiveView {
    LiveHelper mLiveHelper;
    private String teatureUserId;
    /**
     * 教师状态提示文字
     */
    TextView teacher_video_toast_text;

    /**
     * 学生状态提示文字
     */
    TextView student_video_toast_text;

    /**
     * 课程的名字
     */
    TextView video_class_name_text;

    /**
     * 课程时间的倒计时
     */
     TextView video_class_countdown;
    /**
     * 教师是否就为
     */
    boolean isTeatureCamaraEnable = false;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.video_class_activity);
    }

    @Override
    public void initView() {
        teacher_video_toast_text= (TextView) findViewById(R.id.teacher_video_toast_text);
        student_video_toast_text= (TextView) findViewById(R.id.student_video_toast_text);
        video_class_name_text= (TextView) findViewById(R.id.video_class_name_text);
        video_class_countdown= (TextView) findViewById(R.id.video_class_countdown);
        mLiveHelper = new LiveHelper(this, this, getWindow().getDecorView());
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getClassConfig(SharedPreference.getData(LxtParameters.Key.GUID),
                bundle.getString("LessonId"),
                SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                "LP_DEPLOY_TEST",
                SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        EnterClass room = (EnterClass) data.result;
        mLiveHelper.enterRoom(room.room_id,room.user_number,room.user_name,room.user_avatar,room.sign);
    }

    @Override
    public void onEnterRoomSuc(LiveRoom liveRoom) {

    }

    @Override
    public void onEnterRoomFaild(LPError error) {

    }

    @Override
    public void onTeatureConnect(String teatuerUserId) {
        mLiveHelper.showVideo(teatuerUserId);
        teatureUserId = teatuerUserId;
    }

    @Override
    public void teatureCloseCamara(boolean isWarnUser) {
        teacher_video_toast_text.setVisibility(View.VISIBLE);
        isTeatureCamaraEnable = false;
        if (isWarnUser) {
            teacher_video_toast_text.setText(getString(R.string.video_teatureCloseCamara));
        }
    }

    @Override
    public void teatureOpenCamara(boolean isWarnUser) {
        teacher_video_toast_text.postDelayed(new Runnable() {
            @Override
            public void run() {
                isTeatureCamaraEnable = true;
                teacher_video_toast_text.setVisibility(View.GONE);
                if (isTeatureCamaraEnable) {
                    mLiveHelper.openLocalCamram();
                }
            }
        }, 3000);
        teacher_video_toast_text.setVisibility(View.GONE);
        if (isWarnUser) {
            showToast("教师打开了摄像头");
        }
    }

    @Override
    public void onLiveRoomErro(LPError lpError) {

    }

    @Override
    public void onClassBegin() {

    }

    @Override
    public void onClassOver() {

    }

    @Override
    public void userLists(List<IUserModel> iUserModel) {

    }

    @Override
    public void logout(ILoginConflictModel iLoginConflictModel) {

    }

    @Override
    public void onNoVoice(Boolean aBoolean) {

    }

    @Override
    public void onUserOut(String userId) {

    }

    @Override
    public void onUserin(IUserInModel iUserInModel) {

    }

    @Override
    public void onUserNumberChange(Integer integer) {

    }

    @Override
    public void onReceiveMessage(IMessageModel iMessageModel) {
        LogUtil.e( iMessageModel.getFrom().getName());
        LogUtil.e( iMessageModel.getFrom().getAvatar());
        LogUtil.e(iMessageModel.getFrom().getUserId());
        LogUtil.e(iMessageModel.getContent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLiveHelper.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLiveHelper.onDestory();
    }
}
