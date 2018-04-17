import {Component, EventEmitter, OnInit, Output, SecurityContext} from '@angular/core';
import {NotificationService} from '../../core/notifications/notification.service';
import {GameService} from '../game.service';
import {AppConstants} from '../../app-constants';
import {DomSanitizer} from '@angular/platform-browser';
import {HeroService} from '../hero.service';

@Component({
  selector: 'market',
  templateUrl: './market.component.html',
  styleUrls: ['../../app.component.scss', './market.component.scss']
})
export class MarketComponent implements OnInit {

  private page = 0;
  private readonly ITEMS_PER_PAGE = 6;
  private items = [];
  private allPages;

  private currentQuery: string;

  private nextDisabled = false;
  private prevDisabled = true;


  constructor(private notificationService: NotificationService,
              private gameService: GameService,
              private heroService: HeroService,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit(): void {
    this.fetchItems(this.page, this.currentQuery);
  }

  getStaminaOfItem(item) {

    return Math.trunc(item.bonus / 3);
  }

  private fetchItems(page, query) {
    this.gameService.getItemsByPage(page, this.ITEMS_PER_PAGE, query)
      .subscribe(
        result => {

          if (result == null) {
            this.items = [];
          } else {
            this.items = result.items;
            this.allPages = result.allPages;
          }

          this.page = page;
          this.nextDisabled = this.page + 1 >= this.allPages;

          this.prevDisabled = this.page <= 0;

        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  nextPage() {
    this.fetchItems(this.page + 1, this.currentQuery);
  }

  prevPage() {
    if (this.page !== 0) {
      this.fetchItems(this.page - 1, this.currentQuery);
    }
  }

  findItemsByType(type) {
    const sanitizedQuery = this.sanitizer.sanitize(SecurityContext.HTML, type);

    if (sanitizedQuery !== this.currentQuery) {
      this.currentQuery = sanitizedQuery;
      this.page = 0;
    }

    this.fetchItems(this.page, this.currentQuery);
  }

  buyItem(itemId) {
    this.heroService.buyItem(itemId).subscribe(
      result => {
        if (result.success) {
          this.notificationService.notify(result.message);
          this.heroService.doRefreshHero();
        } else {
          this.notificationService.showError(result.message);
        }
      },
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }
}
