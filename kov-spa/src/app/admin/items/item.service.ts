import {Injectable} from '@angular/core';
import {HttpService} from '../../core/http.service';
import {EditItemModel} from './edit/edit-item.model';
import {AddItemModel} from './add/add-item.model';

@Injectable()
export class ItemService {

  private readonly ADD_ITEM_URL = 'api/admin/items/add';
  private readonly GET_ITEM_URL = 'api/admin/items/';
  private readonly ALL_ITEMS_URL = 'api/admin/items';
  private readonly EDIT_ITEM_IMG_URL = 'api/admin/items/newimg/';


  constructor(private httpService: HttpService) {
  }

  getAllItemsByPage(page, size, query = null) {

    let urlQuery = `/?page=${page}&size=${size}`;
    const url = this.ALL_ITEMS_URL + urlQuery;

    return this.httpService.get(url, true);
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
}
