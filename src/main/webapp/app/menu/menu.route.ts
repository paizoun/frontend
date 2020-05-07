import { Route } from '@angular/router';

import { MenuComponent } from './menu.component';

export const MENU_ROUTE: Route = {
  path: 'g',
  component: MenuComponent,
  data: {
    authorities: [],
    pageTitle: 'menu.title'
  }
};
