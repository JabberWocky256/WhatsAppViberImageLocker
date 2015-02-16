package com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.GalleryAdapter;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.Image;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.MyImageLoader;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.TouchImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Alexander on 16.02.2015.
 */
public class ScreenImageSlidePageFragment extends Fragment {
    private static WeakReference<Bitmap> mBitmapReference;
    private static final String ARGUMENT_PAGE_NUMBER = "PAGE_NUMBER";
    private int pageNumber;
    public static TouchImageView touchImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER, -1);

        if(savedInstanceState != null) {
            pageNumber = savedInstanceState.getInt(ARGUMENT_PAGE_NUMBER, pageNumber);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.layout_fullscreen_image, container, false);
        touchImage = (TouchImageView)rootView.findViewById(R.id.imgDisplay);

        Image img = (GalleryAdapter.imagesInGallery.get(pageNumber));
        MyImageLoader imageLoader = MyImageLoader.getInstance(getActivity());
        imageLoader.setImage(touchImage, img.getImagePath(), null);

/*        Bitmap bitmapImage = ((new BitmapLoader()).getImage(img.getImagePath(), (int)Math.ceil(img.getFullSize().width),
                (int) Math.ceil(img.getFullSize().height)));

        mBitmapReference = new WeakReference<Bitmap>(bitmapImage);

        touchImage.setImageBitmap(mBitmapReference.get());*/

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARGUMENT_PAGE_NUMBER, pageNumber);
    }

    public static ScreenImageSlidePageFragment getInstance(int page){
        ScreenImageSlidePageFragment pageFragment = new ScreenImageSlidePageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
}
