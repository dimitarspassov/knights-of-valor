import {Component, OnInit} from '@angular/core';
import {NotificationService} from '../../core/notifications/notification.service';
import {GameService} from '../game.service';
import {HeroService} from '../hero.service';
import {HeroModel} from '../hero.model';
import {AppConstants} from '../../app-constants';

@Component({
  selector: 'arena',
  templateUrl: './arena.component.html',
  styleUrls: ['./arena.component.scss']
})
export class ArenaComponent implements OnInit {

  currentHero: HeroModel;
  heroes = [];

  constructor(private notificationService: NotificationService,
              private gameService: GameService,
              private heroService: HeroService) {
    this.heroService.hero$.subscribe(n => this.currentHero = n);
  }

  ngOnInit(): void {
    this.fillArena();
  }

  private fillArena() {
    this.gameService.getHeroesForArena().subscribe(
      result => {
        this.heroes = result;
      },
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }

  fight(heroId: string) {
    this.heroService.fightHero(heroId).subscribe(
      result => {
        if (result.success) {
          this.notificationService.notify(result.message);
          this.heroService.doRefreshHero();
        } else {
          this.notificationService.showError(result.message);
        }
      },
      error => this.notificationService.showError(AppConstants.GENERIC_ERROR_MESSAGE)
    );
  }
}
