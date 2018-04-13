import {Component, OnInit} from '@angular/core';
import {EditItemModel} from './edit-item.model';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationService} from '../../../core/notifications/notification.service';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {AppConstants} from '../../../app-constants';
import {ItemService} from '../item.service';

@Component({
  selector: 'edit-item',
  templateUrl: './edit-item.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class EditItemComponent implements OnInit {

  readonly NAME_MIN_LENGTH = AppConstants.ITEM_NAME_MIN_LENGTH;
  readonly NAME_MAX_LENGTH = AppConstants.ITEM_NAME_MAX_LENGTH;
  readonly BONUS_MIN = AppConstants.ITEM_BONUS_MIN;
  readonly BONUS_MAX = AppConstants.ITEM_BONUS_MAX;
  readonly PRICE_MIN = AppConstants.ITEM_PRICE_MIN;
  readonly PRICE_MAX = AppConstants.ITEM_PRICE_MAX;

  private readonly id;
  private item: EditItemModel = new EditItemModel();
  imageInvalid = false;

  constructor(private router: Router,
              private notificationService: NotificationService,
              private activatedRoute: ActivatedRoute,
              private itemService: ItemService) {
    const params: any = this.activatedRoute.snapshot.params;
    this.id = params.id;
  }

  ngOnInit() {
    if (this.id !== undefined) {
      this.itemService.getItemById(this.id)
        .subscribe(
          result => this.mapItem(result),
          error => {
            this.notificationService.showError('An error occurred. Please try again.');
            this.router.navigateByUrl('admin/items');
          }
        );
    }
  }

  onSubmit() {
    this.notificationService.loading();
    this.itemService.editItem(this.id, this.item)
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
          this.notificationService.showError(error.message);
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
