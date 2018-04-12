package com.dspassov.kovapi.exceptions;

import com.dspassov.kovapi.web.ResponseMessageConstants;

public class CloudStorageException extends RuntimeException {


    public CloudStorageException(String message) {
        super(message);
    }

    public CloudStorageException() {
        super(ResponseMessageConstants.CLOUD_UPLOAD_ERROR);
    }
}
