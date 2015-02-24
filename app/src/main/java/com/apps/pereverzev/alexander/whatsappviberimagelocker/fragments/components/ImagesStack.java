package com.apps.pereverzev.alexander.whatsappviberimagelocker.fragments.components;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.TouchImageView;

/**
 * Created by Alexander on 17.02.2015.
 */
public class ImagesStack {
    private TouchImageView[] images;
    private int currentPosition = 0;

    public ImagesStack() {
        images = new TouchImageView[3];
    }

    public void add(int position, TouchImageView imageView){
        resetImageZoom();
        if(images[1] == null){
            images[1] = imageView;
        } else {

            if (position > currentPosition) {
                setRight(imageView);
            } else {
                setLeft(imageView);
            }
        }
        currentPosition = position;

    }

    private void setLeft(TouchImageView imageView) {
        if(images[0] != null) {
            images[2] = images[1];
            images[1] = images[0];
        }
        images[0] = imageView;
    }

    private void setRight(TouchImageView imageView) {
        if(images[2] != null) {
            images[0] = images[1];
            images[1] = images[2];
        }
        images[2] = imageView;
    }

    public boolean isImageZoom(){
        for(TouchImageView img: images){
            if(img != null && img.isZoomed())
                return true;
        }

            return false;
    }

    public void resetImageZoom(){
        for(TouchImageView img: images){
            if(img != null)
                img.resetZoom();
        }
    }
}
