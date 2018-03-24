import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {AppRoutesModule} from './routes.module';
import {IndexModule} from './index/index.module';
import {GameModule} from './game/game.module';
import {MessagesModule} from './messages/messages.module';
import {AdminModule} from './admin/admin.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutesModule,
    IndexModule,
    GameModule,
    MessagesModule,
    AdminModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
