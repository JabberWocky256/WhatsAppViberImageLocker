package com.apps.pereverzev.alexander.whatsappviberimagelocker;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.constants.AppConstant;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.exceptions.DirectoryPassIsNotValid;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.exceptions.EmptyDirectoryException;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Александр on 02.11.2014.
 */
public class Utils {
    private ArrayList<String> filePaths;

    // Reading file paths from SDCard
    public synchronized ArrayList<String> getFilePaths() throws EmptyDirectoryException, DirectoryPassIsNotValid {
        filePaths = new ArrayList<String>();

        File directory = new File(
                android.os.Environment.getExternalStorageDirectory() + "");

        /*File directory = new File("mnt/sdcard/EncryptedImage");*/

        // check for directory
        if (directory.isDirectory()) {
            filePaths = getFilesList(directory);

        } else {
            throw new DirectoryPassIsNotValid("directory path is not valid! Please set the image directory name AppConstant.java class");
        }

        return filePaths;
    }

    // getting list of file paths
    private  ArrayList<String> getFilesList(File directory) throws EmptyDirectoryException {
        File[] listFiles = directory.listFiles();
        String[] listFileNames = directory.list();


        // Check for count
        if (listFiles.length > 0) {

            // loop through all files
            for (int i = 0; i < listFiles.length; i++) {

                String name = listFileNames[i];
                if(name.startsWith(".") || name.equals("User photos") || name.equals("Profile Picture")|| name.equals("Android")){
                    continue;
                }

                // get file path
                String filePath = listFiles[i].getAbsolutePath();

                try {
                    // check for supported file extension
                    if (IsSupportedFile(filePath)) {
                        // Add image path to array list
                        GridViewImageAdapter.setPath(filePath);
                    } else if (new File(filePath).isDirectory()) {
                        try {
                            getFilesList(new File(filePath));
                        } catch (Exception e){ }
                    }
                }catch (Exception ex){
                    Log.e("HERE ERROR", filePath);
                }
            }
        }

        return filePaths;
    }

    public static void getFilesFromViberWhatsApp(){
        Utils utils = new Utils();
        try {
            utils.getFilesList(new File(android.os.Environment.getExternalStorageDirectory() + "/" + AppConstant.PHOTO_ALBUM_VIBER)) ;
            utils.getFilesList(new File(android.os.Environment.getExternalStorageDirectory() + "/" + AppConstant.PHOTO_ALBUM_WATS_APP));
            //  utils.getFilesList(new File(android.os.Environment.getExternalStorageDirectory() + "/" + AppConstant.PHOTO_ALBUM_WATS_APP_SENT));
        } catch (EmptyDirectoryException e) {
            e.printStackTrace();
        }


    }

    // Check supported file extensions
    public boolean IsSupportedFile(String filePath) {
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (AppConstant.FILE_EXTN
                .contains(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;

    }

    public boolean isSpecialFile(String filePath){
        String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                filePath.length());

        if (AppConstant.HIDE_FILE_EXTN
                .equals(ext.toLowerCase(Locale.getDefault())))
            return true;
        else
            return false;
    }

    /*
     * getting screen width
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public int getScreenWidth(Context _context) {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
