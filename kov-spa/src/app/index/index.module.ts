import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {IndexComponent} from './index.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {CoreModule} from '../core/core.module';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {UserService} from './user.service';
import {HttpService} from '../core/http.service';


@NgModule({
  imports: [
    RouterModule,
    FormsModule,
    CommonModule,
    CoreModule
  ],
  declarations: [
    IndexComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent],
  providers: [HttpService, UserService]
})
export class IndexModule {

}
