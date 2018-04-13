package com.dspassov.kovapi.areas.game.common;

public final class Toolbox {

    private Toolbox() {
    }

    public static boolean isValidImage(String fileName) {
        return fileName.endsWith(".png")
                || fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg");
    }

    public static String getImageId(String image) {

        String currentImageId = image.substring(
                image.lastIndexOf("/") + 1
        );

        return currentImageId.substring(0, currentImageId.lastIndexOf("."));
    }
}
