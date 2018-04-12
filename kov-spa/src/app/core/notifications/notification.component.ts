import {Component, OnInit} from '@angular/core';
import {NotificationService} from './notification.service';

@Component({
  selector: 'notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  private readonly NOTIFICATION_PRIMARY = 'alert-primary';
  private readonly NOTIFICATION_ERROR = 'alert-danger';
  private readonly NOTIFICATION_LOADING = 'alert-light';

  private showNotification: boolean;
  private message: string;
  private notificationTypeClass: string;

  constructor(private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.showNotification = false;

    this.notificationService.messageUpdated.subscribe(
      (message) => {
        this.show(message, this.NOTIFICATION_PRIMARY);
      }
    );

    this.notificationService.errorUpdated.subscribe(
      (message) => {
        this.show(message, this.NOTIFICATION_ERROR);
      }
    );

    this.notificationService.loadingUpdated.subscribe(
      () => {
        this.show('', this.NOTIFICATION_LOADING);
      }
    );

  }

  closeNotification(): void {
    this.notificationTypeClass = this.notificationTypeClass
      .replace('visible', '');
    setTimeout(() => {
      this.showNotification = false;
    }, 200);

  }

  private show(message: string, notificationClass: string) {
    this.message = message;
    this.showNotification = true;
    this.notificationTypeClass = notificationClass;
    setTimeout(() => {
      this.notificationTypeClass += ' visible';
    }, 100);
  }

}
