import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {GameComponent} from './game.component';
import {HomeLoggedComponent} from './home-logged/home-logged.component';
import {BrowserModule} from '@angular/platform-browser';
import {CoreModule} from '../core/core.module';
import {NotificationService} from '../core/notification/notification.service';
import {InventoryComponent} from './inventory/inventory.component';
import {MarketComponent} from './market/market.component';
import {WildernessComponent} from './wilderness/wilderness.component';
import {ArenaComponent} from './arena/arena.component';
import {WorkComponent} from './work/work.component';


@NgModule({
  imports: [BrowserModule, RouterModule, CoreModule],
  declarations: [
    GameComponent,
    HomeLoggedComponent,
    InventoryComponent,
    MarketComponent,
    WildernessComponent,
    ArenaComponent,
    WorkComponent
  ],
  providers: [NotificationService]
})
export class GameModule {

}
