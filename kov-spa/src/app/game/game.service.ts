import {Injectable} from '@angular/core';
import {HttpService} from '../core/http.service';

@Injectable()
export class GameService {

  private readonly ITEMS_URL = 'api/items';
  private readonly UNITS_URL = 'api/neutrals';
  private readonly JOBS_URL = 'api/jobs';

  constructor(private httpService: HttpService) {

  }

  getItemsByPage(page, size) {
    const url = this.ITEMS_URL + `/?page=${page}&size=${size}`;
    return this.httpService.get(url, true);
  }

  getUnitsByPage(page, size) {
    const url = this.UNITS_URL + `/?page=${page}&size=${size}`;
    return this.httpService.get(url, true);
  }

  getJobsByPage(page, size) {
    const url = this.JOBS_URL + `/?page=${page}&size=${size}`;
    return this.httpService.get(url, true);
  }

}
