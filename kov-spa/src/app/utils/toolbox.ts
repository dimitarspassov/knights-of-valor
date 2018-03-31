export class Toolbox {

  static isInteger(val: string): boolean {
    return !!val.match(/^[\d]+$/);
  }

  /**
   * Check if the given number is an integer
   * with size between the passed MIN and MAX arguments
   *
   * @returns {boolean}
   */
  static isNumberBetween(num: number, min: number, max: number): boolean {

    return (num && num >= min &&
      num <= max &&
      Toolbox.isInteger(num.toString()));
  }

  /**
   * Check if the given string has length
   * between the passed MIN and MAX arguments
   *
   * * @returns {boolean}
   */
  static isContentLengthBetween(val: string, min: number, max: number): boolean {
    return val && val.trim().length >= min
      && val.trim().length <= max;
  }
}
