import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { TeamDetailByUserComponent } from './team-detail-by-user.component';
import { TeamDetailByUserDetailComponent } from './team-detail-by-user-detail.component';
import { TeamDetailByUserUpdateComponent } from './team-detail-by-user-update.component';
import { TeamDetailByUserDeleteDialogComponent } from './team-detail-by-user-delete-dialog.component';
import { teamDetailByUserRoute } from './team-detail-by-user.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(teamDetailByUserRoute)],
  declarations: [
    TeamDetailByUserComponent,
    TeamDetailByUserDetailComponent,
    TeamDetailByUserUpdateComponent,
    TeamDetailByUserDeleteDialogComponent
  ],
  entryComponents: [TeamDetailByUserDeleteDialogComponent]
})
export class PaizounTeamDetailByUserModule {}
