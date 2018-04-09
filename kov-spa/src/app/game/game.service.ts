import {Injectable} from '@angular/core';
import {HttpService} from '../core/http.service';

@Injectable()
export class GameService {

  private readonly ADD_ITEM_URL = 'api/items';

  constructor(private httpService: HttpService) {

  }

  getAllItems() {
    return this.httpService.get(this.ADD_ITEM_URL, true);
  }

}
