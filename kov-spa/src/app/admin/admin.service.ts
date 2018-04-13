import {HttpService} from '../core/http.service';
import {Injectable} from '@angular/core';

@Injectable()
export class AdminService {

  private readonly GET_ADMINS_URL = 'api/superadmin/users';
  private readonly ADD_ADMIN_URL = 'api/superadmin/new-admin/';
  private readonly REMOVE_ADMIN_URL = 'api/superadmin/remove-admin/';

  constructor(private httpService: HttpService) {
  }

  getAdminUsersByPage(page: number, size: number) {
    return this.httpService.get(
      this.GET_ADMINS_URL + `/?page=${page}&size=${size}`,
      true);
  }

  makeAdmin(username: string) {
    return this.httpService.post(this.ADD_ADMIN_URL + username, null, true);
  }

  removeAdmin(username: string) {
    return this.httpService.post(this.REMOVE_ADMIN_URL + username, null, true);
  }

}
