package com.example.commonutils;

/**
 * Created by dlj on 2016/9/1.
 */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;


/**
 * Created by litp on 2016/8/6.
 */
public abstract class GlideUtils {


    //图片加载监听器
    public interface ImageLoadListener {

        /**
         * 图片加载成功回调
         *
         * @param uri      图片url 或资源id 或 文件
         * @param view     目标载体，不传则为空
         * @param resource 返回的资源,GlideDrawable或者Bitmap或者GifDrawable,ImageView.setImageRecourse设置
         */
        <T, K> void onLoadingComplete(T uri, ImageView view, K resource);

        /**
         * 图片加载异常返回
         *
         * @param source   图片地址、File、资源id
         * @param errorMsg 异常信息
         */
        <T> void onLoadingError(T source, String errorMsg);

    }




    /**
     * 图片加载监听类
     *
     * @param <T> 图片链接 的类型
     * @param <K> 图片资源返回类型
     * @param <Z> 返回的图片url
     */
    private static class GlideListener<T, K, Z> implements RequestListener<T, K> {

        ImageLoadListener imageLoadListener = null;
        Z url;
        ImageView imageView = null;

        GlideListener(ImageLoadListener imageLoadListener, Z url, ImageView view) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
            this.imageView = view;
        }

        GlideListener(ImageLoadListener imageLoadListener, Z url) {
            this.imageLoadListener = imageLoadListener;
            this.url = url;
        }

        GlideListener(Z url) {
            this.url = url;
        }

        @Override
        public boolean onResourceReady(K resource, T model, Target<K> target, boolean isFromMemoryCache, boolean isFirstResource) {
            if (null != imageLoadListener) {
                if (imageView != null) {
                    imageLoadListener.onLoadingComplete(url, imageView, resource);
                } else {
                    imageLoadListener.onLoadingComplete(url, null, resource);
                }
            }

            return false;
        }

        @Override
        public boolean onException(Exception e, T model, Target<K> target, boolean isFirstResource) {
            if (imageLoadListener != null) {
                imageLoadListener.onLoadingError(url, e.getMessage());
            }
            return false;
        }
    }





    /**
     * 加载存储器上的图片到目标载体
     *
     * @param file      文件File
     * @param imageView 要显示到的图片ImageView
     */
    public static void loadImage(final File file, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(file)
                .listener(new GlideListener<File, GlideDrawable, File>(imageLoadListener, file, imageView))
                .into(imageView);


    }

    /**
     * 加载资源中的图片到目标载体
     *
     * @param resourceId 资源id
     * @param imageView  图片View
     */
    public static void loadImage(final int resourceId, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        Glide.with(imageView.getContext())
                .load(resourceId)
                .listener(new GlideListener<Integer, GlideDrawable, Integer>(imageLoadListener, resourceId, imageView))
                .into(imageView);
    }





    /**
     * 加载网络图片到指定Imageview，支持CircleImageView
     *
     * @param url               图片url
     * @param imageView         要显示的Imageview
     * @param imageLoadListener 图片加载回调
     * @param parms             第一个是error的图片
     */
    public static void loadImage(final String url, final ImageView imageView, final ImageLoadListener imageLoadListener, final int... parms) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);

        if (parms != null && parms.length > 0) {
            type.placeholder(parms[0]);   //占位符
            if (parms.length > 1) {
                type.error(parms[1]);    //图片加载失败显示图片
            }
        }

        //单张CircleImageView不允许动画，不然会不显示，listview适配器有setTag 才设置
        if (imageView instanceof CircleImageView) {
            type.dontAnimate();
        }
        type.listener(new GlideListener<String, GlideDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }






    /**
     * 加载gif图片到指定ImageView
     *
     * @param url               图片Url
     * @param imageView         图片View
     * @param imageLoadListener 图片加载监听器
     */
    public static void loadImageGif(final String url, final ImageView imageView, final ImageLoadListener imageLoadListener) {
        DrawableTypeRequest<String> type = Glide.with(imageView.getContext()).load(url);
        type.asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new GlideListener<String, GifDrawable, String>(imageLoadListener, url, imageView))
                .into(imageView);
    }


    /**
     * 释放内存
     *
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }


    /**
     * 取消所有正在下载或等待下载的任务。
     *
     * @param context 上下文
     */
    public static void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }


    /**
     * 清除所有缓存
     *
     * @param context 上下文
     */
    public static void cleanAll(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}