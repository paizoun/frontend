import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { MENU_ROUTE } from './menu.route';
import { MenuComponent } from './menu.component';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild([MENU_ROUTE])],
  declarations: [MenuComponent]
})
export class PaizounMenuModule {}
