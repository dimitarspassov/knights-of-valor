import {Component, OnInit} from '@angular/core';
import {GameService} from '../../../game/game.service';
import {NotificationService} from '../../../core/notifications/notification.service';
import {JobService} from '../job.service';
import {AppConstants} from '../../../app-constants';

@Component({
  selector: 'jobs',
  templateUrl: './jobs.component.html',
  styleUrls: ['../../admin.component.scss']
})
export class JobsComponent implements OnInit {

  private readonly JOBS_PER_PAGE = 5;

  private page = 0;
  private jobs;
  private allPages;

  private nextDisabled = false;
  private prevDisabled = true;

  constructor(private jobService: JobService,
              private gameService: GameService,
              private notificationService: NotificationService) {
    this.jobs = [];
  }

  ngOnInit(): void {
    this.fetchJobs(this.page);
  }

  private fetchJobs(page) {
    this.gameService.getJobsByPage(page, this.JOBS_PER_PAGE)
      .subscribe(
        result => {
          this.jobs = result.jobs;
          this.allPages = result.allPages;

          this.page = page;
          this.nextDisabled = this.page + 1 === this.allPages;

          this.prevDisabled = this.page <= 0;

        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  nextPage() {
    this.fetchJobs(this.page + 1);
  }

  prevPage() {
    if (this.page !== 0) {
      this.fetchJobs(this.page - 1);
    }
  }

  changeStatus(job, status) {
    this.jobService.changeJobStatus(job, status)
      .subscribe(
        result => {
          this.notificationService.notify(result.message);
          this.fetchJobs(this.page);
        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  minutesToHours(inputMinutes: number): string {
    const hours = '0' + Math.trunc((inputMinutes / 60));
    const minutes = '0' + Math.trunc((inputMinutes % 60));

    return `${hours.slice(-2)}:${minutes.slice(-2)}`;

  }
}
