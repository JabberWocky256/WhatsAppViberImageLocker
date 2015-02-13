package com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.GalleryAdapter;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryGridCreator;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.gallery_decorator.GalleryLinearLayout;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.test.TestData;

import java.util.List;

/**
 * Created by opereverzyev on 10.02.15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GalleryFragment extends Fragment {
    private GalleryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gallery_view, container, false);

        List<GalleryRow> galleryRows = (new GalleryGridCreator(TestData.getImagesPaths())).getGrid(getActivity());
        adapter = new GalleryAdapter(galleryRows, getActivity());

        LinearLayout galleryVerticalView = (LinearLayout)view.findViewById(R.id.galleryView);
        GalleryLinearLayout galleryView = new GalleryLinearLayout(galleryVerticalView);
        galleryView.setAdapter(adapter);

        return view;
    }

}
