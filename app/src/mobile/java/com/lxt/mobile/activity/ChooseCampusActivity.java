package com.lxt.mobile.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.CampusChooseBeen;
import com.lxt.mobile.adapter.ChooseSchoolAdapter;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.SharedUtils;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.mobile.widget.baserecycleview.BaseRecyclerViewAdapter;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;

import java.util.List;

import static com.lxt.mobile.utils.SharedUtils.loginHandle;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 11:57
 * @description : 选择校区页面
 */
public class ChooseCampusActivity extends MBaseActivity implements BaseRecyclerViewAdapter.OnRecyclerViewListener {
    /**
     * 校区的数据集合
     */
    List<CampusChooseBeen> mSchoolBeens;
    /**
     * RecyclerView
     */
    RecyclerView mRecyclerView;
    /**
     * 适配器
     */
    ChooseSchoolAdapter mAdapter;
    /**
     * 学校的guid
     */
    String mSchool_guid;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.recyclerview_list_layout);
    }

    @Override
    public void initView() {
        ((TitleLayout) findViewById(R.id.title)).setMode(true).setTitle("校区选择");
        mRecyclerView = (RecyclerView) findViewById(R.id.dataList);
    }

    @Override
    public void initData() {
        super.initData();
        mSchoolBeens = (List<CampusChooseBeen>) getIntent().getSerializableExtra(LxtParameters.Key.SCHOOLDATALIST);
        setAdapter();
    }

    /**
     * 设置适配器
     */
    public void setAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ChooseSchoolAdapter(this, mSchoolBeens);
        mAdapter.setOnRecyclerViewListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 登陆
     */
    public void login() {
        LxtHttp.getInstance().setCallBackListener(this);
        showProgressDialog(getString(R.string.login_state));
        LxtHttp.getInstance().lxt_login(getIntent().getStringExtra(LxtParameters.Key.NAME), getIntent().getStringExtra(LxtParameters.Key.PASSWORD), mSchool_guid);
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        SharedUtils.loginHandle(this, data, mSchool_guid);
    }

    @Override
    public void onItemClick(View view, int position) {
        mSchool_guid = mSchoolBeens.get(position).getSchool_guid();
        login();
    }

    @Override
    public boolean onItemLongClick(int position) {
        return false;
    }
}
