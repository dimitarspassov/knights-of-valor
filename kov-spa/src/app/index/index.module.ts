import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {IndexComponent} from './index.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {CoreModule} from '../core/core.module';
import {NotificationService} from '../core/notification/notification.service';


@NgModule({
  imports: [
    RouterModule,
    CoreModule
  ],
  declarations: [
    IndexComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent],
  providers: [NotificationService]
})
export class IndexModule {

}
