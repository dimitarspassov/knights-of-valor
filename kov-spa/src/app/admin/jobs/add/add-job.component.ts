import {Component} from '@angular/core';
import {AddJobModel} from './add-job.model';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';

@Component({
  selector: 'add-job',
  templateUrl: './add-job.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddJobComponent {

  readonly NAME_MIN_LENGTH = 3;
  readonly NAME_MAX_LENGTH = 40;
  readonly MINUTES_MIN = 60;
  readonly MINUTES_MAX = 600;
  readonly SALARY_MIN = 100;
  readonly SALARY_MAX = 1000000;

  job: AddJobModel = new AddJobModel();
  imageInvalid = false;

  onSubmit() {
    console.log(this.job);
  }

  isNameValid(name: string): boolean {

    if (name && name.trim().length >= this.NAME_MIN_LENGTH
      && name.trim().length <= this.NAME_MAX_LENGTH) {
      return true;
    }
    return false;
  }

  areMinutesValid(minutes: number): boolean {

    if (minutes && minutes >= this.MINUTES_MIN && minutes <= this.MINUTES_MAX
      && Toolbox.isInteger(minutes.toString())) {
      return true;
    }

    return false;

  }

  isSalaryValid(salary: number): boolean {

    if (salary && salary >= this.SALARY_MIN && salary < this.SALARY_MAX
      && Toolbox.isInteger(salary.toString())) {
      return true;
    }
    return false;
  }

  processImage(event): void {
    const file = event.srcElement.files[0];
    if (AdminConstants.ALLOWED_IMAGE_TYPES.indexOf(file.type) > -1) {
      this.job.image = file;
      this.imageInvalid = false;
      return;
    }
    this.imageInvalid = true;
  }

}
