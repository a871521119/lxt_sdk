package com.lxt.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.lxt.R;

/**
 * @copyright : 北京乐学通教育科技有限公司 2017
 * @creator : 杨福
 * @create-time : 2017/3/2 15:24
 * @description :
 */
public class ImageLoaderUtil {

    public static ImageLoaderUtil mImageLoaderUtil = null;

    /**
     * 单例
     *
     * @return 类对象
     */
    public static ImageLoaderUtil getInstence() {
        synchronized (ImageLoaderUtil.class) {
            if (mImageLoaderUtil == null) {
                mImageLoaderUtil = new ImageLoaderUtil();
            }
            return mImageLoaderUtil;
        }
    }

    /**
     * 加载正常图片
     *
     * @param uri
     * @param imageView
     */
    public void loadImage(Context context, String uri, ImageView imageView, int... parms) {
        GlideUtils.loadImage(context, uri, imageView, new GlideImageLoadListener(),parms);
    }

    /**
     * 加载圆形图片
     *
     * @param uri
     * @param imageView
     */
    public void loadRoundImage(Context context, String uri, ImageView imageView) {
        GlideUtils.loadCircleImage(uri, imageView, new GlideImageLoadListener(), R.drawable.defalt_circle_img);
    }

    /**
     * 显示圆角图片
     *
     * @param imageView 图像控件
     * @param uri   图片地址
     * @param radius    圆角半径，
     */
    public  void loadCornerImage(Context context, String uri, ImageView imageView, int radius) {
        GlideUtils.loadConerImage(uri, imageView,radius, new GlideImageLoadListener());
    }
    /**
     * 清除缓存
     */
    public static void clearCache(Context context){
        GlideUtils.cleanAll(context);
    }

    /**
     * 统一处理的图片加载回调
     */
    class GlideImageLoadListener implements GlideUtils.ImageLoadListener<String, GlideDrawable> {

        @Override
        public void onLoadingComplete(String uri, ImageView view, GlideDrawable resource) {

        }

        @Override
        public void onLoadingError(String source, Exception e) {

        }
    }

}
