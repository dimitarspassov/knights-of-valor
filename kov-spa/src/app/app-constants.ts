export class AppConstants {

  static readonly HERO_NAME_MIN_LENGTH: number = 3;
  static readonly HERO_NAME_MAX_LENGTH: number = 30;


  static readonly ITEM_NAME_MIN_LENGTH = 3;
  static readonly ITEM_NAME_MAX_LENGTH = 40;
  static readonly ITEM_BONUS_MIN = 1;
  static readonly ITEM_BONUS_MAX = 100000;
  static readonly ITEM_PRICE_MIN = 1;
  static readonly ITEM_PRICE_MAX = 1000000;

  static readonly UNIT_NAME_MIN_LENGTH = 3;
  static readonly UNIT_NAME_MAX_LENGTH = 40;
  static readonly UNIT_LEVEL_MIN = 1;
  static readonly UNIT_LEVEL_MAX = 10000;
  static readonly UNIT_HEALTH_MIN = 10;
  static readonly UNIT_HEALTH_MAX = 1000000;

  static readonly JOB_NAME_MIN_LENGTH = 3;
  static readonly JOB_NAME_MAX_LENGTH = 40;
  static readonly JOB_MINUTES_MIN = 60;
  static readonly JOB_MINUTES_MAX = 600;
  static readonly JOB_SALARY_MIN = 100;
  static readonly JOB_SALARY_MAX = 1000000;

  static readonly GENERIC_ERROR_MESSAGE = 'An error occurred. Please, try again.';
  static readonly GENERIC_UPLOAD_ERROR_MESSAGE = 'An error occurred. Please, make sure the image, you are trying to upload is less than 3MB.';

  private constructor() {

  }
}
