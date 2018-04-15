import {Component, OnInit} from '@angular/core';
import {NotificationService} from '../../../core/notifications/notification.service';
import {AppConstants} from '../../../app-constants';
import {JobService} from '../job.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AdminConstants} from '../../admin-constants';
import {Toolbox} from '../../../utils/toolbox';
import {EditJobModel} from './edit-job.model';

@Component({
  selector: 'edit-job',
  templateUrl: './edit-job.component.html',
  styleUrls: ['../../../app.component.scss', '../../admin.component.scss']
})
export class EditJobComponent implements OnInit {

  readonly NAME_MIN_LENGTH = AppConstants.JOB_NAME_MIN_LENGTH;
  readonly NAME_MAX_LENGTH = AppConstants.JOB_NAME_MAX_LENGTH;
  readonly MINUTES_MIN = AppConstants.JOB_MINUTES_MIN;
  readonly MINUTES_MAX = AppConstants.JOB_MINUTES_MAX;
  readonly SALARY_MIN = AppConstants.JOB_SALARY_MIN;
  readonly SALARY_MAX = AppConstants.JOB_SALARY_MAX;

  private readonly id;
  job: EditJobModel = new EditJobModel();
  imageInvalid = false;

  constructor(private jobService: JobService,
              private notificationService: NotificationService,
              private activatedRoute: ActivatedRoute,
              private router: Router) {
    const params: any = this.activatedRoute.snapshot.params;
    this.id = params.id;
  }

  ngOnInit() {
    if (this.id !== undefined) {
      this.jobService.getJobById(this.id)
        .subscribe(
          result => this.mapJob(result),
          error => {
            this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE);
            this.router.navigateByUrl('admin/jobs');
          }
        );
    }
  }

  onSubmit() {
    this.notificationService.loading();
    this.jobService.editJob(this.id, this.job)
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


  private mapJob(job) {
    this.job.name = job.name;
    this.job.salary = job.salary;
    this.job.minutes = job.minutes;
    this.job.oldImage = job.image;
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
