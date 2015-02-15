package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class GalleryRow {
    private List<Image> images;

    public GalleryRow() {
        images = new ArrayList<>();
    }

    public int getCount() {
        return images.size();
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void addArray(Image[] images) {
        for (Image img : images) {
            addImage(img);
        }
    }

    public Image getImage(int position) {
        return images.get(position);
    }
}
