package com.lxt.mobile.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.OrderClassDetailBeen;
import com.lxt.mobile.adapter.OrderClassDetailAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;

public class OrderClassDetailActivity extends MBaseActivity implements TitleLayout.OnBackPressListener {
    /**
     * 标题
     */
    TitleLayout titleLayout;
    /**
     * 内容
     */
    RecyclerView recyclerView;
    private String classType;
    private String lesson_guid;//课程唯一Id
    private String course_guid;//商品唯一Id
    private String book_id;//书籍名称
    private String className;//类名称
    OrderClassDetailBeen orderClassDetailBeen;
    OrderClassDetailAdapter adapter;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.recyclerview_list_layout);
    }

    @Override
    public void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.title);
        titleLayout.setMode(true).setTitle("预约上课").setOnBackPressLister(this);
        recyclerView = (RecyclerView) findViewById(R.id.dataList);
        ViewGroup.MarginLayoutParams paramTest2 = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        paramTest2.setMargins(150, 150, 150, 150);
        recyclerView.requestLayout();
        recyclerView.setBackgroundResource(R.mipmap.course_detail_background);
        getIntentData();
    }

    /**
     * 获取传来的数据
     */
    public void getIntentData() {
        Intent intent = getIntent();
        lesson_guid = intent.getStringExtra(LxtParameters.Key.LESSON_GUID);
        course_guid = intent.getStringExtra(LxtParameters.Key.COURSE_GUID);
        book_id = intent.getStringExtra(LxtParameters.Key.BOOK_ID);
        className = intent.getStringExtra(LxtParameters.Key.CLASSNAME);
        classType = intent.getStringExtra(LxtParameters.Key.CLASSTYPE);
    }

    /**
     * 数据请求
     */
    public void getPackageSetbacks() {
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().lxt_getStudentCourseLessonInfo(getValue(LxtParameters.Key.GUID), course_guid, classType, lesson_guid, getValue(LxtParameters.Key.TOKEN));
    }

    @Override
    public void initData() {
        super.initData();
        initAdapter();
        getPackageSetbacks();
    }

    /**
     * 设置适配器
     */
    public void initAdapter() {
        adapter = new OrderClassDetailAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (data.action == LxtParameters.Action.PACKAGESETBACKS) {
            orderClassDetailBeen = (OrderClassDetailBeen) data.result;
            adapter.setData(orderClassDetailBeen);
        }
    }

    @Override
    public void onPressed(View v) {
        overridePendingTransition(R.anim.hold, R.anim.slide_out_to_bottom);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_out_to_bottom);
    }
}
