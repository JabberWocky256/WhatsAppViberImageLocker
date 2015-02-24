package com.apps.pereverzev.alexander.whatsappviberimagelocker.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.activities.components.ZoomOutPageTransformer;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.GalleryAdapter;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments.ScreenImageSlidePageFragment;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments.components.ImagesStack;

import java.lang.ref.WeakReference;

/**
 * Created by Alexander on 16.02.2015.
 */
public class ScreenImageSlidePagerActivity extends FragmentActivity {
    public static final String POSITION = "position";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        int currentItem = getIntent().getIntExtra(POSITION, 0);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(currentItem);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        ImagesStack stack = ScreenImageSlidePageFragment.stack;
        if(stack != null)
        {
           if(stack.isImageZoom()){
               stack.resetImageZoom();
           } else {
               ScreenImageSlidePageFragment.stack = null;
               super.onBackPressed();
           }
        } else {
            super.onBackPressed();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenImageSlidePageFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return GalleryAdapter.imagesInGallery.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}
