import {HttpService} from '../core/http.service';
import {Injectable} from '@angular/core';
import {AddItemModel} from './items/add/add-item.model';
import {EditItemModel} from './items/edit/edit-item.model';

@Injectable()
export class AdminService {


  private readonly ADD_ITEM_URL = 'api/admin/items/add';
  private readonly GET_ITEM_URL = 'api/admin/items/';
  private readonly EDIT_ITEM_IMG_URL = 'api/admin/items/newimg/';
  private readonly GET_ADMINS_URL = 'api/superadmin/users';
  private readonly ADD_ADMIN_URL = 'api/superadmin/new-admin/';
  private readonly REMOVE_ADMIN_URL = 'api/superadmin/remove-admin/';

  constructor(private httpService: HttpService) {
  }

  addNewItem(item: AddItemModel) {
    const formData = new FormData();
    formData.append('image', item.image, item.image.name);
    formData.append('item', new Blob([JSON.stringify({
      'name': item.name,
      'type': item.type,
      'price': item.price,
      'bonus': item.bonus
    })], {
      type: 'application/json'
    }));


    return this.httpService.post(this.ADD_ITEM_URL, formData, true, true);
  }

  getItemById(id: string) {
    return this.httpService.get(this.GET_ITEM_URL + id, true);
  }

  editItem(id: string, item: EditItemModel) {

    if (item.image !== undefined) {

      const formData = new FormData();
      formData.append('image', item.image, item.image.name);
      formData.append('item', new Blob([JSON.stringify({
        'name': item.name,
        'type': item.type,
        'price': item.price,
        'bonus': item.bonus,
      })], {
        type: 'application/json'
      }));

      return this.httpService.post(this.EDIT_ITEM_IMG_URL + id, formData, true, true);
    } else {
      return this.httpService.post(this.GET_ITEM_URL + id, item, true);
    }

  }

  changeItemStatus(itemId: string, status: boolean) {
    return this.httpService.post(`${this.GET_ITEM_URL}${itemId}/status/${status}`, null, true);
  }

  getAdminUsersByPage(page: number, size: number) {
    return this.httpService.get(
      this.GET_ADMINS_URL + `/?page=${page}&size=${size}`,
      true);
  }

  makeAdmin(username: string) {
    return this.httpService.post(this.ADD_ADMIN_URL + username, null, true);
  }

  removeAdmin(username: string) {
    return this.httpService.post(this.REMOVE_ADMIN_URL + username, null, true);
  }

}
