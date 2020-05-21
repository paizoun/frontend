import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaizounSharedModule } from 'app/shared/shared.module';
import { ScoreTeamByUserComponent } from './score-team-by-user.component';
import { ScoreTeamByUserDetailComponent } from './score-team-by-user-detail.component';
import { ScoreTeamByUserUpdateComponent } from './score-team-by-user-update.component';
import { ScoreTeamByUserDeleteDialogComponent } from './score-team-by-user-delete-dialog.component';
import { scoreTeamByUserRoute } from './score-team-by-user.route';

@NgModule({
  imports: [PaizounSharedModule, RouterModule.forChild(scoreTeamByUserRoute)],
  declarations: [
    ScoreTeamByUserComponent,
    ScoreTeamByUserDetailComponent,
    ScoreTeamByUserUpdateComponent,
    ScoreTeamByUserDeleteDialogComponent
  ],
  entryComponents: [ScoreTeamByUserDeleteDialogComponent]
})
export class PaizounScoreTeamByUserModule {}
