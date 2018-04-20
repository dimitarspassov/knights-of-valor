import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {AppRoutesModule} from './routing/routes.module';
import {IndexModule} from './index/index.module';
import {GameModule} from './game/game.module';
import {AdminModule} from './admin/admin.module';
import {HttpModule} from '@angular/http';
import {CoreModule} from './core/core.module';



@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    AppRoutesModule,
    CoreModule,
    IndexModule,
    GameModule,
    AdminModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
