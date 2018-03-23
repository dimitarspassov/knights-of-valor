import {Component, OnInit} from '@angular/core';
import {NotificationService} from '../../core/notification/notification.service';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['../index.component.scss', './register.component.scss']
})
export class RegisterComponent{

  constructor(private notificationService: NotificationService) {
  }
}
