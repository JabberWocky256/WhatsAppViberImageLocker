package com.apps.pereverzev.alexander.whatsappviberimagelocker.gallery_decorator;

import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by opereverzyev on 12.02.15.
 */
public class GalleryLinearLayout extends GalleryViewDecorator {

    public GalleryLinearLayout(LinearLayout component) {
        super(component);
        component.setGravity(Gravity.TOP);
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, null);
            component.addView(item);
        }
    }
}
