import {Routes, RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {IndexComponent} from '../index/index.component';
import {HomeComponent} from '../index/home/home.component';
import {LoginComponent} from '../index/login/login.component';
import {RegisterComponent} from '../index/register/register.component';
import {GameComponent} from '../game/game.component';
import {HomeLoggedComponent} from '../game/home-logged/home-logged.component';
import {InventoryComponent} from '../game/inventory/inventory.component';
import {MarketComponent} from '../game/market/market.component';
import {WildernessComponent} from '../game/wilderness/wilderness.component';
import {ArenaComponent} from '../game/arena/arena.component';
import {WorkComponent} from '../game/work/work.component';
import {InboxComponent} from '../messages/inbox/inbox.component';
import {NewMessageComponent} from '../messages/new-message/new-message.component';
import {SingleMessageComponent} from '../messages/single-message/single-message.component';
import {AdminHomeComponent} from '../admin/admin-home/admin-home.component';
import {ItemsComponent} from '../admin/items/index/items.component';
import {AddItemComponent} from '../admin/items/add/add-item.component';
import {EditItemComponent} from '../admin/items/edit/edit-item.component';
import {JobsComponent} from '../admin/jobs/index/jobs.component';
import {AddJobComponent} from '../admin/jobs/add/add-job.component';
import {EditJobComponent} from '../admin/jobs/edit/edit-job.component';
import {NeutralsComponent} from '../admin/neutrals/index/neutrals.component';
import {AddNeutralComponent} from '../admin/neutrals/add/add-neutral.component';
import {EditNeutralComponent} from '../admin/neutrals/edit/edit-neutral.component';
import {PrivateRoute} from './private-route';
import {PublicRoute} from './public-route';
import {UrlConstants} from './url-constants';


const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    children: [
      {path: '', component: HomeComponent, canActivate: [PublicRoute]},
      {path: UrlConstants.LOGIN_URL, component: LoginComponent, canActivate: [PublicRoute]},
      {path: UrlConstants.REGISTER_URL, component: RegisterComponent, canActivate: [PublicRoute]},
    ]
  },
  {
    path: 'game',
    component: GameComponent,
    children: [
      {path: '', component: HomeLoggedComponent, canActivate: [PrivateRoute]},
      {path: 'inventory', component: InventoryComponent, canActivate: [PrivateRoute]},
      {path: 'market', component: MarketComponent, canActivate: [PrivateRoute]},
      {path: 'wilderness', component: WildernessComponent, canActivate: [PrivateRoute]},
      {path: 'arena', component: ArenaComponent, canActivate: [PrivateRoute]},
      {path: 'work', component: WorkComponent, canActivate: [PrivateRoute]}
    ]
  },
  {
    path: 'messages',
    component: GameComponent,
    children: [
      {path: '', component: InboxComponent, canActivate: [PrivateRoute]},
      {path: 'new', component: NewMessageComponent, canActivate: [PrivateRoute]},
      {path: ':id', component: SingleMessageComponent, canActivate: [PrivateRoute]}
    ]
  },
  {
    path: 'admin',
    component: GameComponent,
    children: [
      {path: '', component: AdminHomeComponent, canActivate: [PrivateRoute]},
      {path: 'items', component: ItemsComponent, canActivate: [PrivateRoute]},
      {path: 'items/add', component: AddItemComponent, canActivate: [PrivateRoute]},
      {path: 'items/edit/:id', component: EditItemComponent, canActivate: [PrivateRoute]},

      {path: 'jobs', component: JobsComponent, canActivate: [PrivateRoute]},
      {path: 'jobs/add', component: AddJobComponent, canActivate: [PrivateRoute]},
      {path: 'jobs/edit/:id', component: EditJobComponent, canActivate: [PrivateRoute]},

      {path: 'neutrals', component: NeutralsComponent, canActivate: [PrivateRoute]},
      {path: 'neutrals/add', component: AddNeutralComponent, canActivate: [PrivateRoute]},
      {path: 'neutrals/edit/:id', component: EditNeutralComponent, canActivate: [PrivateRoute]}
    ]
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [PrivateRoute, PublicRoute]
})

export class AppRoutesModule {

}
