package com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.TestData;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.GalleryAdapter;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;

import java.util.List;
import java.util.zip.Inflater;

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

        List<GalleryRow> galleryRows = TestData.getGalleryRows(getActivity());
        adapter = new GalleryAdapter(galleryRows, getActivity());
        ListView list = (ListView)view.findViewById(R.id.galleryListView);
        list.setAdapter(adapter);

        return view;
    }

}
