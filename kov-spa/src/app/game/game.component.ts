import {Component, OnInit} from '@angular/core';
import {UserService} from '../index/user.service';
import {Router} from '@angular/router';
import {HeroService} from './hero.service';
import {NotificationService} from '../core/notifications/notification.service';
import {AppConstants} from '../app-constants';
import {HeroModel} from './hero.model';

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['../app.component.scss', './game.component.scss']
})
export class GameComponent implements OnInit {

  private navOpened: boolean;
  private navClass: string;

  isAdmin = false;
  isSuperAdmin = false;

  currentHero: HeroModel;

  constructor(private userService: UserService,
              private notificationService: NotificationService,
              private heroService: HeroService,
              private router: Router) {
    this.heroService.hero$.subscribe(n => this.currentHero = n);
  }

  ngOnInit(): void {
    this.navOpened = false;
    this.navClass = '';

    this.userService.isUserSuperAdmin()
      .subscribe(result => {
        if (result && result.success) {
          this.isSuperAdmin = true;
        }
      });

    this.userService.isUserAdmin()
      .subscribe(result => {
        if (result && result.success) {
          this.isAdmin = true;
        }
      });
    this.heroService.doRefreshHero();

  }

  toggleNav(): void {

    this.navOpened = !this.navOpened;
    setTimeout(() => {
      this.navClass = this.navOpened ? 'show bg-dark' : '';
    }, 100);

  }

  logout() {
    this.userService.logout();
    this.router.navigateByUrl('');
  }

}
