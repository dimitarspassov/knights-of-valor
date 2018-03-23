import {Routes, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {IndexComponent} from './index/index.component';
import {HomeComponent} from './index/home/home.component';
import {LoginComponent} from './index/login/login.component';
import {RegisterComponent} from './index/register/register.component';
import {GameComponent} from './game/game.component';
import {HomeLoggedComponent} from './game/home-logged/home-logged.component';
import {InventoryComponent} from './game/inventory/inventory.component';
import {MarketComponent} from './game/market/market.component';
import {WildernessComponent} from './game/wilderness/wilderness.component';
import {ArenaComponent} from './game/arena/arena.component';
import {WorkComponent} from './game/work/work.component';


const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    children: [
      {path: '', component: HomeComponent},
      {path: 'users/login', component: LoginComponent},
      {path: 'users/register', component: RegisterComponent},
    ]
  },
  {
    path: 'game',
    component: GameComponent,
    children: [
      {path: '', component: HomeLoggedComponent},
      {path: 'inventory', component: InventoryComponent},
      {path: 'market', component: MarketComponent},
      {path: 'wilderness', component: WildernessComponent},
      {path: 'arena', component: ArenaComponent},
      {path: 'work', component: WorkComponent}
    ]
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutesModule {

}
