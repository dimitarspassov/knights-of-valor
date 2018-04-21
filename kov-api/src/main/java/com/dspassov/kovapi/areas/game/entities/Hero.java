package com.dspassov.kovapi.areas.game.entities;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;
import com.dspassov.kovapi.areas.users.entities.User;
import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "heroes")
public class Hero {

    private static final Integer XP_MULTIPLIER = 3150;


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
    @Min(GameDomainConstants.HERO_LEVEL_MIN)
    @Column(name = "level")
    private Integer level = GameDomainConstants.HERO_LEVEL_MIN;

    @NotNull
    @Min(0)
    @Column(name = "experience")
    private Long experience = 0L;

    @NotNull
    @Min(0)
    @Column(name = "gold")
    private Long gold = 0L;

    @NotNull
    @Min(GameDomainConstants.HERO_HEALTH_MIN)
    @Column(name = "health")
    private Integer health = GameDomainConstants.HERO_HEALTH_MIN;

    @NotNull
    @Min(GameDomainConstants.HERO_STRENGTH_MIN)
    @Column(name = "strength")
    private Integer strength = GameDomainConstants.HERO_STRENGTH_MIN;

    @NotNull
    @Min(GameDomainConstants.HERO_DEFENSE_MIN)
    @Column(name = "defense")
    private Integer defense = GameDomainConstants.HERO_DEFENSE_MIN;

    @NotNull
    @Min(GameDomainConstants.HERO_STAMINA_MIN)
    @Column(name = "stamina")
    private Integer stamina = GameDomainConstants.HERO_STAMINA_MIN;

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
        if (gold < 0) {
            throw new IllegalParamException(ResponseMessageConstants.NON_NEGATIVE_NUMBER_ARGUMENT);
        }
        this.gold += gold;
    }

    public void subtractGold(Long gold) {
        if (gold < 0) {
            throw new IllegalParamException(ResponseMessageConstants.NON_NEGATIVE_NUMBER_ARGUMENT);
        }

        this.gold -= gold;
        if (this.gold < 0) {
            this.gold = 0L;
        }
    }

    public void addExperience(Long experience) {

        if (experience < 0) {
            throw new IllegalParamException(ResponseMessageConstants.NON_NEGATIVE_NUMBER_ARGUMENT);
        }

        this.experience += experience;
        if (this.experience >= this.level * XP_MULTIPLIER) {
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

        } else {
            this.health += this.level;
            this.strength++;
            this.stamina += 5;
            this.defense += this.level;
        }

        if (this.inventory.getSize() < 20) {
            this.inventory.incrementSize();
        }


        if (this.experience >= this.level * XP_MULTIPLIER) {
            this.levelUp();
        }
    }

    public Integer experiencePercentToNextLevel() {
        long currentXp = this.experience;
        double targetXp = this.level * XP_MULTIPLIER;
        double ratio = currentXp / targetXp;
        return Math.toIntExact(Math.round(ratio * 100));
    }
}
