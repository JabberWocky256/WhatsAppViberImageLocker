package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;

import android.nfc.Tag;
import android.util.Log;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.test.TestData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by opereverzyev on 12.02.15.
 */
public class GalleryGridCreator {
    private final static int ONE_ELEMENT = 1;
    private final static int TWO_ELEMENTS = 2;
    private final static int THREE_ELEMENTS = 3;

    private Random random;
    private List<GalleryRow> galleryRows;
    private List<String> paths;
    private List<Image> images;
    private int pathNumber = 1;
    private String TAG = getClass().getSimpleName().toString();

    public GalleryGridCreator(List<String> paths) {
        this.galleryRows = new ArrayList<>();
        this.images = new ArrayList<>();
        this.paths = paths;
    }

    public List<GalleryRow> getGrid(){
        for(String path: paths){
            File image = new File(path);

            if(image.exists())
                images.add(new Image(path));
        }

        createGalleryRows();
        return galleryRows;
    }

    private void createGalleryRows(){
        if(paths.size()>2){
            int size = paths.size();
            for(; pathNumber <= size; paths.size()) {
                int numberOfImages = getRandom();
                if(numberOfImages == 0)
                    break;

                setImages(numberOfImages);
            }
        }

        //TODO: else
    }

    private int getRandom(){
        int result = 0;
        int size = paths.size();

        if(size - pathNumber >= 2) {
            result = getRandomNumber();
        } else if(size - pathNumber == 1){
            result = TWO_ELEMENTS;
            pathNumber += 2;
        } else if(size - pathNumber == 0){
            result = ONE_ELEMENT;
            pathNumber ++;
        } else {
            pathNumber++;
            Log.e(TAG, "must be never called");
        }

        Log.d(TAG, "return random image numbers: " + result);
        return result;
    }

    private int getRandomNumber(){
        random = new Random();

        int r = 0;
        while(r == 0)
            r = random.nextInt(7);

        Log.d(TAG, "random number: " + r);

        if(r == 6) {
            pathNumber ++;
            return ONE_ELEMENT;
        } else if(r >3 && r < 6) {
            pathNumber += 2;
            return TWO_ELEMENTS;
        } else {
            pathNumber +=3;
            return THREE_ELEMENTS;
        }
    }

    private void setImages(int numberOfImages){
        switch (numberOfImages){
            case THREE_ELEMENTS: addTreeElements(); break;
            case TWO_ELEMENTS: addTwoElements(); break;
            case ONE_ELEMENT: addOneElement(); break;
        }
    }

    private void addTreeElements(){
        Log.d(TAG,"added 3 elements");
    }

    private void addTwoElements(){
        Log.d(TAG,"added 2 elements");
    }

    private void addOneElement(){
        Log.d(TAG,"added 1 elements");
    }

    public static void test(){
        for(int i = 0; i<10; i++) {
            GalleryGridCreator creator = new GalleryGridCreator(TestData.getImagesPaths());
            creator.getGrid();
        }
    }
}
