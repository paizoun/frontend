import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { TeamUserComponent } from './team-user.component';
import { TeamUserDetailComponent } from './team-user-detail.component';
import { TeamUserUpdateComponent } from './team-user-update.component';
import { TeamUserDeleteDialogComponent } from './team-user-delete-dialog.component';
import { teamUserRoute } from './team-user.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(teamUserRoute)],
  declarations: [TeamUserComponent, TeamUserDetailComponent, TeamUserUpdateComponent, TeamUserDeleteDialogComponent],
  entryComponents: [TeamUserDeleteDialogComponent]
})
export class PaizounTeamUserModule {}
