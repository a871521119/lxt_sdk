package com.lxt.tv.listener; /**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : bryan
 * @create-time : 2017/2/17 16:38
 * @description :
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 *parameter
 **/
public class HeadSetListen {
    private Context mContext;//谁监听
    private IntentFilter mHeadSetIntentFilter = null;//监听谁
    private OnHeadSetLitener mOnHeadSetListener = null;//回调
    private HeadSetReceiver mHeadSetReceiver = null;
    private static final String TAG = "HeadsetPlugReceiver";

    public HeadSetListen(Context context, OnHeadSetLitener tOnHeadSetListener){
        mContext = context;
        mHeadSetIntentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        mHeadSetReceiver = new HeadSetReceiver();
        mOnHeadSetListener = tOnHeadSetListener;
    }
    public void start(){
        mContext.registerReceiver(mHeadSetReceiver,mHeadSetIntentFilter);
    }
    public void stop(){
        mContext.unregisterReceiver(mHeadSetReceiver);
    }

    public void receive(Context context, Intent intent) {
        if (intent.hasExtra("state")){
            if (intent.getIntExtra("state", 0) == 0){
                //外放
                mOnHeadSetListener.onHomeSetFalse();
            }
            else if (intent.getIntExtra("state", 0) == 1){
                //耳机
                mOnHeadSetListener.onHeadSetOk();
            }
        }
    }

    class HeadSetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            receive( context, intent );
        }
    }

    public interface OnHeadSetLitener{
        public void onHeadSetOk();
        public void onHomeSetFalse();
    }
}
