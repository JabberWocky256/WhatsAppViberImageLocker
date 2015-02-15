package com.apps.pereverzev.alexander.whatsappviberimagelocker.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.FullScreenImageAdapter;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.MyImageLoader;

/**
 * Created by Александр on 02.11.2014.
 */
public class FullScreenViewActivity extends Activity {
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        viewPager = (ViewPager) findViewById(R.id.pager);

        int position = getPosition();
        adapter = new FullScreenImageAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    private int getPosition() {
        Intent i = getIntent();
        return i.getIntExtra("position", 0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}