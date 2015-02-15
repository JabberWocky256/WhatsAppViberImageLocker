package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;

import android.graphics.Bitmap;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.BitmapLoader;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class Image {
    private DisplaySize.Size fullSize;
    private DisplaySize.Size iconSize;
    private String imagePath;

    public Image(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image(DisplaySize.Size fullSize, DisplaySize.Size iconSize, String imagePath) {
        this.fullSize = fullSize;
        this.iconSize = iconSize;
        this.imagePath = imagePath;
    }

    public Bitmap getImageBitmap() {
        Bitmap result = null;

        if (imagePath != null) {
            BitmapLoader bitmapLoader = new BitmapLoader();
            result = bitmapLoader.getImage(imagePath, (int) iconSize.width, (int) iconSize.height);
        }

        return result;
    }

    public String getImagePath() {
        return imagePath;
    }

    public DisplaySize.Size getFullSize() {
        if (fullSize == null) {
            fullSize = new BitmapLoader().getImageSize(imagePath);
        }
        return fullSize;
    }

    public DisplaySize.Size getIconSize() {
        return iconSize;
    }

    public void setIconSize(DisplaySize.Size iconSize) {
        this.iconSize = iconSize;
    }
}
