package com.lxt.mobile.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxt.R;
import com.lxt.util.DisplayUtil;


/**
 * author 高明宇
 * date 2016/8/9 19:05
 * function 自定义弹出框
 */
public class LxtDialog extends Dialog {

    public LxtDialog(Context context) {
        super(context);
    }

    public LxtDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public LxtDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final LxtDialog dialog = new LxtDialog(context, R.style.dialog);
            View layout = inflater.inflate(R.layout.lxtdialoglayout, null, false);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, 200));
            // set the dialog title
            if(TextUtils.isEmpty(title)){
                ((TextView) layout.findViewById(R.id.toast_show_text)).setVisibility(View.GONE);
            }else {
                ((TextView) layout.findViewById(R.id.toast_show_text)).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.toast_show_text)).setText(title);
            }
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.speedclass_cancel_button))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    layout.findViewById(R.id.speedclass_cancel_button)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.speedclass_cancel_button).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.speedclass_abandon_button))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    layout.findViewById(R.id.speedclass_abandon_button)
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.speedclass_abandon_button).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.toast_show_text2)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT,200));
            }
            dialog.setContentView(layout);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
//		window.setLayout(deviceWidth, 100);
//		window.setGravity(Gravity.CENTER);
            android.view.WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = 6*DisplayUtil.getScreenWidth(context)/7;
            layoutParams.height = DisplayUtil.getScreenHeight(context)/3;
            window.setAttributes(layoutParams);
            return dialog;
        }
    }
}