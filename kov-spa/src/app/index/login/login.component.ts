import {Component} from '@angular/core';
import {LoginUserModel} from './login.user.model';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['../../app.component.scss', '../index.component.scss']
})
export class LoginComponent {

  user: LoginUserModel = new LoginUserModel();

  onSubmit() {
    console.log(this.user);
  }
}
