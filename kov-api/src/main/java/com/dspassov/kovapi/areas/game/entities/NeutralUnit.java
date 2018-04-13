package com.dspassov.kovapi.areas.game.entities;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;
import com.dspassov.kovapi.areas.game.enumerations.NeutralUnitType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "neutral_units")
public class NeutralUnit {




    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Length(min = GameDomainConstants.UNIT_NAME_MIN_LENGTH,
            max = GameDomainConstants.UNIT_NAME_MAX_LENGTH)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private NeutralUnitType type;

    @NotNull
    @Min(GameDomainConstants.UNIT_LEVEL_MIN)
    @Max(GameDomainConstants.UNIT_LEVEL_MAX)
    @Column(name = "level")
    private Integer level;

    @NotNull
    @Min(GameDomainConstants.UNIT_HEALTH_MIN)
    @Max(GameDomainConstants.UNIT_HEALTH_MAX)
    @Column(name = "health")
    private Integer health;

    @NotNull
    @Min(GameDomainConstants.UNIT_STAT_MIN)
    @Max(GameDomainConstants.UNIT_STAT_MAX)
    @Column(name = "strength")
    private Integer strength;

    @NotNull
    @Min(GameDomainConstants.UNIT_STAT_MIN)
    @Max(GameDomainConstants.UNIT_STAT_MAX)
    @Column(name = "defense")
    private Integer defense;

    @NotNull
    @Min(GameDomainConstants.UNIT_STAT_MIN)
    @Max(GameDomainConstants.UNIT_STAT_MAX)
    @Column(name = "stamina")
    private Integer stamina;

    @NotNull
    @Min(GameDomainConstants.UNIT_LOOT_MIN)
    @Max(GameDomainConstants.UNIT_LOOT_MAX)
    @Column(name = "loot_gold")
    private Integer lootGold;

    @NotNull
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    @NotNull
    @Column(name = "free")
    private Boolean free;

    public NeutralUnit() {
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

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public NeutralUnitType getType() {
        return type;
    }

    public void setType(NeutralUnitType type) {
        this.type = type;
    }
}
