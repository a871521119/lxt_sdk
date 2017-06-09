package com.lxt.tv.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.lxt.R;
import com.lxt.sdk.cache.SharedPreference;
import com.lxt.sdk.http.LxtHttp;
import com.lxt.sdk.http.LxtParameters;
import com.lxt.base.BaseBeen;
import com.lxt.tv.base.MBaseActivity;

import java.util.Map;



/**
 * 课程评价页面
 */
public class ClassAppraiseDialogActivity extends MBaseActivity implements
        CheckBox.OnCheckedChangeListener,View.OnFocusChangeListener{
    //星级评价
    private LinearLayout starLayout;
    //获得焦点的星星
    private View focusStarView;
    //第一行评价标签
    private LinearLayout evaluateTagLayout;
    //第二行评价标签
    private LinearLayout evaluateTagLayoutSecond;

    private Button cancle_btn = null, confirm_btn = null;
    //参数
    private String lession_guid, teacher_guid;
    // 星星的评分数量
    private int starCheckedPosition = 5;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.course_record_dialog);
        Intent it = getIntent();
        lession_guid = it.getStringExtra(LxtParameters.Key.LESSON_GUID);
        teacher_guid = it.getStringExtra(LxtParameters.Key.TEACHER_GUID);
    }

    @Override
    public void initView() {
        starLayout = (LinearLayout) findViewById(R.id.starLayout);
        for(int i = 0;i<starLayout.getChildCount();i++){
            CheckBox checkBox = (CheckBox) starLayout.getChildAt(i);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setOnFocusChangeListener(this);
        }
        evaluateTagLayout = (LinearLayout) findViewById(R.id.evaluateTagLayout);
        evaluateTagLayoutSecond = (LinearLayout) findViewById(R.id.evaluateTagLayoutSecond);
        //取消  确定
        cancle_btn = (Button) findViewById(R.id.course_record_dialog_cancle);
        confirm_btn = (Button) findViewById(R.id.course_record_dialog_confirm);
    }

    @Override
    public void initData() {
        super.initListener();
        cancle_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
    }

    /**
     * 获取评价标签
     * @param row 行数
     * @param group 容器
     * @return
     */
    private String getLabelTag(int row, ViewGroup group){
        String tags = "";
        int col = 4;//列数
        int n = (row -1) * col;//行数之间的倍数
        for (int index = 0;index < group.getChildCount();index++){
            if(((CheckBox)group.getChildAt(index)).isChecked()){
                int position =  (index % col) +1 +n;
                tags+= position + "-";
            }
        }
        return tags.trim();
    }

    /**
     * 评价标签数组
     * @return
     */
    private String[] getLabelTagArray(){
        String tags = getLabelTag(1,evaluateTagLayout)+getLabelTag(2,evaluateTagLayoutSecond);
        if(!TextUtils.isEmpty(tags)){
            return tags.split("-");
        }
        return new String[1];
    }


    @Override
    public void onClickView(View view) {
        switch (view.getId()) {

            case R.id.course_record_dialog_cancle:
                finish();
                break;
            case R.id.course_record_dialog_confirm:
                evaluate_getLink();
                break;
        }
    }


    //评价接口
    private void evaluate_getLink() {
        if (!SharedPreference.getData(LxtParameters.Key.GUID).equals("")) {
            LxtHttp.getInstance().setCallBackListener(this);
            LxtHttp.getInstance().lxt_teacherComment(SharedPreference.getData(LxtParameters.Key.GUID),
                    SharedPreference.getData(LxtParameters.Key.SCHOOL_GUID),
                    lession_guid,teacher_guid,String.valueOf(starCheckedPosition),"",getLabelTagArray(),
                    SharedPreference.getData(LxtParameters.Key.TOKEN));
        } else {
            showToast(getString(R.string.outOfData));
            finish();
        }
    }

    @Override
    protected void bindViewData(BaseBeen data) {
        super.bindViewData(data);
        showToast(getString(R.string.appraise_succeed));
        SharedPreference.setData("star",String.valueOf(starCheckedPosition));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onFailed(String action, String code) {
        super.onFailed(action, code);
        if (code.equals("SN400")) {
            showToast(getString(R.string.appraise_Faild));
            finish();
        }
    }

    @Override
    public void onErrored(String action, Map<String, Object> params, String errormsg) {
        super.onErrored(action, params, errormsg);
        showToast(getString(R.string.netDisconnect));
        finish();
    }

    //    @Override
//    public void onSuccessCallback(String str, String action) {
//        super.onSuccessCallback(str, action);
//        showToast(getString(R.string.appraise_succeed));
//        SharedPreference.setData(String.valueOf(starCheckedPosition),"star",this);
//        setResult(RESULT_OK);
//        finish();
//    }
//
//
//    @Override
//    public void onFailureCallback(String error, String action, String resultData) {
//        super.onFailureCallback(error, action, resultData);
//        if (error.equals("SN400")) {
//            showToast(getString(R.string.appraise_Faild));
//            finish();
//        }
//    }

//    @Override
//    public void onErroCallback(String action, String result) {
//        super.onErroCallback(action, result);
//        showToast(getString(R.string.netDisconnect));
//        finish();
//    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == focusStarView){
            int focusStarPosition = starFocusPosition();
            starCheckedPosition = focusStarPosition + 1;
            for (int i= 0;i < starLayout.getChildCount();i++){
                CheckBox box =(CheckBox)starLayout.getChildAt(i);
                if(i <= focusStarPosition){
                    box.setChecked(true);
                }else {
                    box.setChecked(false);
                }
            }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            focusStarView = v;
        }
    }
    /**
     * 获得焦点星星的位置
     * @return
     */
    private int starFocusPosition(){
        for (int i = 0;i < starLayout.getChildCount();i++){
            if (starLayout.getChildAt(i).hasFocus()){
                return i;
            }
        }
        return -1;
    }

}
