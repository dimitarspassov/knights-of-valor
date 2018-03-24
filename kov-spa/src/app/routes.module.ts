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
import {InboxComponent} from './messages/inbox/inbox.component';
import {NewMessageComponent} from './messages/new-message/new-message.component';
import {SingleMessageComponent} from './messages/single-message/single-message.component';
import {AdminHomeComponent} from './admin/admin-home/admin-home.component';
import {ItemsComponent} from './admin/items/index/items.component';
import {AddItemComponent} from './admin/items/add/add-item.component';
import {EditItemComponent} from './admin/items/edit/edit-item.component';
import {JobsComponent} from './admin/jobs/index/jobs.component';
import {AddJobComponent} from './admin/jobs/add/add-job.component';
import {EditJobComponent} from './admin/jobs/edit/edit-job.component';
import {NeutralsComponent} from './admin/neutrals/index/neutrals.component';
import {AddNeutralComponent} from './admin/neutrals/add/add-neutral.component';
import {EditNeutralComponent} from './admin/neutrals/edit/edit-neutral.component';


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
  },
  {
    path: 'messages',
    component: GameComponent,
    children: [
      {path: '', component: InboxComponent},
      {path: 'new', component: NewMessageComponent},
      {path: ':id', component: SingleMessageComponent}
    ]
  },
  {
    path: 'admin',
    component: GameComponent,
    children: [
      {path: '', component: AdminHomeComponent},
      {path: 'items', component: ItemsComponent},
      {path: 'items/add', component: AddItemComponent},
      {path: 'items/edit/:id', component: EditItemComponent},

      {path: 'jobs', component: JobsComponent},
      {path: 'jobs/add', component: AddJobComponent},
      {path: 'jobs/edit/:id', component: EditJobComponent},

      {path: 'neutrals', component: NeutralsComponent},
      {path: 'neutrals/add', component: AddNeutralComponent},
      {path: 'neutrals/edit/:id', component: EditNeutralComponent}
    ]
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutesModule {

}
