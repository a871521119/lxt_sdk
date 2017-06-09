package com.lxt.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.lxt.R;
import com.lxt.been.TeacherBean;
import com.lxt.mobile.adapter.TeatureListAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.widget.StatusView;
import com.lxt.mobile.widget.TeatureDetailDialog;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.mobile.widget.baserecycleview.BaseRecyclerViewAdapter;
import com.lxt.mobile.widget.baserecycleview.DividerItemDecoration;
import com.lxt.mobile.widget.baserecycleview.ScaleInAnimation;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.sdk.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 9:42
 * @description : 我的关注
 */
public class WatchListActivity extends MBaseActivity implements BaseRecyclerViewAdapter.OnRecyclerViewListener, TeatureDetailDialog.OnCancelWathchClickListener {
    List<TeacherBean> teacherBeanList;
    TeatureListAdapter adapter;
    RecyclerView recyclerView;
    TeatureDetailDialog dialog;
    TeacherBean dialog_teacherBean;
    private StatusView mStatusView;

    private String fromList = "1";////1默认 我的关注过来 2从选课程 教师列表里面的关注来的
    private String className;
    private String lessonGuid;


    @Override
    public void setContentLayout() {
        setContentView(R.layout.recyclerview_list_layout);
    }

    @Override
    public void initView() {
        ((TitleLayout) findViewById(R.id.title)).setMode(true).setTitle("我的关注");
        teacherBeanList = new ArrayList<>();
        mStatusView = (StatusView) findViewById(R.id.main_multiplestatusview);
        recyclerView = (RecyclerView) findViewById(R.id.dataList);
        mStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWatchList();
            }
        });
        mStatusView.showContent(recyclerView);
    }

    @Override
    public void initData() {
        super.initData();
        Intent intentTemp = getIntent();
        className = intentTemp.getStringExtra(LxtParameters.Key.NAME);
        lessonGuid = intentTemp.getStringExtra(LxtParameters.Key.LESSON_GUID);
        fromList =  intentTemp.getStringExtra("fromList");
        initAdapter();
        getWatchList();
    }

    /**
     * 请求数据
     */
    public void getWatchList() {
        LxtHttp.getInstance().setCallBackListener(this);
        String guid = SharedPreference.getData(LxtParameters.Key.GUID);
        String school_guid = SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID);
        String token = SharedPreference.getData(LxtParameters.Key.TOKEN);
        LxtHttp.getInstance().lxt_getTeacherCollectionList(guid, school_guid, getLessionGuid(), token);
    }


    private String getLessionGuid() {
       return TextUtils.equals("2",fromList) ? lessonGuid : null;
    }

    /**
     * 取消关注
     */
    public void cancelWatch(String teatureGuid) {
        LxtHttp.getInstance().lxt_teacherCollection(getValue(LxtParameters.Key.GUID), getValue(LxtParameters.Key.SCHOOL_GUID), teatureGuid, getValue(LxtParameters.Key.TOKEN));
    }

    void initAdapter() {
        adapter = new TeatureListAdapter(this, teacherBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.openLoadAnimation();
        adapter.setmSelectAnimation(new ScaleInAnimation());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, true));
        adapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onSuccessed(String action, String result) {
        super.onSuccessed(action, result);
        if (action.equals(LxtParameters.Action.FOLLOW)) {
            getWatchList();
            if (dialog_teacherBean.getFollow() == 1) {
                LogUtil.e("取消关注成功");
                dialog.setWatchState(0);
            } else {
                dialog.setWatchState(1);
                LogUtil.e("关注成功");
            }
        } else {
            List<TeacherBean> list = JsonUtils.fromJsonArray(JsonUtils.getValue(JsonUtils.getValue(result, "ResultData"), "teacher"), TeacherBean.class);
            if (list.size() == 0) {
                showToast("你还没有关注的教师");
                teacherBeanList.clear();
                adapter.notifyDataSetChanged();
                mStatusView.showEmpty(getString(R.string.empty_data));
                return;
            }
            //for (int i = 0; i < 30; i++) {
            teacherBeanList.clear();
            teacherBeanList.addAll(list);
            adapter.notifyDataSetChanged();
            mStatusView.showContent(recyclerView);
        }
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        mStatusView.showError();
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        mStatusView.showEmpty(getString(R.string.empty_data));
    }

    @Override
    public void onItemClick(View view, int position) {
            dialog = new TeatureDetailDialog(this, R.style.dialog, teacherBeanList.get(position));
        dialog.setOnCancelWatchClickListener(this);
        dialog.show();
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }

    @Override
    public void onCliclCancelWatch(TeacherBean teacherBean) {
        dialog_teacherBean = teacherBean;
        cancelWatch(teacherBean.getTeacher_guid());
    }


}
