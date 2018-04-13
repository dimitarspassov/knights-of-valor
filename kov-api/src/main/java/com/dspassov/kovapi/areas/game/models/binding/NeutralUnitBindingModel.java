package com.dspassov.kovapi.areas.game.models.binding;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;


import javax.validation.constraints.*;

public class NeutralUnitBindingModel {


    @NotEmpty(message = "Neutral unit name cannot be empty.")
    @Size(min = GameDomainConstants.UNIT_NAME_MIN_LENGTH, max = GameDomainConstants.UNIT_NAME_MAX_LENGTH,
            message = "Neutral unit name must contain between "
                    + GameDomainConstants.UNIT_NAME_MIN_LENGTH
                    + " and " + GameDomainConstants.UNIT_NAME_MAX_LENGTH
                    + " symbols.")
    private String name;

    @NotEmpty(message = "Neutral unit type cannot be empty.")
    @Pattern(regexp = "^(Regular|Special)$",
            message = "Neutral unit type must be 'Regular' or 'Special'.")
    private String type;

    @NotNull(message = "Neutral unit level cannot be null.")
    @Min(value = GameDomainConstants.UNIT_LEVEL_MIN, message = "Neutral unit level must be greater than or equal to " + GameDomainConstants.UNIT_LEVEL_MIN)
    @Max(value = GameDomainConstants.UNIT_LEVEL_MAX, message = "Neutral unit level must be less or equal to " + GameDomainConstants.UNIT_LEVEL_MAX)
    private Integer level;


    @NotNull(message = "Neutral unit health cannot be null.")
    @Min(value = GameDomainConstants.UNIT_HEALTH_MIN, message = "Neutral unit health must be greater than or equal to " + GameDomainConstants.UNIT_HEALTH_MIN)
    @Max(value = GameDomainConstants.UNIT_HEALTH_MAX, message = "Neutral unit health must be less or equal to " + GameDomainConstants.UNIT_HEALTH_MAX)
    private Integer health;

    @NotNull(message = "Neutral unit strength cannot be null.")
    @Min(value = GameDomainConstants.UNIT_STAT_MIN, message = "Neutral unit strength must be greater than or equal to " + GameDomainConstants.UNIT_STAT_MIN)
    @Max(value = GameDomainConstants.UNIT_STAT_MAX, message = "Neutral unit strength must be less or equal to " + GameDomainConstants.UNIT_STAT_MAX)
    private Integer strength;

    @NotNull(message = "Neutral unit defense cannot be null.")
    @Min(value = GameDomainConstants.UNIT_STAT_MIN, message = "Neutral unit defense must be greater than or equal to " + GameDomainConstants.UNIT_STAT_MIN)
    @Max(value = GameDomainConstants.UNIT_STAT_MAX, message = "Neutral unit defense must be less or equal to " + GameDomainConstants.UNIT_STAT_MAX)
    private Integer defense;

    @NotNull(message = "Neutral unit stamina cannot be null.")
    @Min(value = GameDomainConstants.UNIT_STAT_MIN, message = "Neutral unit stamina must be greater than or equal to " + GameDomainConstants.UNIT_STAT_MIN)
    @Max(value = GameDomainConstants.UNIT_STAT_MAX, message = "Neutral unit stamina must be less or equal to " + GameDomainConstants.UNIT_STAT_MAX)
    private Integer stamina;

    @NotNull(message = "Neutral unit loot cannot be null.")
    @Min(value = GameDomainConstants.UNIT_LOOT_MIN, message = "Neutral unit loot must be greater than or equal to " + GameDomainConstants.UNIT_LOOT_MIN)
    @Max(value = GameDomainConstants.UNIT_LOOT_MAX, message = "Neutral unit loot must be less or equal to " + GameDomainConstants.UNIT_LOOT_MAX)
    private Integer lootGold;


    public NeutralUnitBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Integer getLootGold() {
        return lootGold;
    }

    public void setLootGold(Integer lootGold) {
        this.lootGold = lootGold;
    }
}
