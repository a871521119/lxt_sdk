package com.lxt.mobile.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.utils.DataClearManager;
import com.lxt.mobile.utils.GetFileSizeUtil;
import com.lxt.mobile.utils.SharedUtils;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.sdk.util.LxtConfig;
import com.lxt.sdk.util.PhoneInfo;

import java.io.File;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 9:42
 * @description : 系统设置
 */
public class SystemSettingActivity extends MBaseActivity {
    /**
     * 版本号
     */
    TextView setting_version;

    /**
     * 清除缓存
     */
    View clearCache;

    /**
     * 缓存的大小
     */
    TextView setting_cecheCount;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_system_setting);
    }

    @Override
    public void initView() {
        ((TitleLayout) findViewById(R.id.title)).setMode(true).setTitle("系统设置");
        setting_version = (TextView) findViewById(R.id.setting_version);
        clearCache = findViewById(R.id.setting_clearCache);
        setting_cecheCount = (TextView) findViewById(R.id.setting_cecheCount);
    }

    @Override
    public void initListener() {
        super.initListener();
        clearCache.setOnClickListener(this);//清除缓存
        findViewById(R.id.setting_fellBack).setOnClickListener(this);//意见反馈
        findViewById(R.id.setting_updata_password).setOnClickListener(this);//修改密码
        findViewById(R.id.setting_logout).setOnClickListener(this);//修改密码
    }

    @Override
    public void initData() {
        super.initData();
        setting_version.setText(PhoneInfo.getVersion(this));
        getChechSize();

    }

    /**
     * 计算缓存的大小并复制
     */
    public void getChechSize() {
        File file1 = new File(LxtConfig.PICCACHE);
        try {
            GetFileSizeUtil sizeUtil = GetFileSizeUtil.getInstance();
            Long l1 = sizeUtil.getFileSize(file1);
            setting_cecheCount.setText(sizeUtil.FormetFileSize(l1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.setting_clearCache:
                showProgressDialog("正在清除");
                clearCache.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DataClearManager.cleanCustomCache(LxtConfig.PICCACHE);
                        ImageLoaderUtil.clearCache(SystemSettingActivity.this);
                        showToast("清除成功");
                        setting_cecheCount.setText("0.0MB");
                        dismissProgressDialog();
                    }
                }, 1000);

                break;
            case R.id.setting_fellBack:
                startActivity(new Intent(this, FeelBackAcctivity.class));
                break;
            case R.id.setting_updata_password:
                startActivity(new Intent(this, UpdataPasswordActivity.class));
                break;
            case R.id.setting_logout:
                SharedUtils.logoutHandle(this);
                break;

            default:

                break;
        }
    }
}
