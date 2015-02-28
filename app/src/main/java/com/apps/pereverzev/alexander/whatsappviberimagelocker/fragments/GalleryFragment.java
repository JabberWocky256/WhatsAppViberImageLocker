package com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.GalleryAdapter;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryGridCreator;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.test.TestData;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.MyImageLoader;

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

        List<GalleryRow> galleryRows = (new GalleryGridCreator(TestData.getImagesPaths(), 3)).getGrid(getActivity());
        adapter = new GalleryAdapter(galleryRows, getActivity());

        GridView galleryVerticalView = (GridView) view.findViewById(R.id.galleryView);
        galleryVerticalView.setOnScrollListener((MyImageLoader.getInstance(getActivity())).getOnPauseListener());
        galleryVerticalView.setAdapter(adapter);

        return view;
    }

}
