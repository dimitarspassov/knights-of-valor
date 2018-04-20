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

  /**
   * Format minutes to time
   *
   * @param minutes {number}
   */
  minutesToTime(minutes) {
    let h = '0' + (Math.trunc(minutes / 60));
    let m = '0' + (minutes % 60);

    return `${h.substr(-2)}:${m.substr(-2)}`;
  }

  /**
   * Format minutes to hours:minutes:seconds
   *
   * @param seconds {number}
   */
  minutesToTimeWithSeconds(seconds) {
    let s = '0' + (seconds % 60);
    let val = this.minutesToTime(Math.trunc(seconds / 60)) + `:${s.substr(-2)}`;
    return val;
  }
}
