import {Component} from '@angular/core';
import {AddItemModel} from './add-item.model';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {NotificationService} from '../../../core/notifications/notification.service';
import {Router} from '@angular/router';
import {AppConstants} from '../../../app-constants';
import {ItemService} from '../item.service';

@Component({
  selector: 'add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddItemComponent {

  readonly NAME_MIN_LENGTH = AppConstants.ITEM_NAME_MIN_LENGTH;
  readonly NAME_MAX_LENGTH = AppConstants.ITEM_NAME_MAX_LENGTH;
  readonly BONUS_MIN = AppConstants.ITEM_BONUS_MIN;
  readonly BONUS_MAX = AppConstants.ITEM_BONUS_MAX;
  readonly PRICE_MIN = AppConstants.ITEM_PRICE_MIN;
  readonly PRICE_MAX = AppConstants.ITEM_PRICE_MAX;


  item: AddItemModel = new AddItemModel();
  imageInvalid = false;

  constructor(private itemService: ItemService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  onSubmit() {
    this.notificationService.loading();
    this.itemService.addNewItem(this.item)
      .subscribe(
        result => {
          if (result.success) {
            this.notificationService.notify(result.message);
            this.router.navigateByUrl('admin/items');
          } else {
            this.notificationService.showError(result.message);
          }

        },
        error => {
          this.notificationService.showError('An error occurred. Please, make sure the image size is below 1MB.');
        }
      );
  }

  isNameValid(name: string): boolean {
    return Toolbox.isContentLengthBetween(
      name, this.NAME_MIN_LENGTH, this.NAME_MAX_LENGTH);
  }

  isBonusValid(bonus: number): boolean {
    return Toolbox.isNumberBetween(bonus, this.BONUS_MIN, this.BONUS_MAX);
  }

  isPriceValid(price: number): boolean {
    return Toolbox.isNumberBetween(price, this.PRICE_MIN, this.PRICE_MAX);
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
