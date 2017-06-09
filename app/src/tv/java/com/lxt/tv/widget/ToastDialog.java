package com.lxt.tv.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.lxt.R;


/**
 * author 高明宇
 * date 2016/8/9 19:05
 * function 自定义弹出框
 */
public class ToastDialog extends Dialog {

    public ToastDialog(Context context) {
        super(context);
    }

    public ToastDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public ToastDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ToastDialog dialog = new ToastDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.toast_dialog, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            dialog.setContentView(layout);
            return dialog;
        }
    }
}