package com.lxt.mobile.videoclass;


import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baijiahulian.livecore.context.LPError;
import com.baijiahulian.livecore.context.LiveRoom;
import com.baijiahulian.livecore.models.imodels.ILoginConflictModel;
import com.baijiahulian.livecore.models.imodels.IMessageModel;
import com.baijiahulian.livecore.models.imodels.IUserInModel;
import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.lxt.R;
import com.lxt.baijiayun.LiveHelper;
import com.lxt.baijiayun.LiveView;
import com.lxt.base.BaseBeen;
import com.lxt.been.EnterClass;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.Listener.NetworkStatusCallback;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.been.VideoClassMessageBeen;
import com.lxt.mobile.utils.SharedUtils;
import com.lxt.mobile.widget.AppraiseWindow;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.util.KeyBordUtil;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/5 10:38
 * @description :
 */
public class VideoClassActivity extends MBaseActivity implements LiveView, NetworkStatusCallback, AppraiseWindow.EvaluateListener {
    LiveHelper mLiveHelper;
    String teatureUserId;

    /**
     * 老师的默认头像
     */
    public ImageView teatureDefaultHead;
    boolean isTeatureCamaraEnable = false;
    private FrameLayout recorderLayout, playerLayout;
    VideoClassSendMessageLayout mVideoClassSendMessageLayout;
    private boolean layoutState = true;
    View hideView;

    /**
     * 教师评价的popwindow
     */
    AppraiseWindow appraiseWindow;

