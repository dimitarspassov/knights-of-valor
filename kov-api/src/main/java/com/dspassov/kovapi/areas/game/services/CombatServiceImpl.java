package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.models.service.HeroCombatServiceModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitViewModel;
import com.dspassov.kovapi.exceptions.HeroFightException;
import com.dspassov.kovapi.exceptions.HeroWorkException;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CombatServiceImpl implements CombatService {

    private static final Integer LEVEL_MULTIPLIER = 100;
    private static final Long EXPERIENCE_MULTIPLIER = 101L;
    private static final Integer HEALTH_MULTIPLIER = 2;
    private static final Long LOOT_MULTIPLIER = 3L;
    private static final Integer STAMINA_BONUS_DIVISOR = 3;


    private final HeroService heroService;
    private final NeutralUnitService neutralUnitService;
    private final HeroJobService heroJobService;

    @Autowired
    public CombatServiceImpl(HeroService heroService,
                             NeutralUnitService neutralUnitService,
                             HeroJobService heroJobService) {
        this.heroService = heroService;
        this.neutralUnitService = neutralUnitService;
        this.heroJobService = heroJobService;
    }


    @Override
    public String heroFightWithNeutral(String neutralUnitId) {

        if (this.heroJobService.isAtWork()) {
            throw new HeroWorkException();
        }

        if (!this.heroService.isHeroReadyToFight()) {
            throw new HeroFightException();
        }

        HeroCombatServiceModel hero = this.heroService.getHeroCombatModel();
        NeutralUnitViewModel neutralUnit = this.neutralUnitService.getUnitById(neutralUnitId);

        long heroPoints = this.calculateHeroFightPoints(hero);
        long neutralUnitPoints = this.calculateNeutralUnitFightPoints(neutralUnit);

        this.heroService.setTimeOfLastFight();

        if (heroPoints < neutralUnitPoints) {
            this.heroService.getLootFromHero(neutralUnit.getLootGold() * LOOT_MULTIPLIER);
            return String.format(ResponseMessageConstants.FIGHT_LOST, hero.getName());
        }

        this.heroService.addExperiencePoints(Math.max(neutralUnit.getLevel() - hero.getLevel(), 1) * EXPERIENCE_MULTIPLIER);
        this.heroService.payHeroSalary(Long.valueOf(neutralUnit.getLootGold()));

        return String.format(ResponseMessageConstants.FIGHT_WON, hero.getName());
    }

    private long calculateHeroFightPoints(HeroCombatServiceModel hero) {

        long totalPoints = hero.getLevel() * LEVEL_MULTIPLIER;

        long strengthTotal = hero.getStrength();
        if (hero.getBattleSet().getWeapon() != null) {
            strengthTotal += hero.getBattleSet().getWeapon().getBonus();
        }

        long defenseTotal = hero.getDefense();
        if (hero.getBattleSet().getShield() != null) {
            defenseTotal += hero.getBattleSet().getShield().getBonus();
        }

        long staminaTotal = hero.getStamina();
        if (hero.getBattleSet().getArmor() != null) {
            staminaTotal += hero.getBattleSet().getArmor().getBonus() / STAMINA_BONUS_DIVISOR;
            defenseTotal += hero.getBattleSet().getArmor().getBonus();
        }

        totalPoints += strengthTotal + defenseTotal + staminaTotal;
        return totalPoints + (hero.getHealth() * HEALTH_MULTIPLIER);
    }

    private long calculateNeutralUnitFightPoints(NeutralUnitViewModel neutralUnit) {
        long totalPoints = neutralUnit.getLevel() * LEVEL_MULTIPLIER;
        totalPoints += neutralUnit.getStrength();
        totalPoints += neutralUnit.getDefense();
        totalPoints += neutralUnit.getStamina();
        return totalPoints + (neutralUnit.getHealth() * HEALTH_MULTIPLIER);
    }
}
