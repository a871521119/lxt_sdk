package com.lxt.mobile.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lxt.R;


/**
 * Created by yangfu on 2016/6/28.
 */
public class TackPhotoPopwindow extends PopupWindow implements View.OnClickListener {
    Context mContext;
    View view;
    OnPopItemClickListener mOnPopItemClickListener;

    public TackPhotoPopwindow(Context context) {
        this.mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.tackphotopopwindow, null);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        // 设置SelectPicPopupWindow的View
        this.setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        setBackgroundDrawable(new BitmapDrawable());
        view.findViewById(R.id.pop_other).setOnClickListener(this);
        view.findViewById(R.id.pop_takePhone).setOnClickListener(this);
        view.findViewById(R.id.pop_selectFromSD).setOnClickListener(this);
    }

    public void showWindow() {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_takePhone:
                if (mOnPopItemClickListener != null) {
                    mOnPopItemClickListener.onTakePhotoClick();
                }
                break;
            case R.id.pop_selectFromSD:
                if (mOnPopItemClickListener != null) {
                    mOnPopItemClickListener.onTakePhotoFromSdClick();
                }
                break;
            case R.id.btn_cancel:
            case R.id.pop_other:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void setOnPopItemClickListener(OnPopItemClickListener onPopItemClickListener) {
        this.mOnPopItemClickListener = onPopItemClickListener;
    }


    public interface OnPopItemClickListener {
        void onTakePhotoClick();//拍照

        void onTakePhotoFromSdClick();//从相册
    }


}
