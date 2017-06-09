package com.lxt.tv.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lxt.R;
import com.lxt.been.CampusChooseBeen;
import com.lxt.been.UserBeen;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.tv.adapter.ChooseScoolAdapter;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseActivity;
import com.lxt.util.ToastUitl;

import java.util.ArrayList;
import java.util.List;


public class ChooseScoolActivity extends MBaseActivity implements AdapterView.OnItemClickListener {
    /**
     * 小区的列表
     */
    private ListView campusChooseListview;
    /**
     * 错误提示布局
     */
    private LinearLayout noDataErrorLayout;
    /**
     * 错误操作button
     */
    private ImageView bookingClassRefresh;
    /**
     * 外界传来的用户名密码
     */
    private String userName, passWord;
    /**
     * 校区的集合
     */
    List<CampusChooseBeen> campusChooseBeens;
    /**
     * 校区展示适配器
     */
    private ChooseScoolAdapter mCampusChooseAdapter;
    /**
     * 学校id
     */
    private String school_guid = "";
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_choose_scool);
    }

    @Override
    public void initView() {
        campusChooseListview = (ListView) findViewById(R.id.campus_choose_listview);
        noDataErrorLayout = (LinearLayout) findViewById(R.id.loading_error_layout);
        bookingClassRefresh = (ImageView) findViewById(R.id.error_toast_image);
        noDataErrorLayout.setVisibility(View.GONE);
        campusChooseBeens = new ArrayList<>();
        mCampusChooseAdapter=new ChooseScoolAdapter(campusChooseBeens,this);
        campusChooseListview.setAdapter(mCampusChooseAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        campusChooseListview.setOnItemClickListener(this);
        bookingClassRefresh.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        if (!getIntent().getStringExtra(LxtParameters.Key.PHONE).equals("") &&
                !getIntent().getStringExtra(LxtParameters.Key.PASSWORD).equals("")) {
            userName = getIntent().getStringExtra(LxtParameters.Key.PHONE);
            passWord = getIntent().getStringExtra(LxtParameters.Key.PASSWORD);
            campusChooseBeens.addAll(JsonUtils.fromJsonArray(getIntent().getStringExtra("campusData"), CampusChooseBeen.class));
            mCampusChooseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()){
            case R.id.error_toast_image:
                if(!TextUtils.isEmpty(school_guid)){
                    setLoginHttp();
                }else{
                    ToastUitl.showShort(getString(R.string.outOfData));
                    finish();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        school_guid = campusChooseBeens.get(position).getSchool_guid();
        setLoginHttp();
    }
    /**
     * 登录 携带校区
     * */
    private void setLoginHttp(){
        LxtHttp.getInstance().lxt_login(userName,passWord,school_guid);
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (TextUtils.equals(LxtParameters.Action.LOGIN,data.action)){
            UserBeen user = (UserBeen) data.result;
            SharedPreference.setData(LxtParameters.Key.TOKEN,user.getToken());
            SharedPreference.setData(LxtParameters.Key.GUID,user.getGuid());
            SharedPreference.setData(LxtParameters.Key.SCHOOL_GUID,school_guid);
            SharedPreference.setData(LxtParameters.Key.LOGINSTYLE,"true");
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }





}
