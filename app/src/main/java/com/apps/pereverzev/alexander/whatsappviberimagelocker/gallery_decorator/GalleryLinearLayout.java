package com.apps.pereverzev.alexander.whatsappviberimagelocker.gallery_decorator;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by opereverzyev on 12.02.15.
 */
public class GalleryLinearLayout extends GalleryViewDecorator {

    public GalleryLinearLayout(ViewGroup component) {
        super(component);
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        for (int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, null);
            component.addView(item);
        }
    }
}
