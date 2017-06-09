package com.lxt.tv.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

import com.lxt.R;
import com.lxt.util.DisplayUtil;


/**
 * Created by Administrator on 2017/4/20.
 * 首页底部加横线的按钮
 */

public class ButtonTopLine extends Button {

    public ButtonTopLine(Context context) {
        super(context);
    }

    public ButtonTopLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getResources().getDimensionPixelSize(R.dimen.x60);//左右边距
        int y  = getResources().getDimensionPixelSize(R.dimen.y10);//上边距
        int stratX = (DisplayUtil.getScreenWidth(getContext())-canvas.getWidth())/2 -x;
        Paint paint = new Paint();
        paint.setColor(Color.rgb(99,99,99));
        paint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.x2));
        paint.setAlpha(100);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(-stratX, -y, canvas.getWidth()+stratX, -y,paint);
    }
}
