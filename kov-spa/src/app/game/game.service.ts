import {Injectable} from '@angular/core';
import {HttpService} from '../core/http.service';

@Injectable()
export class GameService {

  private readonly ITEMS_URL = 'api/items';

  constructor(private httpService: HttpService) {

  }

  getItemsByPage(page, size) {
    const url = this.ITEMS_URL + `/?page=${page}&size=${size}`;
    return this.httpService.get(url, true);
  }

}
