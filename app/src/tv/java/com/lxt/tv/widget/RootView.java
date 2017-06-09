package com.lxt.tv.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/4/25.
 */

public class RootView extends FrameLayout {

    /*加载页面状态*/
    private State state;
    /*页面加载监听*/
    private LoadViewListener loadViewListener;
    /*成功页面*/
    private View sucessView;
    /*失败页面*/
    private View failView;

    private int sucessViewId,failViewId;

    private View inflaterView(int id){
      return   LayoutInflater.from(getContext()).inflate(id, null);
    }


    public enum  State{
        LOADING,SUCCESS,FAIL
    }

    public RootView(@NonNull Context context) {
        super(context);
        init();
    }

    public RootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public RootView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    /**
     * 初始化
     */
    private void init(){
        state = State.LOADING;
    }

    /**
     * 添加显示的View
     * @param onState 添加页面的状态
     */
    private void rootAddView(State onState){
        if(this.state == State.LOADING && onState == State.SUCCESS){
            if (sucessView != null){
                removeAllViews();
                addView(sucessView);
                this.state = onState;
            }
            if (loadViewListener != null){
                loadViewListener.onSucessView();
            }
        }else if (onState == State.FAIL){
            if (failView !=null){
                removeAllViews();
                addView(failView);
            }
            state = State.LOADING;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        rootAddView(state);
    }

    public LoadViewListener getLoadViewListener() {
        return loadViewListener;
    }

    public void setLoadViewListener(LoadViewListener loadViewListener) {
        this.loadViewListener = loadViewListener;
    }

    public void setSucessView(View sucessView) {
        this.sucessView = sucessView;
    }

    public void setFailView(View failView) {
        this.failView = failView;

    }

    public void setSucessViewId(int sucessViewId) {
        this.sucessViewId = sucessViewId;
        setSucessView(inflaterView(sucessViewId));
    }

    public void setFailViewId(int failViewId) {
        this.failViewId = failViewId;
        setFailView(inflaterView(failViewId));
    }

    public interface LoadViewListener{
        /**页面加载成功*/
      public   void onSucessView();

    }
}
