import {Component, OnInit} from '@angular/core';
import {GameService} from '../../../game/game.service';
import {NotificationService} from '../../../core/notifications/notification.service';
import {AdminService} from '../../admin.service';

@Component({
  selector: 'items',
  templateUrl: './items.component.html',
  styleUrls: ['../../admin.component.scss']
})
export class ItemsComponent implements OnInit {

  private readonly ITEMS_PER_PAGE = 5;

  private page = 0;
  private items;
  private allPages;

  private nextDisabled = false;
  private prevDisabled = true;

  constructor(private adminService: AdminService,
              private gameService: GameService,
              private notificationService: NotificationService) {
    this.items = [];
  }

  ngOnInit(): void {
    this.fetchItems(this.page);
  }

  private fetchItems(page) {
    this.gameService.getItemsByPage(page, this.ITEMS_PER_PAGE)
      .subscribe(
        result => {
          this.items = result.items;
          this.allPages = result.allPages;

          this.page = page;
          this.nextDisabled = this.page + 1 === this.allPages;

          this.prevDisabled = this.page <= 0;

        },
        error => this.notificationService.showError('An error occured. Please, try again.')
      );
  }

  nextPage() {
    this.fetchItems(this.page + 1);
  }

  prevPage() {
    if (this.page !== 0) {
      this.fetchItems(this.page - 1);
    }
  }

  changeStatus(item, status) {
    this.adminService.changeItemStatus(item, status)
      .subscribe(
        result => {
          this.notificationService.notify(result.message);
          this.fetchItems(this.page);
        },
        error => this.notificationService.showError('An error occurred. Please, try again')
      );
  }


}
