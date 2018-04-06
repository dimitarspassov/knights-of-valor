import {Component} from '@angular/core';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {AddNeutralModel} from './add-neutral.model';

@Component({
  selector: 'add-neutral',
  templateUrl: './add-neutral.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddNeutralComponent {

  readonly NAME_MIN_LENGTH = 3;
  readonly NAME_MAX_LENGTH = 40;
  readonly LEVEL_MIN = 1;
  readonly LEVEL_MAX = 10000;
  readonly HEALTH_MIN = 10;
  readonly HEALTH_MAX = 1000000;

  // correspond to strength, stamina and defense
  readonly STATS_MIN = 1;
  readonly STATS_MAX = 1000000;

  readonly LOOT_MIN = 1;
  readonly LOOT_MAX = 400000;

  neutral: AddNeutralModel = new AddNeutralModel();
  imageInvalid = false;

  onSubmit() {
    console.log(this.neutral);
  }

  isNameValid(name: string): boolean {

    if (name && name.trim().length >= this.NAME_MIN_LENGTH
      && name.trim().length <= this.NAME_MAX_LENGTH) {
      return true;
    }
    return false;
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
