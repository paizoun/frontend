import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScoreTeamByUser, ScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';
import { ScoreTeamByUserService } from './score-team-by-user.service';
import { ScoreTeamByUserComponent } from './score-team-by-user.component';
import { ScoreTeamByUserDetailComponent } from './score-team-by-user-detail.component';
import { ScoreTeamByUserUpdateComponent } from './score-team-by-user-update.component';

@Injectable({ providedIn: 'root' })
export class ScoreTeamByUserResolve implements Resolve<IScoreTeamByUser> {
  constructor(private service: ScoreTeamByUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScoreTeamByUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scoreTeamByUser: HttpResponse<ScoreTeamByUser>) => {
          if (scoreTeamByUser.body) {
            return of(scoreTeamByUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ScoreTeamByUser());
  }
}

export const scoreTeamByUserRoute: Routes = [
  {
    path: '',
    component: ScoreTeamByUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.scoreTeamByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScoreTeamByUserDetailComponent,
    resolve: {
      scoreTeamByUser: ScoreTeamByUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.scoreTeamByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScoreTeamByUserUpdateComponent,
    resolve: {
      scoreTeamByUser: ScoreTeamByUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.scoreTeamByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScoreTeamByUserUpdateComponent,
    resolve: {
      scoreTeamByUser: ScoreTeamByUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.scoreTeamByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
