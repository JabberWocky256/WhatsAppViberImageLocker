package com.apps.pereverzev.alexander.whatsappviberimagelocker.test;

import android.content.Context;
import android.os.Environment;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class TestData {
    private static final String[] IMAGE_PATH = {"img.jpg", "imgBig.jpeg", "photo-camera_w22.jpg", "1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg", "9.jpg", "10.jpg", "11.jpg", "12.jpg", "13.jpg", "14.jpg", "15.jpg", "16.jpg", "17.jpg", "18.jpg"};

    public static List<GalleryRow> getGalleryRows(Context context) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Download/";

        List<GalleryRow> galleryRows = new ArrayList<>();
        GalleryRow row = new GalleryRow();

        for (int i = 0; i < 3; i++) {
            row.addImage(new Image(path + IMAGE_PATH[i]));
        }
        galleryRows.add(row);

        return galleryRows;
    }

    public static List<String> getImagesPaths() {
        List<String> paths = new ArrayList<>();
        for (String path : IMAGE_PATH) {
            String p = Environment.getExternalStorageDirectory() + File.separator + "Download/" + path;
            paths.add(p);
        }

        //add not exited path
        paths.add(Environment.getExternalStorageDirectory() + File.separator + "Download/" + "not_exist_file");

        return paths;
    }
}
