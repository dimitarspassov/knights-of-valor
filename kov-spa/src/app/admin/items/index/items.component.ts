import {Component, OnInit} from '@angular/core';
import {GameService} from '../../../game/game.service';
import {NotificationService} from '../../../core/notifications/notification.service';

@Component({
  selector: 'items',
  templateUrl: './items.component.html',
  styleUrls: ['../../admin.component.scss']
})
export class ItemsComponent implements OnInit {

  private items;

  constructor(private gameService: GameService,
              private notificationService: NotificationService) {
    this.items = [];
  }

  ngOnInit(): void {
    this.gameService.getAllItems()
      .subscribe(
        result => this.items = result,
        error => this.notificationService.showError('An error occured. Please, try again.')
      );
  }


}
