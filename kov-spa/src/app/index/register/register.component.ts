import {Component} from '@angular/core';
import {RegisterUserModel} from './register.user.model';
import {AppConstants} from '../../app-constants';
import {Toolbox} from '../../utils/toolbox';
import {UserService} from '../user.service';
import {Router} from '@angular/router';
import {UrlConstants} from '../../routing/url-constants';
import {NotificationService} from '../../core/notifications/notification.service';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['../../app.component.scss', '../index.component.scss']
})
export class RegisterComponent {

  readonly NAME_MIN_LENGTH: number = AppConstants.HERO_NAME_MIN_LENGTH;
  readonly NAME_MAX_LENGTH: number = AppConstants.HERO_NAME_MAX_LENGTH;
  readonly PASSWORD_MIN_LENGTH: number = 5;

  account: RegisterUserModel = new RegisterUserModel();

  constructor(
    private userService: UserService,
    private notificationService: NotificationService,
    private router: Router) {
  }


  isNameValid(name: string): boolean {
    return Toolbox.isContentLengthBetween(name,
      this.NAME_MIN_LENGTH, this.NAME_MAX_LENGTH);
  }

  onSubmit() {
    this.notificationService.loading();
    this.userService.register(this.account)
      .subscribe(result => {
        if (result.success) {
          this.notificationService.notify('Registration successful');
          this.router.navigateByUrl(UrlConstants.LOGIN_URL);
        } else {
          this.notificationService.showError(result.message);
        }
      });
  }
}
