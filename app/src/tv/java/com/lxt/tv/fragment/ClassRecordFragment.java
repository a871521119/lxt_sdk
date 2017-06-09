package com.lxt.tv.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.lxt.R;
import com.lxt.been.ClassRecordBeen;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.tv.activity.ClassAppraiseDialogActivity;
import com.lxt.tv.adapter.CourseRecordAdapter;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseFragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/29 16:14
 * @description :
 */
public class ClassRecordFragment extends MBaseFragment implements ViewPager.OnPageChangeListener,CourseRecordAdapter.EvaluationListener {
    private View view;
    /*数据源*/
    List<ClassRecordBeen> mClassRecordBeens=new ArrayList<>();

    /*适配器*/
    private CourseRecordAdapter mCourseRecordAdapter;
    /*pager*/
    private ViewPager course_record_viewpager;
    /*请求页数*/
    private int pageSize = 10;
    /*当前页数*/
    private int nowPage = 1;
    /*当前总课数*/
    private int classCount;
    /*当前位置*/
    private int itemPoaition = 0;

    @Override
    public View setContentLayout(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_class_record, null);
        return view;
    }

    @Override
    public void initView() {
        viewPagerInit();
    }

    @Override
    public void load() {
        nowPage=1;
        courseRecordHttp();
    }

    @Override
    public void initListener() {
        super.initListener();
        course_record_viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()){
            case R.id.error_toast_image:
                nowPage=1;
                courseRecordHttp();
                break;
        }
    }

    /**
     * ViewPager初始化
     * */
    private void viewPagerInit(){
        //ViewPager初始化
        course_record_viewpager = (ViewPager) view.findViewById(R.id.course_record_viewpager);
        // 2.设置页与页之间的间距
        course_record_viewpager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.x100));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {
        //网络请求 我的课程
        if((mClassRecordBeens.size() - 1) == course_record_viewpager.getCurrentItem() && mClassRecordBeens.size() > nowPage * 10 - 1){
            nowPage = nowPage + 1;
            courseRecordHttp();
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * 课程记录
     */
    public void courseRecordHttp(){
        boolean isEmpty = TextUtils.isEmpty(SharedPreference.getData(LxtParameters.Key.GUID));
        if (!isEmpty) {
            Map<String, String> map = new HashMap<>();
            map.put(LxtParameters.Key.PAGESIZE, String.valueOf(pageSize));
            map.put(LxtParameters.Key.NOWPAGE, String.valueOf(nowPage));
            map.put("par", JsonUtils.toJson(map));
            getHttpResult(LxtParameters.Action.LESSONRECORD,map);
        }else {
            addErrorView(LxtParameters.Action.LESSONRECORD,getString(R.string.outOfData));
        }
    }

    @Override
    public void bindViewData(BaseBeen data) {
        super.bindViewData(data);
       ClassRecordBeen record = (ClassRecordBeen) data.result;
        if (record !=null && record.getMessage() != null){
            if(!record.getMessage().isEmpty()){
                if(nowPage==1){
                    mClassRecordBeens.clear();
                }
                mClassRecordBeens.addAll(record.getMessage());
                if(mClassRecordBeens.size()==0){
                    addErrorView(data.action,getString(R.string.classRecord_noRecord));
                }else{
                    if(mCourseRecordAdapter==null){
                        mCourseRecordAdapter = new CourseRecordAdapter(getActivity(),mClassRecordBeens);
                        mCourseRecordAdapter.setEvaluationListener(this);
                        course_record_viewpager.setAdapter(mCourseRecordAdapter); // 为viewpager设置adapter
                    }else {
                        mCourseRecordAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onEvaluationListener(int position) {
        if(mClassRecordBeens.get(position).getComment()==2){
            Toast.makeText(getActivity(),"当前课程已评价!", Toast.LENGTH_SHORT).show();
        }else{
            Intent mIntent = new Intent(getActivity(),ClassAppraiseDialogActivity.class);
            mIntent.putExtra(LxtParameters.Key.LESSON_GUID,mClassRecordBeens.get(position).getBespeak_guid());
            mIntent.putExtra(LxtParameters.Key.TEACHER_GUID,mClassRecordBeens.get(position).getTeacher_guid());
            startActivityForResult(mIntent,100);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(mCourseRecordAdapter != null){
                mCourseRecordAdapter.updateEvaluation(course_record_viewpager.getCurrentItem(),SharedPreference.getData("star"));
            }
        }
    }
}
