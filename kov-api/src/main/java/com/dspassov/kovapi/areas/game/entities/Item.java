package com.dspassov.kovapi.areas.game.entities;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Item {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Length(min = GameDomainConstants.ITEM_NAME_MIN_LENGTH,
            max = GameDomainConstants.ITEM_NAME_MAX_LENGTH)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Min(GameDomainConstants.ITEM_BONUS_MIN)
    @Max(GameDomainConstants.ITEM_BONUS_MAX)
    @Column(name = "bonus")
    private Integer bonus;

    @NotNull
    @Min(GameDomainConstants.ITEM_PRICE_MIN)
    @Max(GameDomainConstants.ITEM_PRICE_MAX)
    @Column(name = "price")
    private Integer price;

    @NotNull
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    @Column(name = "TYPE", insertable = false, updatable = false)
    private String type;

    protected Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
