package com.lxt.tv.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;


/**
 * Created by liwenjiang on 2016/5/24.
 */
public class NavigationBar extends RadioButton {

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public NavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable[] drawables = getCompoundDrawables();

        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            if (drawableLeft != null) {
                //获取文字的宽度
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawableLeft.getIntrinsicWidth();
                float bodyWidth = textWidth + drawableWidth + drawablePadding;
                canvas.translate((getWidth() - bodyWidth) / 2, 0);//(getWidth() - bodyWidth) / 2
            }

        }
        super.onDraw(canvas);

    }


}
