package com.lxt.mobile.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.ClassRecordBeen;
import com.lxt.sdk.util.LogUtil;
import com.lxt.util.DisplayUtil;

import java.util.List;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED;
import static android.view.WindowManager.LayoutParams.TITLE_CHANGED;
import static com.lxt.R.id.v;

/**
 * Created by LiWenJiang on 2017/5/18.
 */

public class AppraiseWindow extends PopupWindow implements View.OnClickListener{

    private Context context;

    private EvaluateListener evaluateListener;

    private View appraiseLayout,//评价标签
            appraiseContentLayout;//评价内容

    //第一行评价标签
    private LinearLayout evaluateTagLayout;
    //第二行评价标签
    private LinearLayout evaluateTagLayoutSecond;

    private LinearLayout starLayout;//星星
    private int evaluateStarLevel = 5;//评价的星星个数

    private EditText evaluate_content;

    public AppraiseWindow(Context context,EvaluateListener evaluateListener){
        this.context = context;
        this.evaluateListener = evaluateListener;
        initView();
        setDefault();
    }

    private void initView() {
        View  view = LayoutInflater.from(context).inflate(R.layout.course_record_appraise,null);
        view.findViewById(R.id.evaluate_finish_image).setOnClickListener(this);
        view.findViewById(R.id.next_text).setOnClickListener(this);
        starLayout = (LinearLayout) view.findViewById(R.id.starLayout);
        for (int i =0;i<starLayout.getChildCount();i++){
            starLayout.getChildAt(i).setOnClickListener(starListener);
        }
        appraiseLayout = view.findViewById(R.id.appraiseLayout);
        evaluateTagLayout = (LinearLayout) view.findViewById(R.id.evaluateTagLayout);
        evaluateTagLayoutSecond = (LinearLayout) view.findViewById(R.id.evaluateTagLayoutSecond);
        appraiseContentLayout = view.findViewById(R.id.appraiseContentLayout);
        evaluate_content = (EditText) view.findViewById(R.id.evaluate_content);
        setContentView(view);
    }


    /**
     * 星星点击监听
     */
    View.OnClickListener starListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            evaluateStarLevel = getCheckedPosition(v);
            for (int i = 0;i < starLayout.getChildCount();i++){
                CheckBox box = (CheckBox) starLayout.getChildAt(i);
                if (i <= evaluateStarLevel){
                    box.setChecked(true);
                }else {
                    box.setChecked(false);
                }
            }
        }
    };

    /**默认设置*/
    private void setDefault() {
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(DisplayUtil.getScreenHeight(context));//-context.getResources().getDimensionPixelSize(R.dimen.y140)
        setOutsideTouchable(true);
        setFocusable(true);
        setInputMethodMode(INPUT_METHOD_NEEDED);
        setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
    }

    /**显示*/
    public void showAtLocation(View view){
        showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0,0);
    }

    /**关闭*/
    public void dismissWindow(){
        dismiss();
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
    public String[] getLabelTagArray(){
        String tags = getLabelTag(1,evaluateTagLayout)+getLabelTag(2,evaluateTagLayoutSecond);
        if(!TextUtils.isEmpty(tags)){
            return tags.split("-");
        }
        return new String[1];
    }

    /**
     * 评价内容
     * @return
     */
    public String getEvaluateContent(){
        return evaluate_content.getText().toString();
    }
    /**星星等级*/
    public String getEvaluateStarLevel() {
        if (evaluateStarLevel < 5)
            evaluateStarLevel+=1;
        return String.valueOf(evaluateStarLevel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.evaluate_finish_image:
                dismissWindow();
                break;
            case R.id.next_text:
                if(appraiseLayout.getVisibility() == View.VISIBLE){
                    appraiseLayout.setVisibility(View.GONE);
                    appraiseContentLayout.setVisibility(View.VISIBLE);
                    ((TextView)v).setText(context.getResources().getString(R.string.appraise_finish));
                }else{
                    if (evaluateListener != null){
                        evaluateListener.onEvaluate();
                    }
                }
                break;
        }
    }

    /**
     * 选中的位置
     * @param view
     * @return
     */
    private int getCheckedPosition(View view){
        for (int i = 0;i < starLayout.getChildCount();i++){
            if(starLayout.getChildAt(i) == view){
                return i;
            }
        }
        return evaluateStarLevel;
    }

    public interface EvaluateListener{
        void onEvaluate();
    }

}
