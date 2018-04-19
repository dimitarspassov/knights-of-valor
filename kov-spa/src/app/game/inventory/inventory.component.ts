import {Component, OnInit} from '@angular/core';
import {HeroService} from '../hero.service';
import {NotificationService} from '../../core/notifications/notification.service';
import {AppConstants} from '../../app-constants';
import {HelperService} from '../../utils/helper.service';

@Component({
  selector: 'inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['../../app.component.scss', '../game.component.scss', './inventory.component.scss']
})
export class InventoryComponent implements OnInit {

  private battleSet = {};
  private items;
  private maxSize: number;

  constructor(private heroService: HeroService,
              private helperService: HelperService,
              private notificationService: NotificationService) {
  }


  ngOnInit(): void {
    this.reloadBattleSet();
    this.reloadInventory();
  }


  sellItem(itemId: string) {
    this.heroService.sellItem(itemId).subscribe(
      result => result => this.handleResponse(result),
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  equipItem(itemId: string) {
    this.heroService.equipItem(itemId).subscribe(
      result => this.handleResponse(result),
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  unequipItem(itemId: string) {
    this.heroService.unequipItem(itemId).subscribe(
      result => this.handleResponse(result),
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  private reloadInventory() {
    this.heroService.loadInventory().subscribe(
      result => {
        if (result.message) {
          this.notificationService.showError(result.message);
          return;
        }
        this.maxSize = result.size;
        this.items = result.items;
      },
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  private reloadBattleSet() {
    this.heroService.loadBattleSet().subscribe(
      result => {
        if (result.message) {
          this.notificationService.showError(result.message);
          return;
        }
        this.battleSet = result;
        console.log(this.battleSet);
      },
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  private handleResponse(result) {
    if (result.success) {
      this.notificationService.notify(result.message);
      this.heroService.doRefreshHero();
      this.reloadBattleSet();
      this.reloadInventory();
    } else {
      this.notificationService.showError(result.message);
    }
  }
}
