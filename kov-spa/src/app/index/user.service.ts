import {HttpService} from '../core/http.service';
import {Injectable} from '@angular/core';
import {AuthService} from '../core/auth.service';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';

@Injectable()
export class UserService {

  private readonly REGISTER_URL = 'api/register';
  private readonly LOGIN_URL = 'login';
  private readonly AUTH_VALIDATION_URL = '/api/auth';
  private readonly ADMIN_VALIDATION_URL = '/api/auth/admin';


  constructor(private httpService: HttpService, private authService: AuthService) {
  }

  register(user) {
    return this.httpService.post(this.REGISTER_URL, user);
  }

  login(user) {
    return this.httpService.post(this.LOGIN_URL, user);
  }

  isUserLoggedIn() {
    if (this.authService.isUserAuthenticated()) {
      return this.httpService.get(this.AUTH_VALIDATION_URL, true);
    }
    return Observable.of(false);
  }

  isUserAdmin() {
    if (this.authService.isUserAdmin()) {
      return this.httpService.get(this.ADMIN_VALIDATION_URL, true);
    }
    return Observable.of(false);
  }

  logout() {
    this.authService.removeUser();
    this.authService.deauthenticateUser();
    this.authService.removeRoles();
  }
}
