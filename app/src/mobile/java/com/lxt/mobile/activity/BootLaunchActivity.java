package com.lxt.mobile.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.base.BaseBeen;
import com.lxt.been.ProverbBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.been.UpdataBeen;
import com.lxt.mobile.utils.LxtUtils;
import com.lxt.mobile.utils.SharedUtils;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.HttpPost;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.listener.LxtCallBack;
import com.lxt.sdk.util.JsonUtils;
import com.lxt.sdk.util.PhoneInfo;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/12 11:57
 * @description : 闪屏页面
 */
public class BootLaunchActivity extends MBaseActivity {
    /**
     * 每日谚语
     */
    TextView boot_launch_Proverb;
    /**
     * 背景
     */
    ImageView boot_animation_father_bg;

    /**
     * 标题
     */
    TextView boot_launch_submit_title;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_boot_launch);
    }


    @Override
    public void initView() {
        boot_launch_Proverb = (TextView) findViewById(R.id.boot_launch_Proverb);
        boot_animation_father_bg = (ImageView) findViewById(R.id.boot_animation_father_bg);
        boot_launch_submit_title = (TextView) findViewById(R.id.boot_launch_submit_title);
    }

    @Override
    public void initData() {
        super.initData();
        //中文 谚语
        if (TextUtils.isEmpty(SharedPreference.getData("content")))
            boot_launch_Proverb.setText("      " + getString(R.string.defaultProverb));
        else
            boot_launch_Proverb.setText("      " + SharedPreference.getData("content"));

        if (TextUtils.isEmpty(SharedPreference.getData("title")))
            boot_launch_submit_title.setText(getString(R.string.defaultTitle));
        else
            boot_launch_submit_title.setText(SharedPreference.getData("title"));

        if (!TextUtils.isEmpty(SharedPreference.getData("image")))
            ImageLoaderUtil.getInstence().loadImage(this, SharedPreference.getData("image"), boot_animation_father_bg, R.mipmap.boot_launch_bg);
        getProverb();
        checkUpdata();
    }

    @Override
    public void initListener() {
        super.initListener();
        findViewById(R.id.boot_launch_submit_btn).setOnClickListener(this);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.boot_launch_submit_btn:
                String loginStyle = SharedPreference.getData(LxtParameters.Key.LOGINSTYLE);//得到保存的用户名密码
                String welcomeIsShown = SharedPreference.getData("welcomeIsShown");
                if (!TextUtils.equals(welcomeIsShown, "1")) {
                    startActivity(new Intent(this, WelcomeActivity.class));
                }else {
                    if (loginStyle.equals("1")) {//正常
                        SharedUtils.jumToHomePageHandle(this);
                    } else {//预约课程哪里
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                }
                finish();
                break;
            default:

                break;
        }
    }

    /**
     * 请求每日谚语
     */
    public void getProverb() {
        LxtHttp.getInstance().setCallBackListener(this);
        LxtHttp.getInstance().getProverb(PhoneInfo.getVersion(this), Utils.getGroupGuid());
    }

    public void checkUpdata() {
        HttpPost httpPost = new HttpPost();
        Map<String, Object> map = new HashMap<>();
        map.put("time", TimeUtil.getTime());
        map.put("type", "2");
        map.put("group_guid", Utils.getGroupGuid());
        map.put("version", PhoneInfo.getVersion(this));
        httpPost.post(LxtParameters.getUrl() + "checkVersion", map, new LxtCallBack() {
            @Override
            public void onResponse(String result) {
                if (!TextUtils.isEmpty(result)) {
                    if (TextUtils.equals(JsonUtils.getValue(result, "ServerNo"), "SN200")) {
                        UpdataBeen updataBeen = (UpdataBeen) JsonUtils.fromJson(JsonUtils.getValue(result, "ResultData"), UpdataBeen.class);
                        showUpdataDialog(updataBeen);
                    }
                }
            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        if (data.action.equals(LxtParameters.Action.PROVERB)) {
            ProverbBeen been = (ProverbBeen) data.result;
            SharedPreference.setData("content", been.getContent());
            SharedPreference.setData("title", been.getTitle());
            SharedPreference.setData("image", been.getImage());
        }
    }

    /**
     * 弹出对话框通知用户更新程序
     * <p/>
     * 弹出对话框的步骤：
     * 1.创建alertDialog的builder.
     * 2.要给builder设置属性, 对话框的内容,样式,按钮
     * 3.通过builder 创建一个对话框
     * 4.对话框show()出来
     */
    protected void showUpdataDialog(UpdataBeen updataBeen) {
        if (updataBeen.getUpdate() == 1) {
            LxtUtils.checkForUpData(this, updataBeen.getDownload_address(), false);
        } else {
            LxtUtils.checkForUpData(this, updataBeen.getDownload_address(), true);
        }
    }
}
