import {Component, OnInit} from '@angular/core';
import {NotificationService} from './notification.service';

@Component({
  selector: 'notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss']
})
export class NotificationComponent implements OnInit {

  private showNotification: boolean;
  private message: string;
  private notificationTypeClass: string;

  constructor(private notificationService: NotificationService) {
  }

  ngOnInit(): void {
    this.showNotification = false;

    this.notificationService.messageUpdated.subscribe(
      (message) => {
        this.message = message;
        this.showNotification = true;
        this.notificationTypeClass = 'alert-primary';
      }
    );

    this.notificationService.errorUpdated.subscribe(
      (message) => {
        this.message = message;
        this.showNotification = true;
        this.notificationTypeClass = 'alert-danger';
      }
    );

  }

  closeNotification(): void {
    this.showNotification = false;
  }
}
