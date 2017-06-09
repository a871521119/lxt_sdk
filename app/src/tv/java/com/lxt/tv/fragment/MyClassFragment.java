package com.lxt.tv.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.MyClassBeen;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.tv.activity.VideoClassActivity;
import com.lxt.tv.adapter.MyClassAdapter;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseFragment;
import com.lxt.tv.widget.CustomDialog;
import com.lxt.tv.widget.ToastDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 13:50
 * @description :我的课程
 */
public class MyClassFragment extends MBaseFragment {
    private View view;
    /*请求页数*/
    private int pageSize = 10;
    /*当前页数*/
    private int nowPage = 1;
    /*总页数*/
    private int totalPage;
    /*倒计时*/
    private MyCountDownTimer mMyCountDownTimer = null;
    /*当前所在的位置*/
    private int itemPoaition = 0;
    /**
     *
     */
    private String date = null;
    private long time = 0;
    /*预约成功的Toast*/
    private ToastDialog td;
    /*倒计时*/
    private TextView countdownText;
    /*进入教室*/
    private Button enterClassroom;
    /*数据集合*/
    private List<MyClassBeen> mMyClassBeens = new ArrayList<>();


    private int WHAT_1001 = 1001,WHAT_1002 = 1002;

    private MyClassAdapter mAdapter;
    private Handler mIOfCourseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_1001) {//倒计时
                int position = (int) msg.obj;
                setCountdownInit(position);
            } else if (msg.what == WHAT_1002) {//成功提示框
                td = new ToastDialog.Builder(getActivity()).create();
                td.show();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        td.dismiss();
                        td = null;
                        nowPage = 1;
                        getMyLessionList();
                    }
                }, 2000);
            }
        }
    };

    @Override
    public View setContentLayout(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_myclass, null);
        return view;
    }

    @Override
    public void initView() {
        initCustomViewPager(R.id.border,R.id.iofcourse_viewpager_id,pageSize,-getResources().getDimensionPixelOffset(R.dimen.x160));
        //倒计时控件
        countdownText = (TextView) view.findViewById(R.id.countdown_text);
        //进入教室
        enterClassroom = (Button) view.findViewById(R.id.enter_classroom);
    }

    @Override
    public void load() {
        nowPage = 1;
        getMyLessionList();
    }

    @Override
    public void initListener() {
        super.initListener();
        enterClassroom.setOnClickListener(this);
    }

    @Override
    public void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.error_toast_image:
                nowPage = 1;
                getMyLessionList();
                break;
            case R.id.iofcourse_viewpager_id:
                //ViewPager 选择监听--针对遥控
                setenterClassroom();
               // startActivity(VideoClassActivity.class);
                break;
            case R.id.enter_classroom:
                //进入教室button---针对手势，鼠标

                setenterClassroom();
                break;
        }
    }

    /**
     * 进入教室
     */
    private void setenterClassroom() {


        //进入教室
        if (time <= 60000L * 15) {
            //五分钟以内
            MyClassBeen intentData = mMyClassBeens.get(getCurrentItem());
            Intent intent = new Intent(getActivity(),VideoClassActivity.class);
            intent.putExtra("LessonId",intentData.getLessonId());
            intent.putExtra("TeacherId",intentData.getTeacherId());
            intent.putExtra("StudentId",intentData.getStudentId());
            intent.putExtra("SpecialtyTitle",intentData.getSpecialtyTitle());
            intent.putExtra("teacherImage",intentData.getTeacherImage());
            startActivity(intent);
        } else if (time > 60000L * 15 && time <= 3600000L * 2) {
            //两小时以内
            showToast(getString(R.string.myClass_enterroom));
        } else if (time > 3600000L * 2) {
            new CustomDialog.Builder(getActivity()).setMessage(getResources().getString(R.string.myClass_cancelDialog_title))
                    .setPositiveButton(getString(R.string.dialog_sureBtn), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            cancelReservation();
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    }).setNegativeButton(getString(R.string.dialog_NegBtn), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            }).create().show();
        }
    }

    /**
     * ViewPager 回调
     */
    @Override
    public void onPageSelected(int position) {
        sendHandlerMessage(WHAT_1001,position);
        if ((mMyClassBeens.size() - 1) == getCurrentItem() && mMyClassBeens.size() > nowPage * 10 - 1) {
            nowPage = nowPage + 1;
            getMyLessionList();
        }
    }

    /**
     * 发送Handler消息
     * @param position
     */
    private void sendHandlerMessage(int what,int position){
        Message msg = mIOfCourseHandler.obtainMessage();
        msg.what = what;
        msg.obj = position;
        mIOfCourseHandler.handleMessage(msg);
    }

    /**
     * 倒计时处理
     */
    private void setCountdownInit(int position) {
        if (!mMyClassBeens.get(position).getServerTime().equals("")
                && !mMyClassBeens.get(position).getStartTime().equals("")) {
            cancelDownTimer();
            //计算时间差
            long HoursTimeDifference = TimeUtil.timeDifference(
                    mMyClassBeens.get(position).getServerTime(),
                    mMyClassBeens.get(position).getStartTime());
            if (HoursTimeDifference > 1000) {
                mMyCountDownTimer = new MyCountDownTimer(HoursTimeDifference, 1000);
                mMyCountDownTimer.start();
            } else {
                classState(3);
            }
        }
    }

    /**
     * 课程状态
     * @param state 1 取消预约 2 进入教室 3 已经开始上课
     */
    private void classState(int state){
        switch (state){
            case 1:
                countdownText.setTextColor(Color.WHITE);
                enterClassroom.setText(getResources().getString(R.string.myClass_classCancelBooking));
                enterClassroom.setBackgroundResource(R.drawable.icon_short_redbg);
                break;
            case 2:
                //两小时以内且大于十五分钟不可点击  时间变黑
                countdownText.setTextColor(Color.WHITE);
                enterClassroom.setText(getResources().getString(R.string.myClass_classEnterClassRoom));
                enterClassroom.setBackgroundResource(R.drawable.huiseanniu);
                break;
            case 3:
                //十五分钟以内 进入教室
                date = getResources().getString(R.string.myClass_classNowStart);
                countdownText.setTextColor(Color.RED);
                enterClassroom.setText(getResources().getString(R.string.myClass_classNowStart));
                enterClassroom.setBackgroundResource(R.drawable.icon_short_redbg);
                break;
        }
        countdownText.setText(date);
    }

    /**
     * 倒计时
     */
    class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            time = millisUntilFinished;
            date =  TimeUtil.changeTimerFormat(millisUntilFinished);
            if (millisUntilFinished <= 60000L * 15) {
                classState(3);
                mMyCountDownTimer.cancel();
            } else if (millisUntilFinished > 60000L * 15 && millisUntilFinished <= 3600000L * 2) {
                classState(2);
            } else if (millisUntilFinished > 3600000L * 2) {
                classState(1);
            }
        }

        @Override
        public void onFinish() {
            cancelDownTimer();
        }
    }

    /**
     * 取消倒计时
     */
    private void cancelDownTimer(){
        if (mMyCountDownTimer != null) {
            mMyCountDownTimer.cancel();
            mMyCountDownTimer = null;
        }
    }

    /**
     * 网络请求我的课程
     */
    public void getMyLessionList() {
        Map<String, String> map = new HashMap<>();
        map.put(LxtParameters.Key.NOWPAGE, String.valueOf(nowPage));
        map.put(LxtParameters.Key.PAGESIZE, String.valueOf(pageSize));
        getHttpResult(LxtParameters.Action.GET_MYLESSIONLIST,map);
    }

    /**
     * 取消预约
     */
    public void cancelReservation() {
            Map<String, String> map = new HashMap<>();
            map.put(LxtParameters.Key.BESPEAK_GUID, mMyClassBeens.get(getCurrentItem()).getLessonId());
            map.put(LxtParameters.Key.TEACHER_GUID, mMyClassBeens.get(getCurrentItem()).getTeacherId());
            map.put(LxtParameters.Key.CLASSTIMESTAMP, mMyClassBeens.get(getCurrentItem()).getStartTime());
            map.put(LxtParameters.Key.TYPE,"2");
            getHttpResult(LxtParameters.Action.CANCELRESERVATION,map);
    }

    /**
     * 是否需要极光推送  1 需要 2不需要
     * @return
     */
    //   private String type(){
