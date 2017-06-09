package com.lxt.baijiayun;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import com.baijiahulian.avsdk.liveplayer.CameraGLTextureView;
import com.baijiahulian.avsdk.liveplayer.ViETextureViewRenderer;
import com.baijiahulian.livecore.LiveSDK;
import com.baijiahulian.livecore.context.LPConstants;
import com.baijiahulian.livecore.context.LPError;
import com.baijiahulian.livecore.context.LiveRoom;
import com.baijiahulian.livecore.context.OnLiveRoomListener;
import com.baijiahulian.livecore.launch.LPLaunchListener;
import com.baijiahulian.livecore.listener.OnRollCallListener;
import com.baijiahulian.livecore.models.imodels.ILoginConflictModel;
import com.baijiahulian.livecore.models.imodels.IMediaControlModel;
import com.baijiahulian.livecore.models.imodels.IMediaModel;
import com.baijiahulian.livecore.models.imodels.IMessageModel;
import com.baijiahulian.livecore.models.imodels.IUserInModel;
import com.baijiahulian.livecore.models.imodels.IUserModel;
import com.baijiahulian.livecore.ppt.LPPPTFragment;
import com.baijiahulian.livecore.utils.LPBackPressureBufferedSubscriber;
import com.baijiahulian.livecore.utils.LPErrorPrintSubscriber;
import com.baijiahulian.livecore.wrapper.LPPlayer;
import com.baijiahulian.livecore.wrapper.LPRecorder;
import com.lxt.R;
import com.lxt.base.BaseActivity;
import com.lxt.sdk.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/5 10:58
 * @description :
 */
public class LiveHelper extends Presenter {
    LiveView mLiveView;
    Context mContext;
    View mRootView;
    private LiveRoom liveRoom;

    private LPPPTFragment lppptFragment;
    private FrameLayout recorderLayout, playerLayout;
    private List<IMediaModel> playerVideoModel;
    private String currentPlayingVideoUserId;
    private LPRecorder recorder; // recorder用于发布本地音视频
    private LPPlayer player; // player用于播放远程音视频流
    private boolean menuItemState = false;
    private boolean videoItemState = false;
    private boolean audioItemState = false;
    private boolean beautyFilterState = false;
    private boolean captureVideoDefinition = false;
    private boolean isSpeakingAllowed = false;

    private TextureView textureView;

    public LiveHelper(LiveView liveView, Context context, View rootView) {
        this.mLiveView = liveView;
        this.mContext = context;
        this.mRootView = rootView;
        playerVideoModel = new ArrayList<>();
    }

    private void enter(String code, String name) {
        enterRoom(code, name);
    }

    private IMediaModel getVideoMediaById(String userId) {
        for (IMediaModel model : playerVideoModel) {
            if (model.getUser().getUserId().equals(userId))
                return model;
        }
        return null;
    }

