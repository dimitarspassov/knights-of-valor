package com.dspassov.kovapi.areas.game.entities;

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

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 40;

    // Matching strength, stamina & defense
    private static final int STAT_MIN = 1;
    private static final int STAT_MAX = 1000000;

    private static final int HEALTH_MIN = 10;
    private static final int HEALTH_MAX = 1000000;
    private static final int LEVEL_MIN = 1;
    private static final int LEVEL_MAX = 10000;
    private static final int LOOT_MIN = 1;
    private static final int LOOT_MAX = 400000;


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Length(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private NeutralUnitType type;

    @NotNull
    @Min(LEVEL_MIN)
    @Max(LEVEL_MAX)
    @Column(name = "level")
    private Integer level;

    @NotNull
    @Min(HEALTH_MIN)
    @Max(HEALTH_MAX)
    @Column(name = "health")
    private Integer health;

    @NotNull
    @Min(STAT_MIN)
    @Max(STAT_MAX)
    @Column(name = "strength")
    private Integer strength;

    @NotNull
    @Min(STAT_MIN)
    @Max(STAT_MAX)
    @Column(name = "defense")
    private Integer defense;

    @NotNull
    @Min(STAT_MIN)
    @Max(STAT_MAX)
    @Column(name = "stamina")
    private Integer stamina;

    @NotNull
    @Min(LOOT_MIN)
    @Max(LOOT_MAX)
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
