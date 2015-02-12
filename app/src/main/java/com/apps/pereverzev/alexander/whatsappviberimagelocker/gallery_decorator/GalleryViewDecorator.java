package com.apps.pereverzev.alexander.whatsappviberimagelocker.gallery_decorator;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by opereverzyev on 12.02.15.
 */
public abstract class GalleryViewDecorator{
    protected ViewGroup component;

    public GalleryViewDecorator(ViewGroup component) {
        this.component = component;
    }

    public abstract void setAdapter(BaseAdapter adapter);
}
