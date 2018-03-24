import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CoreModule} from '../core/core.module';
import {NotificationService} from '../core/notification/notification.service';
import {AdminHomeComponent} from './admin-home/admin-home.component';
import {ItemsComponent} from './items/index/items.component';
import {AddItemComponent} from './items/add/add-item.component';
import {EditItemComponent} from './items/edit/edit-item.component';
import {JobsComponent} from './jobs/index/jobs.component';
import {AddJobComponent} from './jobs/add/add-job.component';
import {EditJobComponent} from './jobs/edit/edit-job.component';
import {NeutralsComponent} from './neutrals/index/neutrals.component';
import {AddNeutralComponent} from './neutrals/add/add-neutral.component';
import {EditNeutralComponent} from './neutrals/edit/edit-neutral.component';


@NgModule({
  imports: [
    RouterModule,
    CoreModule
  ],
  declarations: [
    AdminHomeComponent,
    ItemsComponent,
    AddItemComponent,
    EditItemComponent,
    JobsComponent,
    AddJobComponent,
    EditJobComponent,
    NeutralsComponent,
    AddNeutralComponent,
    EditNeutralComponent
  ],
  providers: [NotificationService]
})
export class AdminModule {

}
