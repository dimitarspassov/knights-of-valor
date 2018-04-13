import {Injectable} from '@angular/core';
import {HttpService} from '../../core/http.service';
import {AddNeutralModel} from './add/add-neutral.model';
import {EditNeutralModel} from './edit/edit-neutral.model';


@Injectable()
export class NeutralUnitService {

  private readonly ADD_UNIT_URL = 'api/admin/neutrals/add';
  private readonly GET_UNIT_URL = 'api/admin/neutrals/';
  private readonly EDIT_UNIT_IMG_URL = 'api/admin/neutrals/newimg/';


  constructor(private httpService: HttpService) {
  }

  addNewUnit(neutral: AddNeutralModel) {
    const formData = new FormData();
    formData.append('image', neutral.image, neutral.image.name);
    formData.append('unit', new Blob([JSON.stringify({
      'name': neutral.name,
      'level': neutral.level,
      'health': neutral.health,
      'strength': neutral.strength,
      'stamina': neutral.stamina,
      'defense': neutral.defense,
      'lootGold': neutral.loot,
      'type': neutral.type,
    })], {
      type: 'application/json'
    }));


    return this.httpService.post(this.ADD_UNIT_URL, formData, true, true);
  }

  getUnitById(id: string) {
    return this.httpService.get(this.GET_UNIT_URL + id, true);
  }


  editUnit(id: string, neutral: EditNeutralModel) {

    if (neutral.image !== undefined) {
      const formData = new FormData();
      formData.append('image', neutral.image, neutral.image.name);
      formData.append('unit', new Blob([JSON.stringify({
        'name': neutral.name,
        'level': neutral.level,
        'health': neutral.health,
        'strength': neutral.strength,
        'stamina': neutral.stamina,
        'defense': neutral.defense,
        'lootGold': neutral.lootGold,
        'type': neutral.type,
      })], {
        type: 'application/json'
      }));

      return this.httpService.post(this.EDIT_UNIT_IMG_URL + id, formData, true, true);
    } else {
      return this.httpService.post(this.GET_UNIT_URL + id, neutral, true);
    }

  }


  changeUnitStatus(itemId: string, status: boolean) {
    return this.httpService.post(`${this.GET_UNIT_URL}${itemId}/status/${status}`, null, true);
  }
}
