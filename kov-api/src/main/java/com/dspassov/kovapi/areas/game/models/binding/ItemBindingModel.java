package com.dspassov.kovapi.areas.game.models.binding;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;

import javax.validation.constraints.*;

public class ItemBindingModel {

    @NotEmpty(message = "Item name cannot be empty.")
    @Size(min = GameDomainConstants.ITEM_NAME_MIN_LENGTH, max = GameDomainConstants.ITEM_NAME_MAX_LENGTH,
            message = "Item name must contain between "
                    + GameDomainConstants.ITEM_NAME_MIN_LENGTH
                    + " and " + GameDomainConstants.ITEM_NAME_MAX_LENGTH
                    + " symbols.")
    private String name;


    @NotEmpty(message = "Item type cannot be empty.")
    @Pattern(regexp = "^(Armor|Shield|Weapon)$",
            message = "Item type must be 'Armor', 'Shield' or 'Weapon'.")
    private String type;


    @NotNull(message = "Item price cannot be null.")
    @Min(value = GameDomainConstants.ITEM_PRICE_MIN, message = "Item price must be greater than or equal to " + GameDomainConstants.ITEM_PRICE_MIN)
    @Max(value = GameDomainConstants.ITEM_PRICE_MAX, message = "Item price must be less or equal to " + GameDomainConstants.ITEM_PRICE_MAX)
    private Integer price;

    @NotNull(message = "Item bonus cannot be null.")
    @Min(value = GameDomainConstants.ITEM_BONUS_MIN, message = "Item bonus must be greater than or equal to " + GameDomainConstants.ITEM_BONUS_MIN)
    @Max(value = GameDomainConstants.ITEM_BONUS_MAX, message = "Item bonus must be less or equal to " + GameDomainConstants.ITEM_BONUS_MAX)
    private Integer bonus;


    public ItemBindingModel() {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

}
