package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.DisplaySize;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.Image;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.MyImageLoader;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.TouchImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;


/**
 * Created by Александр on 02.11.2014.
 */
public class FullScreenImageAdapter extends PagerAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private int viewMode = 0;
    private boolean enabled = true;
    // constructor
    public FullScreenImageAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return GalleryAdapter.imagesInGallery.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imgDisplay;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);
        imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        Image image = GalleryAdapter.imagesInGallery.get(position);
        DisplaySize.Size size = image.getFullSize();

        MyImageLoader loader = MyImageLoader.getInstance(activity);
        loader.setImage(imgDisplay, (int)Math.ceil(size.width), (int)Math.ceil(size.height), image.getImagePath(), null);
                ((ViewPager) container).addView(viewLayout);

        viewMode = 1;

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }



    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
