package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by opereverzyev on 12.02.15.
 */
public class GalleryGridCreator {
    private List<GalleryRow> galleryRows;
    private Iterator<String> paths;
    private int pathPosition;
    private int pathSize;

    public GalleryGridCreator(List<String> paths) {
        this.galleryRows = new ArrayList<>();
        this.paths = paths.iterator();
        this.pathSize = paths.size();
    }

    public void setElement(Image image){
        if(pathSize>2){
            
        }
    }

    public List<GalleryRow> getGrid(){
        return galleryRows;
    }
}
