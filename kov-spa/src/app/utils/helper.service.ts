import {Injectable} from '@angular/core';

@Injectable()
export class HelperService {

  /**
   * Calculate the stamina of a given item
   *
   * @param item
   * @returns {number}
   */
  getStaminaOfItem(item) {

    return Math.trunc(item.bonus / 3);
  }


  /**
   * Calculate the price of a given item
   * in the hero inventory
   * @param item
   * @returns {number}
   */
  getPriceOfUsedItem(item) {
    return Math.trunc(item.price / 3);
  }
}
