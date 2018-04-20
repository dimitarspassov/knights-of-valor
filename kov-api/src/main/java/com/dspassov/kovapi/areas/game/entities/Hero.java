package com.dspassov.kovapi.areas.game.entities;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;
import com.dspassov.kovapi.areas.users.entities.User;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "heroes")
public class Hero {


    //todo: fix
    private static final Integer LEVEL_MULTIPLIER = 1000;
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
    @Length(min = GameDomainConstants.HERO_NAME_MIN_LENGTH, max = GameDomainConstants.HERO_NAME_MAX_LENGTH)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Min(LEVEL_MIN)
    @Column(name = "level")
    private Integer level = LEVEL_MIN;

    @NotNull
    @Min(0)
    @Column(name = "experience")
    private Long experience = 0L;

    @NotNull
    @Min(0)
    @Column(name = "gold")
    private Long gold = 0L;

    @NotNull
    @Min(HEALTH_MIN)
    @Column(name = "health")
    private Integer health = HEALTH_MIN;

    @NotNull
    @Min(STRENGTH_MIN)
    @Column(name = "strength")
    private Integer strength = STRENGTH_MIN;

    @NotNull
    @Min(DEFENSE_MIN)
    @Column(name = "defense")
    private Integer defense = DEFENSE_MIN;

    @NotNull
    @Min(STAMINA_MIN)
    @Column(name = "stamina")
    private Integer stamina = STAMINA_MIN;

    @Column(name = "last_fight_date")
    private LocalDateTime lastFightDate;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "battle_set_id", referencedColumnName = "id")
    private BattleSet battleSet;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @OneToOne(mappedBy = "hero")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addGold(Long gold) {
        this.gold += gold;
    }

    public void subtractGold(Long gold) {
        this.gold -= gold;
        if (this.gold < 0) {
            this.gold = 0L;
        }
    }

    public void addExperience(Long experience) {
        this.experience += experience;
        if (this.experience >= this.level * LEVEL_MULTIPLIER) {
            this.levelUp();
        }
    }

    private void levelUp() {
        this.level++;

        if (this.level % 2 == 0) {
            this.health += this.level * 2;
            this.stamina++;
            this.strength += this.level;
            this.defense++;

        } else if (this.level % 5 == 0) {
            this.health += this.level;
            this.strength++;
            this.stamina += 5;
            this.defense += this.level;
        } else {
            this.health += 10;
            this.strength += this.level;
            this.stamina += this.level;
            this.defense++;
        }

        this.inventory.incrementSize();
    }

    public Integer experiencePercentToNextLevel() {
        long currentXp = this.experience;
        double targetXp = this.level * LEVEL_MULTIPLIER;
        double ratio = currentXp / targetXp;
        return Math.toIntExact(Math.round(ratio * 100));
    }
}
