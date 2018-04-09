import {Component, OnInit} from '@angular/core';
import {UserService} from '../index/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['../app.component.scss', './game.component.scss']
})
export class GameComponent implements OnInit {

  private navOpened: boolean;
  private navClass: string;
  private isLogged: boolean;

  constructor(private userService: UserService,
              private router: Router,
              ) {

  }

  ngOnInit(): void {
    this.navOpened = false;
    this.navClass = '';
  }

  toggleNav(): void {

    this.navOpened = !this.navOpened;
    setTimeout(() => {
      this.navClass = this.navOpened ? 'show bg-dark' : '';
    }, 100);

  }

  logout() {
    this.userService.logout();
    //todo:notification
    this.router.navigateByUrl('');
  }
}
