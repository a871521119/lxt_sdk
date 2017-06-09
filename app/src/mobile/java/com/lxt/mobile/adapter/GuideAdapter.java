package com.lxt.mobile.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @copyright : 北京乐学通教育科技有限公司 2016
 * @creator : 杨福
 * @create-time : 2016/9/2 13:48
 * @description : 引导页面适配器
 */
public class GuideAdapter extends PagerAdapter {

    private Context context;
    private List<ImageView> mImages;
    private int[] mImgIds;

    public GuideAdapter(Context context, List<ImageView> mImages, int[] mImgIds) {
        this.context = context;
        this.mImages = mImages;
        this.mImgIds = mImgIds;
    }

    @Override
    public int getCount() {
        return mImgIds.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImages.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageview = new ImageView(context);
        imageview.setImageResource(mImgIds[position]);
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageview);
        mImages.add(imageview);
        return imageview;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