    public void onInitSuccess(LiveRoom mLiveRoom) {
        this.liveRoom = mLiveRoom;
        //用于显示上行视频的surfaceview
        recorderLayout = (FrameLayout) mRootView.findViewById(R.id.picture_in_picture_a_small_window_user);
        CameraGLTextureView view = new CameraGLTextureView(mContext);
        recorderLayout.addView(view);
        recorder = liveRoom.getRecorder();
        recorder.setPreview(view);
        recorder.setCaptureVideoDefinition(LPConstants.LPResolutionType.LOW);
        //recorder.setLinkType(LPConstants.LPLinkType.TCP);
        playerLayout = (FrameLayout) mRootView.findViewById(R.id.picture_in_picture_a_small_window_teacher);
        player = liveRoom.getPlayer();


        //初始化ppt模块
        lppptFragment = new LPPPTFragment();
        lppptFragment.setLiveRoom(liveRoom);
        final FragmentTransaction transaction = ((BaseActivity) mContext).getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_join_code_ppt, lppptFragment);
        transaction.commitAllowingStateLoss();
        // 收到聊天消息
        liveRoom.getChatVM().getObservableOfReceiveMessage().subscribe(new Action1<IMessageModel>() {
            @Override
            public void call(IMessageModel iMessageModel) {
                //refreshLogView(iMessageModel.getFrom().getName() + ":" + iMessageModel.getContent() + "\n");
                mLiveView.onReceiveMessage(iMessageModel);
            }
        });
        // 房间人数改变
        liveRoom.getObservableOfUserNumberChange().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                //refreshLogView("房间人数:" + integer + "\n");
                mLiveView.onUserNumberChange(integer);
            }
        });
        // 用户进入
        liveRoom.getObservableOfUserIn().observeOn(AndroidSchedulers.mainThread()).subscribe(new LPBackPressureBufferedSubscriber<IUserInModel>() {
            @Override
            public void call(IUserInModel iUserInModel) {
                mLiveView.onUserin(iUserInModel);
                //tvMessages.append("用户进入:" + iUserInModel.getUser().getName() + "\n");
            }
        });
        // 用户退出
        liveRoom.getObservableOfUserOut().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String userId) {
                mLiveView.onUserOut(userId);
                //tvMessages.append("用户退出:" + userId + "\n");
            }
        });
        // 全体禁言
        liveRoom.getObservableOfForbidAllChatStatus().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        //btnSend.setEnabled(!aBoolean);
                        mLiveView.onNoVoice(aBoolean);
                    }
                });
        // 登录冲突
        liveRoom.getObservableOfLoginConflict().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ILoginConflictModel>() {
                    @Override
                    public void call(ILoginConflictModel iLoginConflictModel) {
//                        Toast.makeText(JoinCodeActivity.this, "您的账号在" + iLoginConflictModel.getConflictEndType().name() + "端登录",
//                                Toast.LENGTH_SHORT).show();
//                        finish();
                        mLiveView.logout(iLoginConflictModel);

                    }
                });
        Subscriber<List<IMediaModel>> subs = new LPErrorPrintSubscriber<List<IMediaModel>>() {
            @Override
            public void call(List<IMediaModel> iMediaModels) {
                playerVideoModel.clear();
                if (iMediaModels != null) {
                    for (IMediaModel model : iMediaModels) {
                        if (model.isVideoOn()) {
                            playerVideoModel.add(model);
                        }
                    }
                }
                if (playerVideoModel.size() == 0) {
                    mLiveView.teatureCloseCamara(false);
                } else {
                    LogUtil.e("------->mLiveView.onTeatureConnect1");
                    mLiveView.teatureOpenCamara(false);
                    currentPlayingVideoUserId = playerVideoModel.get(0).getUser().getUserId();
                    mLiveView.onTeatureConnect(currentPlayingVideoUserId);

                }

            }
        };
        // 进入房间首次获取发言队列
        ConnectableObservable<List<IMediaModel>> obs = liveRoom.getSpeakQueueVM().getObservableOfActiveUsers();
        obs.subscribe(subs);
        obs.connect();
        liveRoom.getSpeakQueueVM().requestActiveUsers();
        /*新的用户发言，所有用户都能收到*/
        liveRoom.getSpeakQueueVM().getObservableOfMediaNew().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<IMediaModel>() {
                    @Override
                    public void call(IMediaModel iMediaModel) {
                        String userId = iMediaModel.getUser().getUserId();
                        IMediaModel model = getVideoMediaById(userId);
                        //tvMessages.append("media change:" + iMediaModel.getUser().getName() + "\n");
                        if (iMediaModel.isVideoOn() && !playerVideoModel.contains(model)) {
                            playerVideoModel.add(iMediaModel);
                        } else if (!iMediaModel.isVideoOn() && playerVideoModel.contains(model)) {
                            playerVideoModel.remove(model);
                            if (userId.equals(currentPlayingVideoUserId)) {
                                currentPlayingVideoUserId = null;
                            }
                        }

                        if (playerVideoModel.size() == 0) {
                            mLiveView.teatureCloseCamara(true);
                        } else {
                            mLiveView.teatureOpenCamara(true);
                            currentPlayingVideoUserId = playerVideoModel.get(0).getUser().getUserId();
                            mLiveView.onTeatureConnect(currentPlayingVideoUserId);
                        }
                    }
                });
        // 发言人音视频状态改变
        liveRoom.getSpeakQueueVM().getObservableOfMediaChange().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<IMediaModel>() {
                    @Override
                    public void call(IMediaModel iMediaModel) {

                        String userId = iMediaModel.getUser().getUserId();
                        IMediaModel model = getVideoMediaById(userId);
                        //tvMessages.append("media change:" + iMediaModel.getUser().getName() + "\n");
                        if (iMediaModel.isVideoOn() && !playerVideoModel.contains(model)) {
                            playerVideoModel.add(iMediaModel);
                        } else if (!iMediaModel.isVideoOn() && playerVideoModel.contains(model)) {
                            playerVideoModel.remove(model);
                            if (userId.equals(currentPlayingVideoUserId)) {
                                currentPlayingVideoUserId = null;
                            }
                        }

                        if (playerVideoModel.size() == 0) {
                            mLiveView.teatureCloseCamara(true);
                        } else {
                            mLiveView.teatureOpenCamara(true);
                            currentPlayingVideoUserId = playerVideoModel.get(0).getUser().getUserId();
                            mLiveView.onTeatureConnect(currentPlayingVideoUserId);
                        }

                    }
                });
        // 新的发言
        liveRoom.getSpeakQueueVM().getObservableOfMediaNew().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<IMediaModel>() {
                    @Override
                    public void call(IMediaModel iMediaModel) {
                        //tvMessages.append("media new:" + iMediaModel.getUser().getName() + "\n");
                        if (iMediaModel.isVideoOn()) {
                            playerVideoModel.add(iMediaModel);
                        }
                    }
                });
        // 关闭发言
        liveRoom.getSpeakQueueVM().getObservableOfMediaClose().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<IMediaModel>() {
                    @Override
                    public void call(IMediaModel iMediaModel) {
                        //tvMessages.append("media close:" + iMediaModel.getUser().getName() + "\n");
                        String userId = iMediaModel.getUser().getUserId();
                        IMediaModel model = getVideoMediaById(userId);
                        if (playerVideoModel.contains(model)) {
                            playerVideoModel.remove(model);
                            if (userId.equals(currentPlayingVideoUserId)) {
                                currentPlayingVideoUserId = null;
                            }
                        }
                    }
                });
        // 用户列表回调
        liveRoom.getOnlineUserVM().getObservableOfOnlineUser().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LPErrorPrintSubscriber<List<IUserModel>>() {
                    @Override
                    public void call(List<IUserModel> iUserModels) {
                        mLiveView.userLists(iUserModels);


//                        tvMessages.append("users:");
//                        for (IUserModel model : iUserModels)
//                            tvMessages.append(model.getName() + " ");
//                        tvMessages.append("\n");
                    }
                });

        liveRoom.getObservableOfClassStart().subscribe(new LPErrorPrintSubscriber<Void>() {
            @Override
            public void call(Void aVoid) {
                mLiveView.onClassBegin();
                //tvMessages.append("上课了\n");
            }
        });
        liveRoom.getObservableOfClassEnd().subscribe(new LPErrorPrintSubscriber<Void>() {
            @Override
            public void call(Void aVoid) {
                mLiveView.onClassOver();
                //tvMessages.append("下课了\n");
            }
        });
        // 上行链路切换
        recorder.getObservableOfLinkType().subscribe(new LPErrorPrintSubscriber<LPConstants.LPLinkType>() {
            @Override
            public void call(LPConstants.LPLinkType lpLinkType) {
                //tvMessages.append("上行:" + lpLinkType.name() + "\n");
            }
        });
        // 下行链路切换
        player.getObservableOfLinkType().subscribe(new LPErrorPrintSubscriber<LPConstants.LPLinkType>() {
            @Override
            public void call(LPConstants.LPLinkType lpLinkType) {
                //tvMessages.append("下行:" + lpLinkType.name() + "\n");
            }
        });
        // error 回调
        liveRoom.setOnLiveRoomListener(new OnLiveRoomListener() {
            @Override
            public void onError(LPError lpError) {
                mLiveView.onLiveRoomErro(lpError);
//                Log.e("error", lpError.getMessage());
//                tvMessages.append("error code:" + lpError.getCode() + " error msg:" + lpError.getMessage() + "\n");
            }
        });
        // 老师远程控制
        liveRoom.getSpeakQueueVM().getObservableOfMediaControl().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LPErrorPrintSubscriber<IMediaControlModel>() {
                    @Override
                    public void call(IMediaControlModel iMediaControlModel) {
                        if (!iMediaControlModel.isApplyAgreed()) {
                            // 老师关闭发言
                            isSpeakingAllowed = false;
                            videoItemState = false;
                            audioItemState = false;
                            beautyFilterState = false;
                            captureVideoDefinition = false;
                        }
                    }
                });
        // 有学生申请发言 自动同意
        liveRoom.getSpeakQueueVM().getObservableOfSpeakApply().subscribe(new Action1<IMediaModel>() {
            @Override
            public void call(IMediaModel iMediaModel) {
                liveRoom.getSpeakQueueVM().agreeSpeakApply(iMediaModel.getUser().getUserId());
            }
        });
        // 老师处理发言结果
        liveRoom.getSpeakQueueVM().getObservableOfSpeakResponse()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new LPErrorPrintSubscriber<IMediaControlModel>() {
                    @Override
                    public void call(IMediaControlModel iMediaControlModel) {
                        if (!iMediaControlModel.getUser().getUserId().equals(liveRoom.getCurrentUser().getUserId()))
                            return;
                        if (iMediaControlModel.isApplyAgreed()) {
                            //tvMessages.append("老师同意了你的发言\n");
                            isSpeakingAllowed = true;
                        } else {
                            //tvMessages.append("老师拒绝了你的发言\n");
                            isSpeakingAllowed = false;
                        }
                    }
                });
        //上课
        if (liveRoom.getCurrentUser().getType() == LPConstants.LPUserType.Teacher)
            liveRoom.requestClassStart();

        // 点名
        liveRoom.setOnRollCallListener(new OnRollCallListener() {

            @Override
            public void onRollCall(int time, final RollCall rollCallListener) {
//                dialog = new AlertDialog.Builder(JoinCodeActivity.this).setTitle("点名了")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                rollCallListener.call();
//                            }
//                        }).create();
//                dialog.show();
            }

            @Override
            public void onRollCallTimeOut() {
//                if (dialog!=null && dialog.isShowing())
//                    dialog.dismiss();
            }
        });
        textureView = ViETextureViewRenderer.CreateRenderer(mContext, true);
        playerLayout.addView(textureView);
        player.setVideoView(textureView);
    }


    /**
     * @param roomId     房间号
     * @param userNumber 用户 ID
     * @param userName   用户名
     * @param userAvatar 用户头像
     * @param sign       请求接口参数签名, 签名由 (roomId, userNumber, userName, userType, userAvatar) 5 个参数生成
     */
    public void enterRoom(long roomId, String userNumber, String userName, String userAvatar, String sign) {
        LPConstants.LPUserType lpUserType = LPConstants.LPUserType.Student;
        if (lpUserType != null) {
            LiveSDK.enterRoom(mContext, roomId, userNumber, userName, lpUserType, userAvatar, sign, new LPLaunchListener() {
                @Override
                public void onLaunchSteps(int i, int i1) {
                    Log.i("init steps", "step:" + i + "/" + i1);
                }

                @Override
                public void onLaunchError(LPError lpError) {
                    Log.e("error", lpError.getCode() + " " + lpError.getMessage());
                    mLiveView.onEnterRoomFaild(lpError);
                }

                @Override
                public void onLaunchSuccess(LiveRoom liveRoom) {
                    Log.e("onLaunchSuccess", liveRoom.toString());
                    mLiveView.onEnterRoomSuc(liveRoom);
                    onInitSuccess(liveRoom);
                }
            });

        }


    }


    public void enterRoom(final String code, final String name) {
        LiveSDK.enterRoom(mContext, code, name, new LPLaunchListener() {
            @Override
            public void onLaunchSteps(int step, int totalStep) {
//                Log.i("init steps", "step:" + step + "/" + totalStep);
//                tvMessages.append("init steps: " + step + "/" + totalStep + "\n");
            }

            @Override
            public void onLaunchError(LPError error) {
//                Log.e("error", error.getCode() + " " + error.getMessage());
//                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
//                finish();
                mLiveView.onEnterRoomFaild(error);
            }

            @Override
            public void onLaunchSuccess(LiveRoom liveRoom) {
                mLiveView.onEnterRoomSuc(liveRoom);
                onInitSuccess(liveRoom);
            }
        });
    }

    /**
     * 显示视频画面（教师或学生）
     */
    public void showVideo(String userId) {
        player.playVideo(userId);
    }

    /**
     * 打开本地摄像头
     */
    public void openLocalCamram() {
        if (!videoItemState) {
            if (!recorder.isPublishing())
                recorder.publish();
            if (!recorder.isVideoAttached())
                recorder.attachVideo();
        } else {
            videoItemState = true;
            //if (recorder.isVideoAttached())
            //recorder.detachVideo();
        }
        videoItemState = !videoItemState;
    }

    /**
     * 发送文字消息
     */

    public void sendMessage(String message) {
        // 发送聊天消息
        liveRoom.getChatVM().sendMessage(message);
    }


    @Override
    public void onDestory() {
        if (liveRoom != null && liveRoom.getCurrentUser() != null && liveRoom.getCurrentUser().getType() == LPConstants.LPUserType.Teacher)
            liveRoom.requestClassEnd();
        if (liveRoom != null)
            liveRoom.quitRoom();
    }

    public void onResume() {
        if (recorder != null && recorder.isPublishing()) {
            if (recorder.isVideoAttached()) {
                recorder.detachVideo();
                recorder.attachVideo();
            }
        }
    }


}
