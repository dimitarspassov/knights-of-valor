import {Component} from '@angular/core';
import {LoginUserModel} from './login.user.model';
import {UserService} from '../user.service';
import {Router} from '@angular/router';
import {AuthService} from '../../core/auth.service';
import {UrlConstants} from '../../routing/url-constants';
import {NotificationService} from '../../core/notifications/notification.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['../../app.component.scss', '../index.component.scss']
})
export class LoginComponent {

  constructor(private userService: UserService,
              private authService: AuthService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  user: LoginUserModel = new LoginUserModel();


  onSubmit() {
    this.userService.login(this.user)
      .subscribe(
        result => this.loginUser(result),
        error => this.handleIncorrectLoginAttempt(error)
      );
  }

  private loginUser(result) {
    this.authService.authenticateUser(result.authToken);
    this.authService.saveUser(result.user);
    this.authService.addRoles(result.roles);
    this.notificationService.notify('Welcome, warrior!');
    this.router.navigateByUrl(UrlConstants.GAME_URL);
  }

  private handleIncorrectLoginAttempt(error) {
    const errorMessage = error.status === 403 ?
      'Invalid credentials'
      :
      'An error occured. Please, try again.';
    this.notificationService.showError('Invalid credentials.');
  }
}
