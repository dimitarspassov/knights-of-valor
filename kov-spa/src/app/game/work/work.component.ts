import {Component, OnInit} from '@angular/core';
import {NotificationService} from '../../core/notifications/notification.service';
import {HeroService} from '../hero.service';
import {GameService} from '../game.service';
import {AppConstants} from '../../app-constants';
import {HelperService} from '../../utils/helper.service';

@Component({
  selector: 'work',
  templateUrl: './work.component.html',
  styleUrls: ['../../app.component.scss', './work.component.scss']
})
export class WorkComponent implements OnInit {

  private readonly JOBS_PER_PAGE = 6;
  private isAtWork = false;
  private page = 0;
  private jobs = [];
  private allPages;
  private currentJob = {};
  private currentJobId;
  private secondsRemaining: number;
  private nextDisabled = false;
  private prevDisabled = true;

  constructor(private notificationService: NotificationService,
              private heroService: HeroService,
              private gameService: GameService,
              private helperService: HelperService) {
  }

  ngOnInit() {
    this.isHeroAtWork();
  }

  nextPage() {
    this.fetchJobs(this.page + 1);
  }

  prevPage() {
    if (this.page !== 0) {
      this.fetchJobs(this.page - 1);
    }
  }

  startJob(jobId: string) {
    this.heroService.startJob(jobId).subscribe(
      result => this.handleResponse(result),
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }


  abandonJob() {
    this.heroService.abandonJob(this.currentJobId).subscribe(
      result => this.handleResponse(result),
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  private handleResponse(result) {
    if (result.success) {
      this.notificationService.notify(result.message);
      this.isHeroAtWork();

    } else {
      this.notificationService.showError(result.message);
    }
  }

  private fetchJobs(page) {
    this.gameService.getJobsByPage(page, this.JOBS_PER_PAGE)
      .subscribe(
        result => {

          if (result == null) {
            this.jobs = [];
          } else {
            this.jobs = result.jobs;
            this.allPages = result.allPages;
          }

          this.page = page;
          this.nextDisabled = this.page + 1 >= this.allPages;
          this.prevDisabled = this.page <= 0;

        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  private isHeroAtWork() {
    this.heroService.isHeroAtWork().subscribe(
      result => {

        this.isAtWork = result.id !== undefined;

        if (!this.isAtWork) {
          this.fetchJobs(this.page);
          this.heroService.doRefreshHero();
        } else {
          this.currentJob = result.job;
          this.secondsRemaining = result.secondsRemaining;
          this.currentJobId = result.id;
          this.timer();
        }

      },
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  private timer() {
    let interval = setInterval(() => {

      if (this.secondsRemaining <= 0) {
        this.secondsRemaining++;
        clearInterval(interval);
        this.finishWork();
      }
      this.secondsRemaining--;
    }, 1000);
  }

  private finishWork() {
    this.heroService.finishJob(this.currentJobId)
      .subscribe(
        result => this.handleResponse(result),
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

}
