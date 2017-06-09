package com.lxt.mobile.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.TeacherBean;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.util.DisplayUtil;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/23 14:45
 * @description :
 */
public class TeatureDetailDialog extends Dialog {
    TeacherBean teacherBean;
    View mView;
    boolean isShow = true;
    OnCancelWathchClickListener onCancelWatchClickListener;

    public TeatureDetailDialog(@NonNull Context context, @StyleRes int themeResId, TeacherBean teacherBean) {
        super(context, themeResId);
        this.teacherBean = teacherBean;
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.teaturedetail_dialog_layout, null, false);
        setCanceledOnTouchOutside(true);
        setContentView(mView);
        Window window = getWindow();
        window.setContentView(mView);
        int deviceWidth, deviceHeight;
        deviceWidth = DisplayUtil.getScreenWidth(getContext());
        deviceHeight = DisplayUtil.getScreenHeight(getContext());
        android.view.WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = deviceWidth;
        layoutParams.height = deviceHeight;
        window.setAttributes(layoutParams);
        initView();
    }

    /**
     * 初始化View
     */
    public void initView() {
        ImageView headView = (ImageView) mView.findViewById(R.id.teature_headView);
        TextView name = (TextView) mView.findViewById(R.id.teature_name);
        TextView level = (TextView) mView.findViewById(R.id.teature_level);
        TextView teature_status = (TextView) mView.findViewById(R.id.teature_status);
        TextView teature_from = (TextView) mView.findViewById(R.id.teature_from);
        TextView teature_info = (TextView) mView.findViewById(R.id.teature_info);
        ImageLoaderUtil.getInstence().loadRoundImage(getContext(), teacherBean.getPic(), headView);
        name.setText("姓名:" + teacherBean.getFirstname() + "  年龄:" + teacherBean.getBirthtime());
        level.setText("教学经验: " + teacherBean.getExp());
        teature_status.setText("认证资格:" + "English");
        teature_from.setText("来自" + teacherBean.getNationality());
        teature_info.setText(teacherBean.getIntroduce());
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.title);
        titleLayout.setMode(false).setTitle("教师详情");
        starRatingRelative(teacherBean.getScore());
        titleLayout.findViewById(R.id.title_left_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mView.findViewById(R.id.teature_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    findViewById(R.id.teature_rl1).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.teature_rl1).setVisibility(View.VISIBLE);
                }
                isShow = !isShow;
            }
        });
        mView.findViewById(R.id.teature_cacelWatch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelWatchClickListener != null) {
                    onCancelWatchClickListener.onCliclCancelWatch(teacherBean);
                }
            }
        });
    }

    /**
     * 设置关注为相反的状态(1关注0为关注)
     */
    public void setWatchState(int state) {
        try {
            teacherBean.setFollow(state);
            if (teacherBean.getFollow() == 1) {
                ((TextView) mView.findViewById(R.id.teature_cacelWatch)).setText("取消关注");
            } else {
                ((TextView) mView.findViewById(R.id.teature_cacelWatch)).setText("关注");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 动态添加星星
     *
     * @param Count
     */
    private void starRatingRelative(double Count) {
        LinearLayout starRating = (LinearLayout) findViewById(R.id.teature_star_lay);
        for (int i = 0; i < 5; i++) {
            ImageView mImageViewTwo = new ImageView(getContext());
            if (Count >= 1) {
                mImageViewTwo.setImageResource(R.mipmap.all_star);
                Count--;
            } else if (Count < 1) {
                if (Count >= 0.8) {
                    mImageViewTwo.setImageResource(R.mipmap.all_star);
                    Count = 0;
                } else if (Count >= 0.3 && Count <= 0.7) {
                    mImageViewTwo.setImageResource(R.mipmap.yello_half_star);
                    Count = 0;
                } else {
                    mImageViewTwo.setImageResource(R.mipmap.normal_star);
                }
            }
            RelativeLayout.LayoutParams paramsTwo = new
                    RelativeLayout.LayoutParams(30, 30);
            paramsTwo.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            paramsTwo.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mImageViewTwo.setLayoutParams(paramsTwo);
            starRating.addView(mImageViewTwo);
        }
    }

    public void setOnCancelWatchClickListener(OnCancelWathchClickListener onCancelWatchClickListener) {
        this.onCancelWatchClickListener = onCancelWatchClickListener;
    }

    public interface OnCancelWathchClickListener {
        void onCliclCancelWatch(TeacherBean teacherBean);
    }
}
