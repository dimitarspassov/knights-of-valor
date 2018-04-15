import {Component} from '@angular/core';
import {AddJobModel} from './add-job.model';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {AppConstants} from '../../../app-constants';
import {NotificationService} from '../../../core/notifications/notification.service';
import {Router} from '@angular/router';
import {JobService} from '../job.service';

@Component({
  selector: 'add-job',
  templateUrl: './add-job.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class AddJobComponent {

  readonly NAME_MIN_LENGTH = AppConstants.JOB_NAME_MIN_LENGTH;
  readonly NAME_MAX_LENGTH = AppConstants.JOB_NAME_MAX_LENGTH;
  readonly MINUTES_MIN = AppConstants.JOB_MINUTES_MIN;
  readonly MINUTES_MAX = AppConstants.JOB_MINUTES_MAX;
  readonly SALARY_MIN = AppConstants.JOB_SALARY_MIN;
  readonly SALARY_MAX = AppConstants.JOB_SALARY_MAX;

  job: AddJobModel = new AddJobModel();
  imageInvalid = false;

  constructor(private jobService: JobService,
              private notificationService: NotificationService,
              private router: Router) {
  }

  onSubmit() {
    this.notificationService.loading();
    this.jobService.addNewJob(this.job)
      .subscribe(
        result => {
          if (result.success) {
            this.notificationService.notify(result.message);
            this.router.navigateByUrl('admin/jobs');
          } else {
            this.notificationService.showError(result.message);
          }

        },
        error => {
          this.notificationService.showError(AppConstants.GENERIC_UPLOAD_ERROR_MESSAGE);
        }
      );
  }

  isNameValid(name: string): boolean {
    return Toolbox.isContentLengthBetween(name, this.NAME_MIN_LENGTH, this.NAME_MAX_LENGTH);
  }

  areMinutesValid(minutes: number): boolean {
    return Toolbox.isNumberBetween(minutes, this.MINUTES_MIN, this.MINUTES_MAX);
  }

  isSalaryValid(salary: number): boolean {
    return Toolbox.isNumberBetween(salary, this.SALARY_MIN, this.SALARY_MAX);
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
