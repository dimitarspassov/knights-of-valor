import {Component} from '@angular/core';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {AddNeutralModel} from './add-neutral.model';
import {NotificationService} from '../../../core/notifications/notification.service';
import {NeutralUnitService} from '../neutral-unit.service';
import {Router} from '@angular/router';
import {AppConstants} from '../../../app-constants';

@Component({
  selector: 'add-neutral',
  templateUrl: './add-neutral.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddNeutralComponent {

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


  neutral: AddNeutralModel = new AddNeutralModel();
  imageInvalid = false;

  constructor(private unitService: NeutralUnitService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  onSubmit() {
    this.notificationService.loading();
    this.unitService.addNewUnit(this.neutral)
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
