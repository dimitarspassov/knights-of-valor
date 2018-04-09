import {Component} from '@angular/core';
import {AddItemModel} from './add-item.model';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {AdminService} from '../../admin.service';
import {NotificationService} from '../../../core/notifications/notification.service';
import {Router} from '@angular/router';
import {AppConstants} from '../../../app-constants';

@Component({
  selector: 'add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddItemComponent {

  item: AddItemModel = new AddItemModel();
  imageInvalid = false;

  constructor(private adminService: AdminService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  onSubmit() {
    this.adminService.addNewItem(this.item)
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
      name, AppConstants.ITEM_NAME_MIN_LENGTH, AppConstants.ITEM_NAME_MAX_LENGTH);
  }

  isBonusValid(bonus: number): boolean {
    return Toolbox.isNumberBetween(bonus, AppConstants.ITEM_BONUS_MIN, AppConstants.ITEM_BONUS_MAX);
  }

  isPriceValid(price: number): boolean {
    return Toolbox.isNumberBetween(price, AppConstants.ITEM_PRICE_MIN, AppConstants.ITEM_PRICE_MAX);
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
