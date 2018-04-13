package com.dspassov.kovapi.areas.game.common;

public final class GameDomainConstants {

    // heroes
    public static final int HERO_NAME_MIN_LENGTH = 3;
    public static final int HERO_NAME_MAX_LENGTH = 30;


    // items
    public static final int ITEM_NAME_MIN_LENGTH = 3;
    public static final int ITEM_NAME_MAX_LENGTH = 40;
    public static final int ITEM_BONUS_MIN = 1;
    public static final int ITEM_BONUS_MAX = 100000;
    public static final int ITEM_PRICE_MIN = 1;
    public static final int ITEM_PRICE_MAX = 1000000;


    // neutrals
    public static final int UNIT_NAME_MIN_LENGTH = 3;
    public static final int UNIT_NAME_MAX_LENGTH = 40;

    // Matching strength, stamina & defense
    public static final int UNIT_STAT_MIN = 1;
    public static final int UNIT_STAT_MAX = 1000000;

    public static final int UNIT_HEALTH_MIN = 10;
    public static final int UNIT_HEALTH_MAX = 1000000;
    public static final int UNIT_LEVEL_MIN = 1;
    public static final int UNIT_LEVEL_MAX = 10000;
    public static final int UNIT_LOOT_MIN = 1;
    public static final int UNIT_LOOT_MAX = 400000;


    private GameDomainConstants() {
    }
}
