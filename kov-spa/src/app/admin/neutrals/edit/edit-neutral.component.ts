import {Component, OnInit} from '@angular/core';
import {NeutralUnitService} from '../neutral-unit.service';
import {NotificationService} from '../../../core/notifications/notification.service';

import {ActivatedRoute, Router} from '@angular/router';
import {EditNeutralModel} from './edit-neutral.model';
import {AppConstants} from '../../../app-constants';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';

@Component({
  selector: 'edit-neutral',
  templateUrl: './edit-neutral.component.html',
  styleUrls: ['../../admin.component.scss']
})
export class EditNeutralComponent implements OnInit {

  readonly NAME_MIN_LENGTH = AppConstants.UNIT_NAME_MIN_LENGTH;
  readonly NAME_MAX_LENGTH = AppConstants.UNIT_NAME_MAX_LENGTH;
  readonly LEVEL_MIN = AppConstants.UNIT_LEVEL_MIN;
  readonly LEVEL_MAX = AppConstants.UNIT_LEVEL_MAX;
  readonly HEALTH_MIN = AppConstants.UNIT_HEALTH_MIN;
  readonly HEALTH_MAX = AppConstants.UNIT_HEALTH_MAX;

  // correspond to strength, stamina and defense
  readonly STATS_MIN = 1;
  readonly STATS_MAX = 1000000;

  readonly LOOT_MIN = 1;
  readonly LOOT_MAX = 400000;

  private readonly id;
  private neutral: EditNeutralModel = new EditNeutralModel();
  imageInvalid = false;

  constructor(private unitService: NeutralUnitService,
              private notificationService: NotificationService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
    const params: any = this.activatedRoute.snapshot.params;
    this.id = params.id;
  }

  ngOnInit() {
    if (this.id !== undefined) {
      this.unitService.getUnitById(this.id)
        .subscribe(
          result => this.mapItem(result),
          error => {
            this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE);
            this.router.navigateByUrl('admin/items');
          }
        );
    }
  }

  onSubmit() {
    this.notificationService.loading();
    this.unitService.editUnit(this.id, this.neutral)
      .subscribe(
        result => {
          if (result.success) {
            this.notificationService.notify(result.message);
            this.router.navigateByUrl('admin/neutrals');
          } else {
            this.notificationService.showError(result.message);
          }
        },
        error => {
          this.notificationService.showError(error.message);
        }
      );
  }

  private mapItem(neutral) {
    this.neutral.oldImage = neutral.image;
    this.neutral.name = neutral.name;
    this.neutral.type = neutral.type;
    this.neutral.level = neutral.level;
    this.neutral.strength = neutral.strength;
    this.neutral.defense = neutral.defense;
    this.neutral.stamina = neutral.stamina;
    this.neutral.health = neutral.health;
    this.neutral.lootGold = neutral.lootGold;
  }

  isNameValid(name: string): boolean {

    return Toolbox.isContentLengthBetween(name, this.NAME_MIN_LENGTH, this.NAME_MAX_LENGTH);
  }

  isLevelValid(level: number): boolean {

    return Toolbox.isNumberBetween(level, this.LEVEL_MIN, this.LEVEL_MAX);
  }

  isHealthValid(health: number): boolean {

    return Toolbox.isNumberBetween(health, this.HEALTH_MIN, this.HEALTH_MAX);
  }

  isStatValid(stat: number): boolean {

    return Toolbox.isNumberBetween(stat, this.STATS_MIN, this.STATS_MAX);
  }

  isLootValid(loot: number): boolean {

    return Toolbox.isNumberBetween(loot, this.LOOT_MIN, this.LOOT_MAX);
  }


  processImage(event): void {
    const file = event.srcElement.files[0];
    if (AdminConstants.ALLOWED_IMAGE_TYPES.indexOf(file.type) > -1) {
      this.neutral.image = file;
      this.imageInvalid = false;
      return;
    }
    this.imageInvalid = true;
  }
}
