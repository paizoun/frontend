import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITeamDetailByUser, TeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';
import { TeamDetailByUserService } from './team-detail-by-user.service';
import { TeamDetailByUserComponent } from './team-detail-by-user.component';
import { TeamDetailByUserDetailComponent } from './team-detail-by-user-detail.component';
import { TeamDetailByUserUpdateComponent } from './team-detail-by-user-update.component';

@Injectable({ providedIn: 'root' })
export class TeamDetailByUserResolve implements Resolve<ITeamDetailByUser> {
  constructor(private service: TeamDetailByUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeamDetailByUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((teamDetailByUser: HttpResponse<TeamDetailByUser>) => {
          if (teamDetailByUser.body) {
            return of(teamDetailByUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TeamDetailByUser());
  }
}

export const teamDetailByUserRoute: Routes = [
  {
    path: '',
    component: TeamDetailByUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.teamDetailByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TeamDetailByUserDetailComponent,
    resolve: {
      teamDetailByUser: TeamDetailByUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.teamDetailByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TeamDetailByUserUpdateComponent,
    resolve: {
      teamDetailByUser: TeamDetailByUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.teamDetailByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TeamDetailByUserUpdateComponent,
    resolve: {
      teamDetailByUser: TeamDetailByUserResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'paizounApp.teamDetailByUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
