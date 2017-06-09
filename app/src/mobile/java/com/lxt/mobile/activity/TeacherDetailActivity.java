package com.lxt.mobile.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.TeacherBean;
import com.lxt.mobile.adapter.TeacherDetailDateAdapter;
import com.lxt.mobile.adapter.TeacherDetailTimeTableAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.been.NavDate;
import com.lxt.mobile.widget.ToolbarHorizontalScrollView;

import com.lxt.sdk.util.LogUtil;
import com.lxt.sdk.util.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.focusable;
import static android.R.attr.offset;
import static com.baijiahulian.livecore.context.LPConstants.d;
import static com.lxt.R.id.date;


/**
 * Created by LiWenJiang on 2017/6/5.
 */

public class TeacherDetailActivity extends MBaseActivity implements TeacherDetailTimeTableAdapter.OnTableItemClickListener{

    private static final long DEFAULT_DURATION = 300L;
    private static final float DEFAULT_DAMPING = 1.5f;

    private TeacherBean teacher;

    private TextView age,name,work,type,from,like,introduce;

    private int MAXLINE_INTRODUCE = 2;//介绍默认最大行数
    private boolean isExpand;//简介是否展开
    private ImageView expandImage;

    private ToolbarHorizontalScrollView navDateBar,navTimeBar;
    // 日期 和 时间
    private RecyclerView navTitleRecycler,navTimeRecycler;

    private TeacherDetailDateAdapter dateAdapter;
    private TeacherDetailTimeTableAdapter timeTableAdapter;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_teacher_detail);
    }

    @Override
    public void initView() {

        age = (TextView) findViewById(R.id.age);
        age.setText(Html.fromHtml(getString(R.string.age,23)));
        name = (TextView) findViewById(R.id.name);
        name.setText(Html.fromHtml(getString(R.string.name,23)));
        work = (TextView) findViewById(R.id.work);
        work.setText(Html.fromHtml(getString(R.string.work,23)));
        type = (TextView) findViewById(R.id.type);
        type.setText(Html.fromHtml(getString(R.string.type,23)));
        from = (TextView) findViewById(R.id.from);
        from.setText(Html.fromHtml(getString(R.string.from,23)));
        introduce = (TextView) findViewById(R.id.introduce);
        introduce.setHeight(introduce.getLineHeight() * MAXLINE_INTRODUCE);
        expandImage = (ImageView) findViewById(R.id.expandImage);
        expandImage.setOnClickListener(this);
        like = (TextView) findViewById(R.id.like);
        like.setText(Html.fromHtml(getString(R.string.like,"120")));

        navDateBar = (ToolbarHorizontalScrollView) findViewById(R.id.navTitleToolbar);
        navTimeBar = (ToolbarHorizontalScrollView) findViewById(R.id.navContentToolBar);
        navDateBar.setToolbar(navTimeBar);
        navTimeBar.setToolbar(navDateBar);
        //日期
        navTitleRecycler = (RecyclerView) findViewById(R.id.navTitleRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        navTitleRecycler.setLayoutManager(layoutManager);
        dateAdapter = new TeacherDetailDateAdapter();
        navTitleRecycler.setAdapter(dateAdapter);
        navTimeRecycler = (RecyclerView) findViewById(R.id.navTimeRecycler);
        timeTableAdapter = new TeacherDetailTimeTableAdapter(this);
        LinearLayoutManager tableLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tableLayoutManager.setSmoothScrollbarEnabled(true);
        tableLayoutManager.setAutoMeasureEnabled(true);
        navTimeRecycler.setLayoutManager(tableLayoutManager);
        navTimeRecycler.setHasFixedSize(true);
        navTimeRecycler.setNestedScrollingEnabled(false);
        navTimeRecycler.setAdapter(timeTableAdapter);

    }





    @Override
    public void initData() {
        super.initData();

//        teacher = (TeacherBean) getIntent().getExtras().getSerializable(LxtParameters.Key.TEACHER_GUID);
//        LxtHttp.getInstance().setCallBackListener(this);
//        LxtHttp.getInstance().lxt_getTeacherCourse(SharedPreference.getData(LxtParameters.Key.GUID),
//                teacher.getTeacher_guid(),
//                SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
//                SharedPreference.getData(LxtParameters.Key.TOKEN));
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()){
            case R.id.expandImage:
                introduceExpand();
                break;
        }
    }

    /**
     * 自我介绍展开操作
     */
    private void introduceExpand() {
        isExpand=!isExpand;
        introduce.clearAnimation();  //清除动画
        final int tempHight;
        final int startHight=introduce.getHeight();  //起始高度
        int duration = 200;
        if(isExpand){
                    /* 折叠效果，从长文折叠成短文*/
            tempHight = introduce.getLineHeight() * introduce.getLineCount() - startHight;  //为正值，长文减去短文的高度差
            expandImage.startAnimation(arrowsRotateAnimation(0,180,duration));
        }else {
                    /*展开效果，从短文展开成长文*/
            tempHight = introduce.getLineHeight() * MAXLINE_INTRODUCE - startHight;//为负值，即短文减去长文的高度差
            expandImage.startAnimation(arrowsRotateAnimation(180,0,duration));
        }

        Animation animation = new Animation() {
            //interpolatedTime 为当前动画帧对应的相对时间，值总在0-1之间
            protected void applyTransformation(float interpolatedTime, Transformation t) { //根据ImageView旋转动画的百分比来显示textview高度，达到动画效果
                introduce.setHeight((int) (startHight + tempHight * interpolatedTime));//原始长度+高度差*（从0到1的渐变）即表现为动画效果
            }
        };
        animation.setDuration(duration);
        introduce.startAnimation(animation);
    }

    /**
     * 旋转动画
     * @param from
     * @param to
     * @return
     */
    private RotateAnimation arrowsRotateAnimation(float from,float to,int duration){
        RotateAnimation animation = new RotateAnimation(from, to, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        return animation;
    }

    @Override
    public void onTableItemClickListeren(String time, int col) {
        showToast("time="+time +  " col="+col);
    }
}
