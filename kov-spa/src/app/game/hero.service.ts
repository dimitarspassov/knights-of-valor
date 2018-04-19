import {HttpService} from '../core/http.service';
import {Injectable} from '@angular/core';
import {HeroModel} from './hero.model';
import {Subject} from 'rxjs/Subject';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class HeroService {

  private readonly GET_HERO_URL = 'api/game/hero';
  private readonly GET_INVENTORY_URL = 'api/game/hero/inventory';
  private readonly GET_BATTLE_SET_URL = 'api/game/hero/battle-set';
  private readonly BUY_ITEM_URL = 'api/game/hero/items/buy/';
  private readonly SELL_ITEM_URL = 'api/game/hero/items/sell/';
  private readonly EQUIP_ITEM_URL = 'api/game/hero/items/equip/';
  private readonly UNEQUIP_ITEM_URL = 'api/game/hero/items/unequip/';

  private hero: Subject<HeroModel> = new BehaviorSubject<HeroModel>(null);
  hero$ = this.hero.asObservable();

  constructor(private httpService: HttpService) {
  }

  loadHero() {
    return this.httpService.get(this.GET_HERO_URL, true);
  }

  setHero(current: HeroModel) {
    this.hero.next(current);
  }

  buyItem(itemId: string) {
    const url = this.BUY_ITEM_URL + itemId;
    return this.httpService.post(url, null, true);
  }

  sellItem(itemId: string) {
    const url = this.SELL_ITEM_URL + itemId;
    return this.httpService.post(url, null, true);
  }

  equipItem(itemId: string) {
    const url = this.EQUIP_ITEM_URL + itemId;
    return this.httpService.post(url, null, true);
  }

  unequipItem(itemId: string) {
    const url = this.UNEQUIP_ITEM_URL + itemId;
    return this.httpService.post(url, null, true);
  }

  loadInventory() {
    return this.httpService.get(this.GET_INVENTORY_URL, true);
  }

  loadBattleSet() {
    return this.httpService.get(this.GET_BATTLE_SET_URL, true);
  }

  public doRefreshHero() {
    this.loadHero().subscribe(
      result => {
        if (result && !result.message) {
          this.setHero(this.mapHero(result));
        }
      }
    );
  }

  private mapHero(object) {

    const hero = new HeroModel();
    hero.name = object.name;
    hero.level = object.level;
    hero.health = object.health;
    hero.gold = object.gold;
    hero.strength = object.strength;
    hero.stamina = object.stamina;
    hero.defense = object.defense;
    return hero;

  }
}
