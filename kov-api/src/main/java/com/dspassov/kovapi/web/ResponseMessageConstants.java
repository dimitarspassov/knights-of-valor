package com.dspassov.kovapi.web;

public final class ResponseMessageConstants {

    public static final String EXISTING_USER = "This email is already taken.";
    public static final String EXISTING_HERO = "This hero name is already taken.";
    public static final String REGISTRATION_SUCCESSFUL = "You have registered successfully.";
    public static final String PASSWORDS_MISMATCH = "Passwords do not match.";

    public static final String NON_EXISTENT_USER = "A user with this email does not exist.";
    public static final String ALREADY_ADMIN = "The given user is admin.";
    public static final String ADMIN_CREATED = "User successfully added as ADMIN";
    public static final String USER_NOT_ADMIN = "The given user is not ADMIN";
    public static final String ADMIN_REMOVED = "ADMIN authority removed for the given user.";

    public static final String INCORRECT_FILE_EXTENSION = "Image must have .png, .jpg or .jpeg extension.";
    public static final String EXISTING_ITEM = "This item already exists.";
    public static final String NON_EXISTENT_ITEM = "This item does not exists.";
    public static final String UNSUPPORTED_ITEM_TYPE = "Item must be Weapon, Armor or Shield.";
    public static final String ITEM_ADDED = "Item added successfully.";
    public static final String ITEM_EDITED = "Item edited successfully.";

    public static final String EXISTING_UNIT = "This neutral unit already exists.";
    public static final String UNIT_ADDED = "Neutral unit added successfully.";
    public static final String NON_EXISTENT_UNIT = "This neutral unit does not exists.";
    public static final String UNIT_EDITED = "Unit edited successfully.";

    public static final String EXISTING_JOB = "This job already exists.";
    public static final String JOB_ADDED = "Job added successfully.";
    public static final String NON_EXISTENT_JOB = "This job does not exists.";
    public static final String JOB_EDITED = "Job edited successfully.";

    public static final String INSUFFICIENT_FUNDS = "You do not have enough gold to get this item.";
    public static final String INVENTORY_FULL = "You already have reached the limit of your inventory";
    public static final String ITEM_BOUGHT = "You have bought this item successfully.";
    public static final String ITEM_SOLD = "You have sold this item successfully.";
    public static final String ITEM_EQUIPPED = "Item added to battle set.";
    public static final String ITEM_UNEQUIPPED = "Item removed from battle set.";
    public static final String ITEM_IN_BATTLE_SET = "This item is still in the hero's battle set.";

    public static final String HERO_AT_WORK = "The hero is currently at work.";
    public static final String JOB_STARTED = "%s job started.";
    public static final String HERO_NOT_AT_WORK = "There is not a job associated with the current hero.";
    public static final String JOB_ALREADY_DONE = "The current job is already done";
    public static final String JOB_ABANDONED = "Job abandoned. %d gold earned.";

    public static final String GENERIC_ERROR = "An error occurred. Please, try again.";
    public static final String CLOUD_UPLOAD_ERROR = "An error occurred. Please, make sure the image is in the correct format and its size is below 3MB.";
    public static final String JOB_FINISHED = "Job finished. %d gold earned.";

    public static final String HERO_CANNOT_FIGHT = "Your hero is still not recovered from the last fight.";
    public static final String FIGHT_LOST = "Unfortunately, %s has lost the fight. Some gold will be spent for recovery.";
    public static final String FIGHT_WON = "%s has won the fight! Experience is gained, as well as loot gold!";
    public static final String NON_EXISTENT_HERO = "A hero with this id does not exits.";
    public static final String CANNOT_TARGET_HIMSELF = "The enemy hero id must be different from the current hero id.";
    public static final String FIGHT_DRAWN = "The fight ended without a winner. %s needs rest now.";


    private ResponseMessageConstants() {
    }
}
