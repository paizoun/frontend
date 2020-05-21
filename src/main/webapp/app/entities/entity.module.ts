import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'team',
        loadChildren: () => import('./team/team.module').then(m => m.PaizounTeamModule)
      },
      {
        path: 'team-user',
        loadChildren: () => import('./team-user/team-user.module').then(m => m.PaizounTeamUserModule)
      },
      {
        path: 'team-detail-by-user',
        loadChildren: () => import('./team-detail-by-user/team-detail-by-user.module').then(m => m.PaizounTeamDetailByUserModule)
      },
      {
        path: 'score-team-by-user',
        loadChildren: () => import('./score-team-by-user/score-team-by-user.module').then(m => m.PaizounScoreTeamByUserModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PaizounEntityModule {}
