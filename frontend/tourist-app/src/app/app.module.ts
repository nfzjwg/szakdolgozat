import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { HeaderComponent } from './pages/header/header.component';
import { TopPanelComponent } from './pages/top-panel/top-panel.component';


@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    HeaderComponent,
    TopPanelComponent
  ],
  imports: [
    
    BrowserModule,
    AppRoutingModule,
    BrowserModule
     
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
