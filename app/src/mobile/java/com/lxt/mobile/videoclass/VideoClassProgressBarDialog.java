package com.lxt.mobile.videoclass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.lxt.R;
import com.lxt.mobile.Listener.NetworkStatusCallback;
import com.lxt.sdk.util.NetworkStateUtil;
/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/8 10:47
 * @description :
 */
public class VideoClassProgressBarDialog extends Dialog implements View.OnClickListener {
    private static final int PROGRESS_UPDATE_TIME = 1000 * 5;//课件 倒计时五秒钟
    private static String NETWORK_STATE = "继续使用";
    private LinearLayout networkStateLayout; //网络状态判断布局
    private TextView netWorkToastText;//网络状态提示文字
    private Button netWorkStateBut;//网络状态 操作按钮
    private RelativeLayout loadingErrorLayout;//教案加载失败
    private ImageView loadingErrorImg;//教案加载失败操作按钮
    private RoundCornerProgressBar progress_1;//教案进度条
    private ProgressBarCountdown mProgressBarCountdown = null;//白板倒计时
    private NetworkStatusCallback mNetworkStatusCallback; //自定义 回调接口

    public VideoClassProgressBarDialog(Context context, int themeResId, NetworkStatusCallback mNetworkStatusCallback) {
        super(context, themeResId);
        this.mNetworkStatusCallback = mNetworkStatusCallback;
        initView();
    }

    public void initView() {
        setContentView(R.layout.video_network_abnormal_fargment);
        networkStateLayout = (LinearLayout) findViewById(R.id.network_state_layout);//网络提示
        netWorkToastText = (TextView) findViewById(R.id.network_toast_text);
        netWorkStateBut = (Button) findViewById(R.id.network_state_but);
        loadingErrorLayout = (RelativeLayout) findViewById(R.id.loading_error_layout);//加载提示
        loadingErrorImg = (ImageView) findViewById(R.id.loading_img);
        progress_1 = (RoundCornerProgressBar) findViewById(R.id.progress_1);//教案加载进度条
        netWorkStateBut.setOnClickListener(this);
        loadingErrorImg.setOnClickListener(this);
        getNetWorkState();
    }


    /**
     * 网络状态获取
     */
    private void getNetWorkState() {
        networkStateLayout.setVisibility(View.GONE);//网络状态
        loadingErrorLayout.setVisibility(View.GONE);//加载失败
        if (NetworkStateUtil.isNetworkAvailable(getContext())) {//是否有网络
            networkStateLayout.setVisibility(View.GONE);
            if (NetworkStateUtil.isWifi(getContext())) {//网络类型
                performCallback();
            } else {
                networkStateLayout.setVisibility(View.VISIBLE);//为流量
                netWorkToastText.setText("已经切换到2G/3G/4G网络,继续播放将会\n消耗您的流量,可能产生资费");
                NETWORK_STATE = "继续使用";
                netWorkStateBut.setText(NETWORK_STATE);
            }
        } else {
            networkStateLayout.setVisibility(View.VISIBLE);
            netWorkToastText.setText("当前无网络请检测\n");
            NETWORK_STATE = "检测网络";
            netWorkStateBut.setText(NETWORK_STATE);
        }
    }

    /**
     * 网络状态获取成功时调用
     * 启动进度条
     * 调用回调
     */
    private void performCallback() {
        setProjressInitView();//加载教案
        mNetworkStatusCallback.NetworkStatusCallback(true, null);//回调接口
    }

    /**
     * 初始化进度条
     * 加载课件时启动
     */
    private void setProjressInitView() {
        if (mProgressBarCountdown != null && !mProgressBarCountdown.equals("")) {
            mProgressBarCountdown.cancel();
        }
        mProgressBarCountdown = new ProgressBarCountdown(PROGRESS_UPDATE_TIME, 500);
        progress_1.setVisibility(View.VISIBLE);
        progress_1.setProgressColor(Color.parseColor("#FFA200"));
        progress_1.setProgressBackgroundColor(Color.parseColor("#1D2126"));
        progress_1.setMax(PROGRESS_UPDATE_TIME);
        progress_1.setProgress(0);
        mProgressBarCountdown.start();
    }

    /**
     * 关闭进度条
     */
    public void shutDownProgressBar() {
        if (null != mProgressBarCountdown) {
            mProgressBarCountdown.cancel();
        }
        if (null != progress_1) {
            progress_1.setVisibility(View.GONE);
        }
    }

    /**
     * 教案加载失败
     */
    public void lessonPlanFailure() {
        if (loadingErrorLayout != null) {
            loadingErrorLayout.setVisibility(View.VISIBLE);//加载失败
        }
        shutDownProgressBar();//关闭进度条
    }

    /**
     * 白板倒计时
     */
    class ProgressBarCountdown extends CountDownTimer {
        public ProgressBarCountdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progress_1.setProgress(PROGRESS_UPDATE_TIME - millisUntilFinished + 1000);
        }

        @Override
        public void onFinish() {
            progress_1.setVisibility(View.GONE);
            mProgressBarCountdown.cancel();
            dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.network_state_but) {
            //网络状态
            if (NETWORK_STATE.equals("检测网络")) {
                Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                getContext().startActivity(wifiSettingsIntent);
            } else if (NETWORK_STATE.equals("继续使用")) {
                performCallback();
            }
        } else if (i == R.id.loading_img) {
            //教案加载布局
            getNetWorkState();
        }
    }


}
