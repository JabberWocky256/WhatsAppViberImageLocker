package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;

import android.graphics.Bitmap;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.BitmapLoader;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class Image {
    private int length = 0;
    private int width = 0;
    private int iconLength = 0;
    private int iconWidth = 0;
    private String imagePath;

    public Image(String imagePath) {
        this.length = length;
        this.width = width;
        this.iconLength = iconLength;
        this.iconWidth = iconWidth;
        this.imagePath = imagePath;
    }

    public Bitmap getImageBitmap(){
        Bitmap result = null;

        if(imagePath != null){
            BitmapLoader bitmapLoader = new BitmapLoader();
            result = bitmapLoader.getImage(imagePath, 100, 100);
        }

        return result;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getIconLength() {
        return iconLength;
    }

    public int getIconWidth() {
        return iconWidth;
    }
}