//        if (Constants.AURORA_PUSH_ID != "1") {
//            return "1";
//        }
//        return "2";
    //   }

    @Override
    public void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(data.action,LxtParameters.Action.GET_MYLESSIONLIST)) {
            MyClassBeen classBeen = (MyClassBeen) data.result;
            if(classBeen.getTotalCount() <=0){
                addErrorView(data.action,getString(R.string.myClass_classNoClassPleaseBooking));
            }else {
                if(classBeen.getData() != null && !classBeen.getData().isEmpty()){
                    if (nowPage == 1) mMyClassBeens.clear();
                    mMyClassBeens.addAll(classBeen.getData());
                    mAdapter = new MyClassAdapter(getActivity(), mMyClassBeens);
                    setViewPagerAdapter(mAdapter);
                    sendHandlerMessage(WHAT_1001,0);
                }
            }

        }else if(TextUtils.equals(data.action, LxtParameters.Action.CANCELRESERVATION)){
            showToast((String) data.result);
            nowPage = 1;
            sendHandlerMessage(WHAT_1002,0);
        }

    }

    @Override
    public void onFailed(String action, String code) {
        super.onFailed(action, code);
        if (TextUtils.equals(action, LxtParameters.Action.GET_MYLESSIONLIST)){
            addErrorView(action,getString(R.string.myClass_classGetFalid));
        }else {
            if (code.equals("SN401")) {
                itemPoaition = getCurrentItem();
                nowPage = 1;
                getMyLessionList();
            } else if (code.equals("SN400")) {
                showToast(getResources().getString(R.string.myClass_classCancelFalid));
            }
        }
    }




}
