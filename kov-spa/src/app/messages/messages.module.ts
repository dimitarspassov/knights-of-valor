import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CoreModule} from '../core/core.module';
import {InboxComponent} from './inbox/inbox.component';
import {NewMessageComponent} from './new-message/new-message.component';
import {SingleMessageComponent} from './single-message/single-message.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';


@NgModule({
  imports: [
    RouterModule,
    FormsModule,
    CommonModule,
    CoreModule
  ],
  declarations: [InboxComponent, NewMessageComponent, SingleMessageComponent],
  providers: []
})
export class MessagesModule {

}
