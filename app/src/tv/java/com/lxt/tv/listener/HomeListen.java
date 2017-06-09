package com.lxt.tv.listener; /**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/2/17 15:28
 * @description :
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 *parameter
 **/
public class HomeListen {
    private Context mContext;
    private IntentFilter mHomeBtnIntentFilter = null;
    private OnHomeBtnPressLitener mOnHomeBtnPressListener = null;
    private HomeBtnReceiver mHomeBtnReceiver = null;

    public HomeListen(Context context, OnHomeBtnPressLitener onHomeBtnPressListener){
        mContext = context;
        mHomeBtnReceiver = new HomeBtnReceiver();
        mHomeBtnIntentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mOnHomeBtnPressListener = onHomeBtnPressListener;
    }

    public void setOnHomeBtnPressListener(OnHomeBtnPressLitener onHomeBtnPressListener ){
        mOnHomeBtnPressListener = onHomeBtnPressListener;
    }

    public void start( ){
        mContext.registerReceiver(mHomeBtnReceiver, mHomeBtnIntentFilter);
    }

    public void stop( ){
        mContext.unregisterReceiver(mHomeBtnReceiver );
    }

    private void receive(Context context, Intent intent){
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra( "reason" );
            if (reason != null) {
                if( null != mOnHomeBtnPressListener ){
                    if( reason.equals( "homekey" ) ){
                        // 按Home按键
                        mOnHomeBtnPressListener.onHomeBtnPress( );
                    }else if( reason.equals( "recentapps" ) ){
                        // 长按Home按键
                        mOnHomeBtnPressListener.onHomeBtnLongPress( );
                    }
                }
            }
        }
    }

    class HomeBtnReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            receive( context, intent );
        }
    }

    public interface OnHomeBtnPressLitener{
        public void onHomeBtnPress();
        public void onHomeBtnLongPress();
    }
}

