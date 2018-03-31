import {Component} from '@angular/core';
import {NotificationService} from '../../core/notification/notification.service';
import {RegisterUserModel} from './register.user.model';
import {AppConstants} from '../../app-constants';
import {Toolbox} from '../../utils/toolbox';

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

  constructor(private notificationService: NotificationService) {
  }

  isNameValid(name: string): boolean {
    return Toolbox.isContentLengthBetween(name,
      this.NAME_MIN_LENGTH, this.NAME_MAX_LENGTH);
  }

  onSubmit() {
    console.log(this.account);
  }
}
