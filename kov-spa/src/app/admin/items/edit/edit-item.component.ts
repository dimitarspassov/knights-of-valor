import {Component, OnInit} from '@angular/core';
import {EditItemModel} from './edit-item.model';
import {ActivatedRoute, Router} from '@angular/router';
import {AdminService} from '../../admin.service';
import {NotificationService} from '../../../core/notifications/notification.service';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {AppConstants} from '../../../app-constants';

@Component({
  selector: 'edit-item',
  templateUrl: './edit-item.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class EditItemComponent implements OnInit {

  private readonly id;
  private item: EditItemModel = new EditItemModel();
  imageInvalid = false;

  constructor(private router: Router,
              private notificationService: NotificationService,
              private activatedRoute: ActivatedRoute,
              private adminService: AdminService) {
    const params: any = this.activatedRoute.snapshot.params;
    this.id = params.id;
  }

  ngOnInit() {
    if (this.id !== undefined) {
      this.adminService.getItemById(this.id)
        .subscribe(
          result => this.mapItem(result),
          error => {
            this.notificationService.showError('An error occured. Please try again.');
            this.router.navigateByUrl('admin/items');
          }
        );
    }
  }

  onSubmit() {
    this.adminService.editItem(this.id, this.item)
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

  private mapItem(item) {
    this.item.oldImage = item.image;
    this.item.name = item.name;
    this.item.bonus = item.bonus;
    this.item.type = item.type;
    this.item.price = item.price;
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
