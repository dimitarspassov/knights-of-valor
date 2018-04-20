import {Component, OnInit} from '@angular/core';
import {NotificationService} from '../../core/notifications/notification.service';
import {HeroService} from '../hero.service';
import {GameService} from '../game.service';
import {AppConstants} from '../../app-constants';

@Component({
  selector: 'wilderness',
  templateUrl: './wilderness.component.html',
  styleUrls: ['../../app.component.scss', './wilderness.component.scss']
})
export class WildernessComponent implements OnInit {

  private page = 0;
  private readonly UNITS_PER_PAGE = 6;
  private units = [];
  private allPages;
  private nextDisabled = false;
  private prevDisabled = true;


  constructor(private notificationService: NotificationService,
              private heroService: HeroService,
              private gameService: GameService) {

  }

  ngOnInit(): void {
    this.fetchUnits(this.page);
  }


  private fetchUnits(page) {
    this.gameService.getWildernessUnitsByPage(page, this.UNITS_PER_PAGE)
      .subscribe(
        result => {

          if (result == null) {
            this.units = [];
          } else {
            this.units = result.units;
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
    this.fetchUnits(this.page + 1);
  }

  prevPage() {
    if (this.page !== 0) {
      this.fetchUnits(this.page - 1);
    }
  }

  fightWithNeutral(unitId: string) {
    this.heroService.fightNeutral(unitId).subscribe(
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
