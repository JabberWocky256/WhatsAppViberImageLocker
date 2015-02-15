package com.apps.pereverzev.alexander.whatsappviberimagelocker.oldproject.exceptions;

import java.io.IOException;

/**
 * Created by alexander on 10.01.15.
 */
public class EmptyDirectoryException extends IOException {

    /**
     * Constructs a new {@code EmptyDirectoryException} with its stack trace
     * filled in.
     */
    public EmptyDirectoryException() {
    }

    /**
     * Constructs a new {@code EmptyDirectoryException} with its stack trace and
     * detail message filled in.
     *
     * @param detailMessage the detail message for this exception.
     */
    public EmptyDirectoryException(String detailMessage) {
        super(detailMessage);
    }
}
