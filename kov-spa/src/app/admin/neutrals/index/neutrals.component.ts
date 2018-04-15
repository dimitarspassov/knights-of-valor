import {Component, OnInit} from '@angular/core';
import {GameService} from '../../../game/game.service';
import {NotificationService} from '../../../core/notifications/notification.service';
import {NeutralUnitService} from '../neutral-unit.service';
import {AppConstants} from '../../../app-constants';

@Component({
  selector: 'neutrals',
  templateUrl: './neutrals.component.html',
  styleUrls: ['../../admin.component.scss']
})
export class NeutralsComponent implements OnInit {

  private readonly UNITS_PER_PAGE = 5;

  private page = 0;
  private units;
  private allPages;

  private nextDisabled = false;
  private prevDisabled = true;

  constructor(private unitService: NeutralUnitService,
              private gameService: GameService,
              private notificationService: NotificationService) {
    this.units = [];
  }

  ngOnInit(): void {
    this.fetchUnits(this.page);
  }

  private fetchUnits(page) {
    this.gameService.getUnitsByPage(page, this.UNITS_PER_PAGE)
      .subscribe(
        result => {
          this.units = result.units;
          this.allPages = result.allPages;

          this.page = page;
          this.nextDisabled = this.page + 1 === this.allPages;

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

  changeStatus(unit, status) {
    this.unitService.changeUnitStatus(unit, status)
      .subscribe(
        result => {
          this.notificationService.notify(result.message);
          this.fetchUnits(this.page);
        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

}
