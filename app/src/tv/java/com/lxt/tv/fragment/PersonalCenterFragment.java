package com.lxt.tv.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.base.AppManager;
import com.lxt.been.PersonalBeen;
import com.lxt.imageloader.ImageLoaderUtil;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseFragment;


/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 高明宇
 * @create-time : 2016/12/21 13:53
 * @description : 个人信息
 */
public class PersonalCenterFragment extends MBaseFragment {
    private View view;
    //头像
    private ImageView user_img;

    //个人信息
    private TextView user_name, user_sex,
            user_phone, user_email, user_birthday;

    //退出button
    private Button mExitButton;
    //bean
    private PersonalBeen mUser_databean;

    @Override
    public View setContentLayout(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_personalcenter, null);
        return view;
    }

    @Override
    public void initView() {
        user_img = (ImageView) view.findViewById(R.id.user_img);
        user_name = (TextView) view.findViewById(R.id.user_name);
        user_sex = (TextView) view.findViewById(R.id.user_sex);
        user_phone = (TextView) view.findViewById(R.id.user_phone);
        user_email = (TextView) view.findViewById(R.id.user_email);
        user_birthday = (TextView) view.findViewById(R.id.user_birthday);
        mExitButton = (Button) view.findViewById(R.id.exit_button_id);
    }

    @Override
    public void load() {
        getHttpResult(LxtParameters.Action.MYMESSAGE, null);
    }

    @Override
    public void initListener() {
        super.initListener();
        mExitButton.setOnClickListener(this);
    }

    @Override
    public void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.exit_button_id:
                SharedPreference.setData(LxtParameters.Key.LOGINSTYLE,"exitStyle");
//                startActivity(LoginActivity.class);
//                getActivity().finish();
                AppManager.getAppManager().finishAllActivity();
                break;
        }

    }


    @Override
    public void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        mUser_databean = (PersonalBeen) data.result;
        if (!TextUtils.isEmpty(mUser_databean.getNickname())) {
            user_name.setText(String.format(getString(R.string.user_name),mUser_databean.getNickname()));
        } else {
            user_name.setText(String.format(getString(R.string.user_name),"student"));
        }

        if (mUser_databean.getSex() == 1) {
            user_sex.setText(String.format(getString(R.string.user_sex),"男"));
        } else {
            user_sex.setText(String.format(getString(R.string.user_sex),"女"));
        }

        if (!TextUtils.isEmpty(mUser_databean.getTel())){
            user_phone.setText(String.format(getString(R.string.user_phone),mUser_databean.getTel()));
        }

        if (!TextUtils.isEmpty(mUser_databean.getEmail())) {
            user_email.setText(String.format(getString(R.string.user_email),mUser_databean.getEmail()));
        } else {
            user_email.setText(String.format(getString(R.string.user_email),"暂无"));
        }

        if (Long.parseLong(mUser_databean.getBirthtime()) != 0) {
            user_birthday.setText(String.format(getString(R.string.user_birthday), TimeUtil.
                    getStrTime(mUser_databean.
                            getBirthtime() + "", TimeUtil.dateFormatYMD)));
        } else {
            user_birthday.setText(String.format(getString(R.string.user_birthday),"暂无"));
            //获取当前时间
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date curDate = new Date(System.currentTimeMillis());
//            String aa = formatter.format(curDate);
//            user_birthday.setText(TimeUtil.getStrTime(TimeUtil.
//                    getTimeStamp(aa, "yyyy-MM-dd") + "", Constants.TIME_TURN_TIMESTAMP) + "");
        }
        if (!TextUtils.isEmpty(mUser_databean.getPic())) {
            ImageLoaderUtil.getInstence().loadRoundImage(getActivity(), mUser_databean.getPic(), user_img);
        }
    }

    @Override
    public void onFailed(String action, String result) {
        super.onFailed(action, result);
        showToast("个人信息加载失败");
    }


}
