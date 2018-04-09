import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpService} from './http.service';
import {AuthService} from './auth.service';
import {NotificationComponent} from './notifications/notification.component';
import {NotificationService} from './notifications/notification.service';


@NgModule({
  imports: [BrowserModule],
  declarations: [NotificationComponent],
  providers: [HttpService, AuthService, NotificationService],
  exports: [NotificationComponent]
})
export class CoreModule {

}
