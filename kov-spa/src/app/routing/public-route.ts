import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {UserService} from '../index/user.service';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import {UrlConstants} from './url-constants';


@Injectable()
export class PublicRoute implements CanActivate {

  constructor(private userService: UserService, private router: Router) {

  }

  canActivate(): Observable<boolean> {
    this.userService.isUserLoggedIn()
      .subscribe(
        result => {
          if (result) {
            this.router.navigateByUrl(UrlConstants.GAME_URL);
            return Observable.of(false);
          }
        },
        error => {
          return Observable.of(false);
        }
      );
    return Observable.of(true);
  }

}
