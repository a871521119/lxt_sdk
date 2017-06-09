package com.lxt.tv.widget;

import android.view.View;

/**
 * Created by leo on 16/5/7.
 */
public class ZoomOutPageTransformer2 implements CustomViewPager.PageTransformer {

    private CustomViewPager mViewPager;
    private int windowWidth = 0;

    private int itemCount = 4;
    private float itemWidth = 0;
    private float translation;
    private int currentItem = 0;
    public ZoomOutPageTransformer2(CustomViewPager viewPager) {
        mViewPager = viewPager;
        windowWidth = viewPager.getContext().getResources().getDisplayMetrics().widthPixels;
        itemWidth = windowWidth / itemCount;
        translation = itemWidth;
    }

    @Override
    public void transformPage(View view, float position) {
        //获取ViewPager当前显示的页面
        currentItem = mViewPager.getCurrentItem();
        setPositionViewAnimation(view, position);
    }

    /**
     * 获取缩放比例系数
     *
     * @param position    view所在页面
     * @param currentItem Viewpager当前显示的页面
     */
    public static float getScaleCoefficient(int currentItem, int position) {
        //右左边相邻的第1个Item
        if (position == currentItem - 1 || position == currentItem + 1){
            return 0.8f;
        }else if (position == currentItem - 2 || position == currentItem + 2) {
            //右左边相邻的第2个Item
            return 0.65f;
        } else {
            //当前显示的item
            return 0f;
        }
    }

    /**
     * 获取缩放大小
     *    注： 这里的float position，参数对应transformPage方法中的参数，
     *          因为我们要实现的效果是慢慢缩小或者，慢慢放大，
     *          所以缩放的最终大小在滑动的过程中是不固定的，
     *          所以需要根据该参数来计算。
     * @param max
     * @param position
     */
    public static float getScaleSize(float max, float position) {
        return (Math.max(max,1- Math.abs(position))/100)*53;
    }

    /**
     * 对View进行动画效果处理
     */
    private void setPositionViewAnimation(View view, float position) {
        //View所在页面
        int postion = (int) view.getTag();
        //缩放比例
        float scaleFactor = getScaleCoefficient(currentItem, postion);
        //缩放大小
        float scale = getScaleSize(scaleFactor, position);
        view.setAlpha(0.9f + (scale - scaleFactor) / (1 - scaleFactor) * (1 - 0.9f));
        view.setScaleX(scale*1.8f);
        view.setScaleY(scale*1.8f);
    }
}
