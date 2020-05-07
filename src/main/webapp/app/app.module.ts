import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PaizounSharedModule } from 'app/shared/shared.module';
import { PaizounCoreModule } from 'app/core/core.module';
import { PaizounAppRoutingModule } from './app-routing.module';
import { PaizounHomeModule } from './home/home.module';
import { PaizounEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { PaizounMenuModule } from './menu/menu.module';
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PaizounSharedModule,
    PaizounCoreModule,
    PaizounHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PaizounEntityModule,
    PaizounAppRoutingModule,
    PaizounMenuModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class PaizounAppModule {}
