import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'game',
  templateUrl: './game.component.html',
  styleUrls: ['../app.component.scss', './game.component.scss']
})
export class GameComponent implements OnInit {

  private navOpened: boolean;
  private navClass: string;
  private isLogged: boolean;

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
}
