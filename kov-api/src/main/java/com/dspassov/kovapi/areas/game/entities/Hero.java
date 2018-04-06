package com.dspassov.kovapi.areas.game.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "heroes")
public class Hero {

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 30;
    private static final int LEVEL_MIN = 1;
    private static final int HEALTH_MIN = 10;
    private static final int STRENGTH_MIN = 3;
    private static final int DEFENSE_MIN = 3;
    private static final int STAMINA_MIN = 1;


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
    @Min(LEVEL_MIN)
    @Column(name = "level")
    private Integer level;

    @NotNull
    @Min(0)
    @Column(name = "experience")
    private Long experience;

    @NotNull
    @Min(0)
    @Column(name = "gold")
    private Long gold;

    @NotNull
    @Min(HEALTH_MIN)
    @Column(name = "health")
    private Integer health;

    @NotNull
    @Min(STRENGTH_MIN)
    @Column(name = "strength")
    private Integer strength;

    @NotNull
    @Min(DEFENSE_MIN)
    @Column(name = "defense")
    private Integer defense;

    @NotNull
    @Min(STAMINA_MIN)
    @Column(name = "stamina")
    private Integer stamina;

    @Column(name = "last_fight_date")
    private LocalDateTime lastFightDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "battle_set_id", referencedColumnName = "id")
    private BattleSet battleSet;

    @OneToOne(optional = false)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    public Hero() {
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

    public Long getExperience() {
        return experience;
    }

    public void setExperience(Long experience) {
        this.experience = experience;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
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

    public LocalDateTime getLastFightDate() {
        return lastFightDate;
    }

    public void setLastFightDate(LocalDateTime lastFightDate) {
        this.lastFightDate = lastFightDate;
    }

    public BattleSet getBattleSet() {
        return battleSet;
    }

    public void setBattleSet(BattleSet battleSet) {
        this.battleSet = battleSet;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