    @Override
    public void setContentLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 不锁屏
        setContentView(R.layout.videoclassactivity_layout);
    }

    @Override
    public void initView() {
        mLiveHelper = new LiveHelper(this, this, getWindow().getDecorView());
        teatureDefaultHead = (ImageView) findViewById(R.id.teacher_detail_image);
        recorderLayout = (FrameLayout) findViewById(R.id.picture_in_picture_a_small_window_user);
        playerLayout = (FrameLayout) findViewById(R.id.picture_in_picture_a_small_window_teacher);
        mVideoClassSendMessageLayout = (VideoClassSendMessageLayout) findViewById(R.id.text_chat_base_layout);
        hideView = findViewById(R.id.intercept_whiteboard_layout);
        mVideoClassSendMessageLayout.setLiveView(mLiveHelper);
        mVideoClassSendMessageLayout.setData();
        VideoClassProgressBarDialog videoClassProgressBarDialog = new VideoClassProgressBarDialog(this, R.style.dialog, this);
        videoClassProgressBarDialog.show();
        getRoomInfo();
    }

    private void getRoomInfo() {
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getClassConfig(SharedPreference.getData(LxtParameters.Key.GUID),
                getIntent().getStringExtra("lessonId"),
                SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                "LP_DEPLOY_TEST",
                SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(data.action, LxtParameters.Action.CLASSROOM)) {
            EnterClass room = (EnterClass) data.result;
            ImageLoaderUtil.getInstence().loadImage(this, room.user_avatar, teatureDefaultHead);
            mLiveHelper.enterRoom(room.room_id, room.user_number, room.user_name, room.user_avatar, room.sign);
        } else {
            showToast(getString(R.string.appraise_succeed));
            finish();
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        findViewById(R.id.intercept_whiteboard_layout).setOnClickListener(this);
        findViewById(R.id.title_left_layout).setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);

        switch (v.getId()) {
            case R.id.intercept_whiteboard_layout:
                //头，脚布局 隐藏显示
                if (layoutState) {
                    setAnimation((RelativeLayout) findViewById(R.id.title), 0, 0, 0, -0.5f, 1, 0.3f, 700);
                    setAnimation(mVideoClassSendMessageLayout, 0, 0, 0, 0.5f, 1, 0.3f, 700);
                    KeyBordUtil.hideSoftKeyboard(hideView);
                    layoutState = false;
                } else {
                    setAnimation((RelativeLayout) findViewById(R.id.title), 0, 0, -0.5f, 0, 0.3f, 1, 700);
                    setAnimation(mVideoClassSendMessageLayout, 0, 0, 0.5f, 0, 0.3f, 1, 700);
                    layoutState = true;
                }
                break;
            case R.id.title_left_layout:
                finish();
                break;
            default:

                break;
        }

    }

    /**
     * 隐藏头布局与文字交互布局
     */
    private void setAnimation(RelativeLayout RelativeLayout,
                              float T_fromXValue, float T_toXValue, float T_fromYValue,
                              float T_toYValue, float A_fromAlpha, float A_toAlpha, long time) {

        AnimationSet mAnimationSet = new AnimationSet(false);
        AlphaAnimation mAlphaAnimation = new AlphaAnimation(A_fromAlpha, A_toAlpha);
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, T_fromXValue,
                Animation.RELATIVE_TO_PARENT, T_toXValue,
                Animation.RELATIVE_TO_PARENT, T_fromYValue,
                Animation.RELATIVE_TO_PARENT, T_toYValue);
        mTranslateAnimation.setDuration(time);
        mAlphaAnimation.setDuration(time);
        mAnimationSet.addAnimation(mAlphaAnimation);
        mAnimationSet.addAnimation(mTranslateAnimation);
        mAnimationSet.setFillAfter(true);
        RelativeLayout.startAnimation(mAnimationSet);
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
        teatureDefaultHead.setVisibility(View.VISIBLE);
        isTeatureCamaraEnable = false;
        ImageLoaderUtil.getInstence().loadImage(this, "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=1671399052,4137662695&fm=80&w=179&h=119&img.JPEG", teatureDefaultHead);
        if (isWarnUser) {
            showToast("教师关闭了摄像头");
        }
    }

    @Override
    public void teatureOpenCamara(boolean isWarnUser) {
        teatureDefaultHead.postDelayed(new Runnable() {
            @Override
            public void run() {
                isTeatureCamaraEnable = true;
                teatureDefaultHead.setVisibility(View.GONE);
            }
        }, 3000);
        if (isWarnUser) {
            showToast("教师打开了摄像头");
        }
        playerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTeatureCamaraEnable) {
                    playerLayout.setVisibility(View.GONE);
                    recorderLayout.setVisibility(View.VISIBLE);
                    mLiveHelper.openLocalCamram();
                }
            }
        });
        recorderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTeatureCamaraEnable) {
                    recorderLayout.setVisibility(View.GONE);
                    playerLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onLiveRoomErro(LPError lpError) {

    }

    @Override
    public void onClassBegin() {
        //showToast("---->开始上课了");
    }

    @Override
    public void onClassOver() {
        appraiseWindow = new AppraiseWindow(this, this);
        appraiseWindow.showAtLocation(findViewById(R.id.parent));
    }

    @Override
    public void userLists(List<IUserModel> iUserModel) {

    }

    @Override
    public void logout(ILoginConflictModel iLoginConflictModel) {
        SharedUtils.logoutHandle(this);
    }

    @Override
    public void onNoVoice(Boolean aBoolean) {

    }

    @Override
    public void onUserOut(String userId) {

    }

    @Override
    public void onUserin(IUserInModel iUserInModel) {
        teatureUserId = iUserInModel.getUser().getUserId();
    }

    @Override
    public void onUserNumberChange(Integer integer) {

    }

    @Override
    public void onReceiveMessage(IMessageModel iMessageModel) {
        String msgText = iMessageModel.getContent();
        if (!TextUtils.isEmpty(msgText)) {
            VideoClassMessageBeen baseBeen = new VideoClassMessageBeen();
            baseBeen.setCreatTime(TimeUtil.getCurrentTime(TimeUtil.dateFormatHM));
            baseBeen.setContent(msgText);
            baseBeen.setHeadImage(iMessageModel.getFrom().getAvatar());
            baseBeen.setUserId(iMessageModel.getFrom().getUserId());
            if (TextUtils.isEmpty(teatureUserId)) {
                baseBeen.setIsStudent(0);
            } else {
                if (!teatureUserId.equals(iMessageModel.getFrom().getUserId())) {
                    baseBeen.setIsStudent(0);
                } else {//教师发言
                    baseBeen.setIsStudent(1);
                }
            }
            mVideoClassSendMessageLayout.addOneMessage(baseBeen);
        }
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


    @Override
    public void NetworkStatusCallback(boolean state, Object data) {

    }

    @Override
    public void onEvaluate() {
        if (appraiseWindow != null) {
            if (TextUtils.isEmpty(appraiseWindow.getEvaluateContent())) {
                showToast("请输入你想说的");
                return;
            } else {
                LxtHttp.getInstance().lxt_teacherComment(SharedPreference.getData(LxtParameters.Key.GUID),
                        SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                        getIntent().getStringExtra("lessonId"),
                        getIntent().getStringExtra("teacher_guid"),
                        appraiseWindow.getEvaluateStarLevel(),
                        appraiseWindow.getEvaluateContent(),
                        appraiseWindow.getLabelTagArray(),
                        SharedPreference.getData(LxtParameters.Key.TOKEN));
            }
        }
    }
}
