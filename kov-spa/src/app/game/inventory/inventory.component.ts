import {Component, OnInit} from '@angular/core';
import {HeroService} from '../hero.service';
import {NotificationService} from '../../core/notifications/notification.service';
import {HeroModel} from '../hero.model';

@Component({
  selector: 'inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['../../app.component.scss', './inventory.component.scss']
})
export class InventoryComponent implements OnInit {

  currentHero: HeroModel;

  constructor(private heroService: HeroService,
              private notificationService: NotificationService) {
    this.heroService.hero$.subscribe(n => this.currentHero = n);
  }


  ngOnInit(): void {
  }


}
