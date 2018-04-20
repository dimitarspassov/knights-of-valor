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
import {AdminRoute} from './admin-route';
import {SuperAdminRoute} from './super-admin-route';
import {NotFoundComponent} from '../index/not-found/not-found.component';


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
    path: 'admin',
    component: GameComponent,
    children: [
      {path: '', component: AdminHomeComponent, canActivate: [SuperAdminRoute]},
      {path: 'items', component: ItemsComponent, canActivate: [AdminRoute]},
      {path: 'items/add', component: AddItemComponent, canActivate: [AdminRoute]},
      {path: 'items/edit/:id', component: EditItemComponent, canActivate: [AdminRoute]},

      {path: 'jobs', component: JobsComponent, canActivate: [AdminRoute]},
      {path: 'jobs/add', component: AddJobComponent, canActivate: [AdminRoute]},
      {path: 'jobs/edit/:id', component: EditJobComponent, canActivate: [AdminRoute]},

      {path: 'neutrals', component: NeutralsComponent, canActivate: [AdminRoute]},
      {path: 'neutrals/add', component: AddNeutralComponent, canActivate: [AdminRoute]},
      {path: 'neutrals/edit/:id', component: EditNeutralComponent, canActivate: [AdminRoute]}
    ]
  },
  {
    path: '404',
    component: IndexComponent,
    children: [
      {path: '', component: NotFoundComponent}
    ]
  },
  {path: '**', redirectTo: '/404'}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [PrivateRoute, PublicRoute, AdminRoute, SuperAdminRoute]
})

export class AppRoutesModule {

}
