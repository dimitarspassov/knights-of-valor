import {Component, OnInit} from '@angular/core';
import {AdminService} from '../admin.service';
import {NotificationService} from '../../core/notifications/notification.service';
import {AppConstants} from '../../app-constants';

@Component({
  selector: 'admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['../admin.component.scss']
})
export class AdminHomeComponent implements OnInit {

  private readonly USERS_PER_PAGE = 5;

  private page = 0;
  private admins;
  private allPages;

  private nextDisabled = false;
  private prevDisabled = true;

  constructor(private adminService: AdminService,
              private notificationService: NotificationService) {
    this.admins = [];
  }

  ngOnInit(): void {
    this.fetchAdmins(this.page);
  }


  private fetchAdmins(page) {
    this.adminService.getAdminUsersByPage(page, this.USERS_PER_PAGE)
      .subscribe(
        result => {
          this.admins = result.users;
          this.allPages = result.allPages;

          this.page = page;
          this.nextDisabled = this.page + 1 === this.allPages;

          this.prevDisabled = this.page <= 0;

        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  addAdmin(username) {
    this.adminService.makeAdmin(username)
      .subscribe(
        result => {

          if (result.success) {
            this.notificationService.notify(result.message);
            this.fetchAdmins(this.page);
          } else {
            this.notificationService.showError(result.message);
          }
        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  removeAdmin(username) {
    this.adminService.removeAdmin(username)
      .subscribe(
        result => {

          if (result.success) {
            this.notificationService.notify(result.message);
            this.fetchAdmins(this.page);
          } else {
            this.notificationService.showError(result.message);
          }
        },
        error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
      );
  }

  nextPage() {
    this.fetchAdmins(this.page + 1);
  }

  prevPage() {
    if (this.page !== 0) {
      this.fetchAdmins(this.page - 1);
    }
  }
}
