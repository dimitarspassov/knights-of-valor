import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {NotificationComponent} from './notification/notification.component';
import {NotificationService} from './notification/notification.service';


@NgModule({
  imports: [BrowserModule],
  declarations: [NotificationComponent],
  providers: [NotificationService],
  exports: [NotificationComponent]
})
export class CoreModule {

}
