package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;

import android.app.Activity;
import android.util.Log;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.test.TestData;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private LinkedList<Image> images;
    private int imgNumber;
    private String TAG = getClass().getSimpleName().toString();
    private DisplaySize displaySize;
    private Activity context;
    private int size;
    private int maxNumberOfColumns;

    public GalleryGridCreator(List<String> paths, int maxNumberOfColumns) {
        this.galleryRows = new ArrayList<>();
        this.images = new LinkedList<>();
        this.paths = paths;
        random = new Random();
        this.maxNumberOfColumns = maxNumberOfColumns;
    }

    public static void test(Activity context) {
        for (int i = 0; i < 10; i++) {
            GalleryGridCreator creator = new GalleryGridCreator(TestData.getImagesPaths(), 3);
            creator.getGrid(context);
        }
    }

    public List<GalleryRow> getGrid(Activity context) {
        this.context = context;

        for (String path : paths) {
            File image = new File(path);

            if (image.exists() && !image.isDirectory())
                images.add(new Image(path));

            size = images.size();
        }

        createRows();
        return galleryRows;
    }

    private void createRows() {
        while (imgNumber < size){
            int numberFreeImages = size - imgNumber;
            int numberOfImagesInRow = getRandomNumber(numberFreeImages);
            setImages(numberOfImagesInRow);
        }
    }

    private void createGalleryRows() {
        if (size >= 3) {
            while (imgNumber < size) {
                int numberOfImages = getRandom();
                if (numberOfImages == 0)
                    break;

                setImages(numberOfImages);
            }
        } else if (size == 2) {
            addTwoElements();
        } else if (size == 1) {
            addOneElement();
        }
    }

    private int getRandom() {
        int result = 0;

        if (size - imgNumber >= 3) {
            result = getRandomNumber();
        } else if (size - imgNumber == 2) {
            result = TWO_ELEMENTS;
            imgNumber += 2;
        } else if (size - imgNumber == 1) {
            result = ONE_ELEMENT;
            imgNumber++;
        } else {
            imgNumber++;
            Log.e(TAG, "must be never called");
        }

        Log.d(TAG, "return random image numbers: " + result);
        return result;
    }

    private int getRandomNumber(int maxValue) {
        if(maxNumberOfColumns < maxValue)
            maxValue = maxNumberOfColumns;

        int rand;
        int max = getMaxRandomNumber(maxValue);
        do {
            rand = random.nextInt(max);
        } while (rand == 0);

        Log.d(TAG, "random number: " + rand);

       return getQuantityOfImagesInRow(max, rand);
    }

    private int getMaxRandomNumber(int amount){
        if(amount <= 0)
            return 0;

        return amount + getMaxRandomNumber(amount-1);
    }

    private int getQuantityOfImagesInRow(int maxRandomNumber, int currentQuantity){
        int result = 1;
        while(maxRandomNumber <= currentQuantity){
            for(int i = 0; i<result; i++){
                maxRandomNumber--;
            }
            result++;
        }

        return result;
    }

    private int getRandomNumber() {
        int r = 0;
        while (r == 0)
            r = random.nextInt(7);


        Log.d(TAG, "random number: " + r);

        if (r == 6) {
            imgNumber++;
            return ONE_ELEMENT;
        } else if (r > 3 && r < 6) {
            imgNumber += 2;
            return TWO_ELEMENTS;
        } else {
            imgNumber += 3;
            return THREE_ELEMENTS;
        }
    }

    private void setImages(int numberOfImages) {
        switch (numberOfImages) {
            case THREE_ELEMENTS:
                addTreeElements();
                break;
            case TWO_ELEMENTS:
                addTwoElements();
                break;
            case ONE_ELEMENT:
                addOneElement();
                break;
        }
    }

    private void addTreeElements() {
        Log.d(TAG, "added 3 elements");

        DisplaySize.Size imageSize = getSmallElementSize();
        Image[] threeImages = new Image[3];

        for (int i = 0; i < threeImages.length; i++) {
            threeImages[i] = images.remove();
            threeImages[i].setIconSize(imageSize);
        }

        GalleryRow row = new GalleryRow();
        row.addArray(threeImages);
        galleryRows.add(row);

    }

    private void addTwoElements() {
        Log.d(TAG, "added 2 elements");

        Image[] twoImages = new Image[2];

        twoImages[0] = images.remove();
        twoImages[1] = images.remove();
        GalleryRow row = new GalleryRow();

        int whichImageBigger = twoImages[0].getFullSize().compareTo(twoImages[1].getFullSize());
        if (whichImageBigger < 0) {
            setMediumSmallSize(twoImages[1], twoImages[0]);
        } else if(whichImageBigger > 0) {
            setMediumSmallSize(twoImages[0], twoImages[1]);
        } else if(whichImageBigger == 0){
            if(random.nextBoolean())
                setMediumSmallSize(twoImages[1], twoImages[0]);
            else
                setMediumSmallSize(twoImages[0], twoImages[1]);
        }

        row.addArray(twoImages);
        galleryRows.add(row);
    }

    private void setMediumSmallSize(Image first, Image second) {
        first.setIconSize(getMediumElementSize());
        second.setIconSize(getSmallElementSize());
    }

    private void addOneElement() {
        Log.d(TAG, "added 1 elements");

        DisplaySize.Size imageSize = getLargeElementSize();

        Image image = images.remove();
        image.setIconSize(imageSize);

        GalleryRow row = new GalleryRow();
        row.addImage(image);
        galleryRows.add(row);
    }

    private DisplaySize.Size getSmallElementSize() {
        displaySize = getDisplaySize();
        return displaySize.getSmallElementSize(context);
    }

    private DisplaySize.Size getMediumElementSize() {
        displaySize = getDisplaySize();
        return displaySize.getMediumElementSize(context);
    }

    private DisplaySize.Size getLargeElementSize() {
        displaySize = getDisplaySize();
        return displaySize.getLargeElementSize(context);
    }

    private DisplaySize getDisplaySize() {
        if (displaySize == null)
            displaySize = new DisplaySize();

        return displaySize;
    }
}
