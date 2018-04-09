package com.dspassov.kovapi.web;

public final class ResponseMessageConstants {

    public static final String EXISTING_USER = "This email is already taken.";
    public static final String EXISTING_HERO = "This hero name is already taken.";
    public static final String REGISTRATION_SUCCESSFUL = "You have registered successfully.";
    public static final String PASSWORDS_MISMATCH = "Passwords do not match.";

    public static final String INCORRECT_FILE_EXTENSION = "Image must have .png, .jpg or .jpeg extension.";
    public static final String FILE_UPLOAD_FAILED = "Fail upload failed. Please, try again.";
    public static final String EXISTING_ITEM = "This item already exists.";
    public static final String NON_EXISTENT_ITEM = "This item does not exists.";
    public static final String UNSUPPORTED_ITEM_TYPE = "Item must be Weapon, Armor or Shield.";
    public static final String ITEM_ADDED = "Item added successfully.";
    public static final String ITEM_EDITED = "Item edited successfully.";


    public static final String GENERIC_ERROR = "An error occurred. Please, try again.";


    private ResponseMessageConstants() {
    }
}
