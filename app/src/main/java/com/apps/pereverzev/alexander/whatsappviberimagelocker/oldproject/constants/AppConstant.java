package com.apps.pereverzev.alexander.whatsappviberimagelocker.oldproject.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alexander on 10.01.15.
 */
public class AppConstant {
    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
    public static final String PHOTO_ALBUM = "Download";
    public static final String PHOTO_ALBUM_WATS_APP = "WhatsApp/Media/WhatsApp Images";
    public static final String PHOTO_ALBUM_WATS_APP_SENT = "WhatsApp/Media/WhatsApp Images";
    public static final String PHOTO_ALBUM_VIBER = "viber/media/Viber Images";

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg", "png", "jpgevg", "jpegevg", "pngevg");
    public static final String HIDE_FILE_EXTN = ".evg";
}
