package com.apps.pereverzev.alexander.whatsappviberimagelocker.oldproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;

/**
 * Created by Александр on 02.11.2014.
 */
public class FullScreenViewActivity extends Activity {
    private Utils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = (ViewPager) findViewById(R.id.pager);

        utils = new Utils();

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this);


      /*  try {
            viewPager.setAdapter(new TouchImageAdapter(this, utils.getFilePaths()));
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        } catch (DirectoryPassIsNotValid directoryPassIsNotValid) {
            directoryPassIsNotValid.printStackTrace();
        }*/

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }


    @Override
    public void onBackPressed() {
        GridViewActivity.onRestart = false;
        super.onBackPressed();
        finish();
    }
}
