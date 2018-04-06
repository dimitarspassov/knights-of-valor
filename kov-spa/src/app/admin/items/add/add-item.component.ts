import {Component} from '@angular/core';
import {AddItemModel} from './add-item.model';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';

@Component({
  selector: 'add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddItemComponent {

  readonly NAME_MIN_LENGTH = 3;
  readonly NAME_MAX_LENGTH = 40;
  readonly BONUS_MIN = 1;
  readonly BONUS_MAX = 100000;
  readonly PRICE_MIN = 1;
  readonly PRICE_MAX = 1000000;

  item: AddItemModel = new AddItemModel();
  imageInvalid = false;

  onSubmit() {
    console.log(this.item);
  }

  isNameValid(name: string): boolean {

    if (name && name.trim().length >= this.NAME_MIN_LENGTH
      && name.trim().length <= this.NAME_MAX_LENGTH) {
      return true;
    }
    return false;
  }

  isBonusValid(bonus: number): boolean {

    if (bonus && bonus >= this.BONUS_MIN && bonus <= this.BONUS_MAX
      && Toolbox.isInteger(bonus.toString())) {
      return true;
    }

    return false;

  }

  isPriceValid(price: number): boolean {

    if (price && price >= this.PRICE_MIN && price < this.PRICE_MAX
      && Toolbox.isInteger(price.toString())) {
      return true;
    }
    return false;
  }

  processImage(event): void {
    const file = event.srcElement.files[0];
    if (AdminConstants.ALLOWED_IMAGE_TYPES.indexOf(file.type) > -1) {
      this.item.image = file;
      this.imageInvalid = false;
      return;
    }
    this.imageInvalid = true;
  }
}
