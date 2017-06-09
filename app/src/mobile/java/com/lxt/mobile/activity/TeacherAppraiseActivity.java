package com.lxt.mobile.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.ConmmentBeen;
import com.lxt.been.TeatureAppraiseBeen;
import com.lxt.mobile.adapter.TeatureAppraiseAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.PullRefreshManager;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.mobile.widget.baserecycleview.DividerItemDecoration;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherAppraiseActivity extends MBaseActivity implements PullRefreshManager.TwinkRefreshLoadListener {
    RecyclerView recyclerView;
    TwinklingRefreshLayout twinklingRefreshLayout;
    PullRefreshManager manager;
    int page = 1;
    TeatureAppraiseAdapter adapter;
    List<ConmmentBeen> conmmentBeenList = new ArrayList<>();

    @Override
    public void setContentLayout() {
        setContentView(R.layout.list_refreshview);
    }

    @Override
    public void initView() {
        TitleLayout titleLayout = (TitleLayout) findViewById(R.id.title);
        titleLayout.setMode(true).setTitle("学生评论");
        recyclerView = (RecyclerView) findViewById(R.id.recy_refresh_view);
        twinklingRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.recy_refresh_layout);
        manager = new PullRefreshManager(twinklingRefreshLayout, this);
        twinklingRefreshLayout = manager.getDefaultRefreshLayout(false, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        showProgressDialog();
    }

    /**
     * 获取数据
     */
    public void loadData() {
        LxtHttp.getInstance().setCallBackListener(this);
//        Intent intent = new Intent();
//        intent.putExtra("teature_guid", "00f3cc1aa24511e6945700163e033e10");
//        setIntent(intent);
        LxtHttp.getInstance().lxt_getTeacherCommentList(getValue(LxtParameters.Key.GUID), getIntent().getStringExtra("teature_guid"), page + ""
                , 10 + "", getValue(LxtParameters.Key.TOKEN)
        );
    }

    @Override
    public void initData() {
        super.initData();
        initAdapter();
        loadData();
    }

    /**
     * 初始化适配器
     */
    public void initAdapter() {
        adapter = new TeatureAppraiseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, true));
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        TeatureAppraiseBeen teatureAppraiseBeen = (TeatureAppraiseBeen) data.result;
        if (teatureAppraiseBeen != null && teatureAppraiseBeen.getData() != null && teatureAppraiseBeen.getData().size() != 0) {
            if (page == 1)
                conmmentBeenList.clear();
            conmmentBeenList.addAll(teatureAppraiseBeen.getData());
            adapter.setData(teatureAppraiseBeen);
        }
        teatureAppraiseBeen.setData(conmmentBeenList);
        adapter.setData(teatureAppraiseBeen);
        manager.stopRefresh();
        dismissProgressDialog();
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        manager.stopRefresh();
        dismissProgressDialog();
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        manager.stopRefresh();
        dismissProgressDialog();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (conmmentBeenList.size() > page - 1) {
            page = page + 1;
            loadData();
        }
    }
}
