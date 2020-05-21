import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamUser } from 'app/shared/model/team-user.model';

@Component({
  selector: 'jhi-team-user-detail',
  templateUrl: './team-user-detail.component.html'
})
export class TeamUserDetailComponent implements OnInit {
  teamUser: ITeamUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamUser }) => (this.teamUser = teamUser));
  }

  previousState(): void {
    window.history.back();
  }
}
