import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppComponent} from './app.component';
import {AppRoutesModule} from './routes.module';
import {IndexModule} from './index/index.module';
import {GameModule} from './game/game.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutesModule,
    IndexModule,
    GameModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
