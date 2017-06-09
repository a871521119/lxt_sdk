package com.lxt.mobile.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.mobile.base.MBaseActivity;
import com.lxt.mobile.widget.LxtDialog;
import com.lxt.mobile.widget.TitleLayout;
import com.lxt.sdk.util.TimeUtil;
import com.lxt.util.FormatUtil;

import java.util.Date;
import java.util.Locale;

import static com.lxt.mobile.activity.SelfInfoActivity.UPDATA_SEX;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/18 11:59
 * @description :
 */
public class SelfInfoEditActivity extends MBaseActivity {
    /**
     * 更改名字和邮件的情况
     */
    TextView selfInfo_tv;//更改标识

    /**
     * 更改框
     */
    EditText selfInfo_update_edit;
    /**
     * 标题
     */
    TitleLayout titleLayout;

    /**
     * 男女标志
     */
    public boolean isBoy = true;

    /**
     * 男女勾选图标
     */
    ImageView self_boy_check_icon, self_girl_check_icon;
    private String birthdayString = "1990-1-1";
    // 生日数组
    int[] birthy = {1990, 0, 1};

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_selfinfo_edit);
    }

    @Override
    public void initView() {
        titleLayout = (TitleLayout) findViewById(R.id.title);
        titleLayout.setMode(false).setTitle("个人信息").setRightText("保存");

        if (getIntentData() == UPDATA_SEX) {
            self_boy_check_icon = (ImageView) findViewById(R.id.self_boy_check_icon);
            self_girl_check_icon = (ImageView) findViewById(R.id.self_girl_check_icon);
        } else {
            selfInfo_tv = (TextView) findViewById(R.id.selfInfo_tv);
            selfInfo_update_edit = (EditText) findViewById(R.id.selfInfo_update_edit);
        }
        initTitleAndDefaltData();
    }

    @Override
    public void initListener() {
        super.initListener();
        titleLayout.findViewById(R.id.title_right_text).setOnClickListener(this);
        titleLayout.findViewById(R.id.title_left_layout).setOnClickListener(this);
        findViewById(R.id.self_select_boy).setOnClickListener(this);
        findViewById(R.id.self_select_girl).setOnClickListener(this);

    }

    void initTitleAndDefaltData() {
        switch (getIntentData()) {
            case SelfInfoActivity.UPDATA_NAME:
                selfInfo_tv.setText(getString(R.string.selfInfo_englist_name));
                findViewById(R.id.self_other_parent).setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(getIntent().getStringExtra("data"))) {
                    selfInfo_update_edit.setText(getIntent().getStringExtra("data"));
                }
                break;
            case SelfInfoActivity.UPDATA_EMAIL:
                findViewById(R.id.self_other_parent).setVisibility(View.VISIBLE);
                selfInfo_tv.setText(getString(R.string.selfInfo_email));
                if (!TextUtils.isEmpty(getIntent().getStringExtra("data"))) {
                    selfInfo_update_edit.setText(getIntent().getStringExtra("data"));
                }
                break;
            case SelfInfoActivity.UPDATA_BIRTHDAY:
                findViewById(R.id.self_other_parent).setVisibility(View.VISIBLE);
                selfInfo_tv.setText(getString(R.string.selfInfo_birthday));
                selfInfo_update_edit.clearFocus();
                selfInfo_update_edit.setFocusable(false);
                findViewById(R.id.self_birthdayClick).setVisibility(View.VISIBLE);
                findViewById(R.id.self_birthdayClick).setOnClickListener(this);
                if (!TextUtils.isEmpty(getIntent().getStringExtra("data"))) {
                    birthdayString = TimeUtil.getStrTime(getIntent().getStringExtra("data"), TimeUtil.dateFormatYMD);
                }
                String[] arg = birthdayString.split("-");
                birthy[0] = Integer.parseInt(arg[0]);
                birthy[1] = (Integer.parseInt(arg[1]) - 1);
                birthy[2] = Integer.parseInt(arg[2]);
                selfInfo_update_edit.setText(birthdayString);
                break;
            case SelfInfoActivity.UPDATA_SEX:
                findViewById(R.id.self_sex_parent).setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(getIntent().getStringExtra("data"))) {
                    if (getIntent().getStringExtra("data").equals("1")) {
                        self_boy_check_icon.setVisibility(View.VISIBLE);
                        self_girl_check_icon.setVisibility(View.INVISIBLE);
                    } else {
                        self_boy_check_icon.setVisibility(View.INVISIBLE);
                        self_girl_check_icon.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:

                break;
        }

    }


    /**
     * 获取intent数据
     */
    public int getIntentData() {
        return getIntent().getIntExtra("where", 0);
    }

    @Override
    protected void onClickView(View v) {
        super.onClickView(v);
        switch (v.getId()) {
            case R.id.title_left_layout:
                showFinishDialog();
                break;
            case R.id.title_right_text:
                sava();
                break;
            case R.id.self_birthdayClick://显示选择生日的dialog
                showBirthdayDialog(birthy[0], birthy[1], birthy[2]);
                break;
            case R.id.self_select_boy://选择男
                isBoy = true;
                self_boy_check_icon.setVisibility(View.VISIBLE);
                self_girl_check_icon.setVisibility(View.INVISIBLE);
                break;
            case R.id.self_select_girl://选择女
                self_boy_check_icon.setVisibility(View.INVISIBLE);
                self_girl_check_icon.setVisibility(View.VISIBLE);
                isBoy = false;
                break;
            default:

                break;
        }
    }

    public void sava() {
        switch (getIntentData()) {
            case SelfInfoActivity.UPDATA_NAME:
                if (TextUtils.isEmpty(selfInfo_update_edit.getText().toString())) {
                    showToast(getString(R.string.selfInfo_namenotNone));
                    return;
                }
                Intent intent1 = new Intent();
                intent1.putExtra("name", selfInfo_update_edit.getText().toString());
                setResult(SelfInfoActivity.UPDATA_NAME, intent1);
                finish();
                break;
            case SelfInfoActivity.UPDATA_SEX:
                Intent intent4 = new Intent();
                if (isBoy) {
                    intent4.putExtra("sex", 1 + "");
                } else {
                    intent4.putExtra("sex", 2 + "");
                }
                setResult(SelfInfoActivity.UPDATA_SEX, intent4);
                finish();
                break;
            case SelfInfoActivity.UPDATA_EMAIL:
                if (!FormatUtil.isEmail(selfInfo_update_edit.getText().toString())) {
                    showToast(getString(R.string.selfInfo_emailnottrue));
                    return;
                }
                Intent intent2 = new Intent();
                intent2.putExtra("email", selfInfo_update_edit.getText().toString());
                setResult(SelfInfoActivity.UPDATA_EMAIL, intent2);
                finish();
                break;
            case SelfInfoActivity.UPDATA_BIRTHDAY:
                if (TextUtils.isEmpty(selfInfo_update_edit.getText().toString())) {
                    showToast(getString(R.string.selfInfo_birthdaynotTrue));
                    return;
                }
                Intent intent3 = new Intent();
                intent3.putExtra("birthday", selfInfo_update_edit.getText().toString());
                setResult(SelfInfoActivity.UPDATA_BIRTHDAY, intent3);
                finish();
                break;
        }
    }

    /**
     * 生日部分Dialog
     */

    private void showBirthdayDialog(int arg0, int arg1, int arg2) {
        // 这个方法3.0以上支持 3.0以下不支持
        Locale.setDefault(Locale.CHINA);
        DatePickerDialog dateyear = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        if (!birthdayString.equals(String.format("%d-%d-%d", year, month + 1, day))) {
                            selfInfo_update_edit.setText(String.format("%d-%d-%d", year, month + 1, day));
                            birthdayString = String.format("%d-%d-%d", year, month + 1, day);
                        }
                        birthy[0] = year;
                        birthy[1] = month;
                        birthy[2] = day;
                        birthdayString = String.format("%d-%d-%d", year, month + 1, day);
                    }
                }, arg0, arg1, arg2);
        Date date = new Date();
        DatePicker dp = dateyear.getDatePicker();
        dp.setMinDate(TimeUtil.getStringToDate("1900-1-1"));
        dp.setMaxDate(date.getTime());
        dateyear.show();
    }

    @Override
    public void onBackPressed() {
        showFinishDialog();
    }

    /**
     * 显示放弃编辑的提示框
     */
    public void showFinishDialog() {
        new LxtDialog.Builder(mContext).setMessage(getString(R.string.selfInfo_finishEdit))
                .setPositiveButton(getString(R.string.dialog_sureBut), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                        dialog.cancel();
                    }
                }).setNegativeButton(getString(R.string.dialog_cancelBut),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                }).create().show();
    }
}
