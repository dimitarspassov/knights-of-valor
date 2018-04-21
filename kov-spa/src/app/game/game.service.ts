import {Injectable} from '@angular/core';
import {HttpService} from '../core/http.service';

@Injectable()
export class GameService {

  private readonly ITEM_TYPES = ['Weapon', 'Armor', 'Shield'];

  private readonly ITEMS_URL = 'api/items';
  private readonly UNITS_URL = 'api/neutrals';
  private readonly FREE_UNITS_URL = 'api/game/neutrals';
  private readonly JOBS_URL = 'api/jobs';
  private readonly ARENA_URL = 'api/game/hero/arena';

  constructor(private httpService: HttpService) {

  }

  getItemsByPage(page, size, query = null) {

    let customParam = '';

    if (query != null) {
      customParam = this.ITEM_TYPES.indexOf(query) > -1
        ? '&type=' + query : '&search=' + query;
    }

    let urlQuery = `/?page=${page}&size=${size}`;
    urlQuery += customParam !== '' ? customParam : '';

    const url = this.ITEMS_URL + urlQuery;

    return this.httpService.get(url, true);
  }

  getJobsByPage(page, size) {
    const url = this.JOBS_URL + `/?page=${page}&size=${size}`;
    return this.httpService.get(url, true);
  }

  getWildernessUnitsByPage(page, size) {
    const url = this.FREE_UNITS_URL + `/?page=${page}&size=${size}`;
    return this.httpService.get(url, true);
  }

  getHeroesForArena() {
    return this.httpService.get(this.ARENA_URL, true);
  }

}
