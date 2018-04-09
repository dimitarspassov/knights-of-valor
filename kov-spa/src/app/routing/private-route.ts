import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {UserService} from '../index/user.service';
import 'rxjs/add/observable/of';

@Injectable()
export class PrivateRoute implements CanActivate {

  constructor(private userService: UserService, private router: Router) {

  }

  canActivate(): Observable<boolean> {
    this.userService.isUserLoggedIn()
      .subscribe(
        result => {
          if (!result) {
            this.router.navigateByUrl('');
            return Observable.of(false);
          }
        },
        error => {
          this.router.navigateByUrl('');
          return Observable.of(false);
        }
      );
    return Observable.of(true);
  }

}
