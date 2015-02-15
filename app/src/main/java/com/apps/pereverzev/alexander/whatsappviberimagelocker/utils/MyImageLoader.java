package com.apps.pereverzev.alexander.whatsappviberimagelocker.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.DisplaySize;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Alexander on 15.02.2015.
 */
public class MyImageLoader {
    private static MyImageLoader instance;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration config;
    private Activity context;


    private MyImageLoader(Activity context) {
        this.imageLoader = ImageLoader.getInstance();
        this.context = context;

        File cacheDir = StorageUtils.getOwnCacheDirectory(context, context.getApplicationInfo().dataDir);
        this.config = getConfiguration(context, cacheDir);
        imageLoader.init(config);
    }

    public static MyImageLoader getInstance(Activity context) {
        if (instance == null) {
            instance = new MyImageLoader(context);
        }
        return instance;
    }

    public PauseOnScrollListener getOnPauseListener() {
        boolean pauseOnScroll = false; // or true
        boolean pauseOnFling = true; // or false
        PauseOnScrollListener listener = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);

        return listener;
    }

    public void setImage(ImageView image, int width, int height, String path, ProgressBar progress) {
        setImgConfiguration(image, width, height, path, progress);
    }

    private void setImgConfiguration(final ImageView image, int width, int height, String path, final ProgressBar ringProgressDialog) {
        path = "file://" + path;
        DisplayImageOptions options = getDisplayOptions(height, width);

        imageLoader.displayImage(path, image, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (ringProgressDialog != null) {
                    ringProgressDialog.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (ringProgressDialog != null) {
                    ringProgressDialog.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (ringProgressDialog != null) {
                    ringProgressDialog.setVisibility(View.GONE);
                }
            }
        });

        Log.v("ImageLogs", path.toString());
    }

    private ImageLoaderConfiguration getConfiguration(Context context, File cacheDir) {
        DisplaySize displaySize = new DisplaySize();
        DisplaySize.Size size = displaySize.getLargeElementSize(context);
        int mWidth = (int)Math.ceil(size.width);
        int mHeight = (int)Math.ceil(size.height);

        return new ImageLoaderConfiguration.Builder(context)
                // You can pass your own memory cache implementation
                .threadPoolSize(5)
                .diskCache(new UnlimitedDiscCache(cacheDir)) // You can pass your own disc cache implementation
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .diskCacheExtraOptions(mWidth, mHeight, null)
                .memoryCacheExtraOptions(mWidth, mHeight)
                .build();
    }

    private DisplayImageOptions getDisplayOptions(final int height, final int width) {
        DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder()
                // .showImageOnFail(imagePreview)
                // .showImageForEmptyUri(imagePreview)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
               /* .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap src) {
                        if(width<0 || height<0)
                            return src;

                        int xOffset = (src.getWidth() - width) / 2;
                        int yOffset = (src.getHeight() - height) / 2;
                        Bitmap result = Bitmap.createBitmap(src, xOffset, yOffset, width, height);
                        src.recycle();

                        return result;
                    }
                })*/
                .imageScaleType(ImageScaleType.EXACTLY);

/*        if (width > 0 && height > 0) {
            optionsBuilder.postProcessor(new BitmapProcessor() {
                @Override
                public Bitmap process(Bitmap bmp) {
                    return Bitmap.createScaledBitmap(bmp, width, height, false);
                }
            });
        }*/

        return optionsBuilder.build();
    }
}
