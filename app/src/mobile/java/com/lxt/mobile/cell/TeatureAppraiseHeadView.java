package com.lxt.mobile.cell;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.been.TeatureAppraiseBeen;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/5/25 10:40
 * @description :
 */
public class TeatureAppraiseHeadView extends LinearLayout implements ListCell {
    TeatureAppraiseBeen teatureAppraiseBeen;
    private TextView tvTotalScore;
    private Button btComment1, btComment2, btComment3, btComment4, btComment5, btComment6, btComment7, btComment8;

    public TeatureAppraiseHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvTotalScore = (TextView) findViewById(R.id.tv_total_score);
        btComment1 = (Button) findViewById(R.id.bt_comment1);
        btComment2 = (Button) findViewById(R.id.bt_comment2);
        btComment3 = (Button) findViewById(R.id.bt_comment3);
        btComment4 = (Button) findViewById(R.id.bt_comment4);
        btComment5 = (Button) findViewById(R.id.bt_comment5);
        btComment6 = (Button) findViewById(R.id.bt_comment6);
        btComment7 = (Button) findViewById(R.id.bt_comment7);
        btComment8 = (Button) findViewById(R.id.bt_comment8);
    }

    @Override
    public void setData(Object data, int position, final RecyclerView.Adapter mAdapter) {
        if (data == null) return;
        teatureAppraiseBeen = (TeatureAppraiseBeen) data;
        tvTotalScore.setText(teatureAppraiseBeen.getPingfen());

        //设置标签数量
        String com1 = teatureAppraiseBeen.getTags().get_$1();
        btComment1.setText("发音标准" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$2();
        btComment2.setText("颜值高" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$3();
        btComment3.setText("有耐心" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$4();
        btComment4.setText("中文流利" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$5();
        btComment5.setText("有趣" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$6();
        btComment6.setText("热情" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$7();
        btComment7.setText("准备充分" + "(" + (null == com1 ? 0 : com1) + ")");
        com1 = teatureAppraiseBeen.getTags().get_$8();
        btComment8.setText("声音好听" + "(" + (null == com1 ? 0 : com1) + ")");
    }
}
