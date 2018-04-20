package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.services.CombatService;
import com.dspassov.kovapi.areas.game.services.HeroJobService;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.web.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/game/hero")
public class HeroController extends BaseController {

    private final HeroService heroService;
    private final HeroJobService heroJobService;
    private final CombatService combatService;

    @Autowired
    public HeroController(HeroService heroService, HeroJobService heroJobService, CombatService combatService) {
        this.heroService = heroService;
        this.heroJobService = heroJobService;
        this.combatService = combatService;
    }

    @GetMapping("")
    public String getHero() {

        return this.objectToJson(this.heroService.getCurrentHero());
    }

    @GetMapping("/arena")
    public String getArenaHeroes() {

        return this.objectToJson(this.heroService.getHeroesForArena());
    }

    @PostMapping("/items/buy/{id}")
    public String buyItem(@PathVariable String id) {

        return this.success(this.heroService.buyItem(id));
    }

    @GetMapping("/inventory")
    public String getInventory() {

        return this.objectToJson(this.heroService.getInventory());
    }

    @GetMapping("/battle-set")
    public String getBattleSet() {

        return this.objectToJson(this.heroService.getBattleSet());
    }

    @PostMapping("/items/sell/{itemId}")
    public String sellItem(@PathVariable String itemId) {

        return this.success(this.heroService.sellItem(itemId));
    }

    @PostMapping("/items/equip/{itemId}")
    public String equipItem(@PathVariable String itemId) {

        return this.success(this.heroService.equipItem(itemId));
    }

    @PostMapping("/items/unequip/{itemId}")
    public String unequipItem(@PathVariable String itemId) {

        return this.success(this.heroService.unequipItem(itemId));
    }

    @PostMapping("/job/start/{jobId}")
    public String startJob(@PathVariable String jobId) {

        return this.success(this.heroJobService.startJob(jobId));
    }

    @PostMapping("/job/abandon/{jobId}")
    public String abandonJob(@PathVariable String jobId) {

        return this.success(this.heroJobService.abandonJob(jobId));
    }

    @PostMapping("/job/finish/{jobId}")
    public String finishJob(@PathVariable String jobId) {
        return this.success(this.heroJobService.finishJob(jobId));
    }

    @GetMapping("/job")
    public String isHeroAtWork() {
        return this.objectToJson(this.heroJobService.getCurrentJob());
    }

    @PostMapping("/neutrals/fight/{unitId}")
    public String fightNeutral(@PathVariable String unitId) {
        return this.success(this.combatService.heroFightWithNeutral(unitId));
    }

    @PostMapping("/arena/fight/{heroId}")
    public String fightHero(@PathVariable String heroId) {
        return this.success(this.combatService.heroFightOnArena(heroId));
    }
}

