import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';

@Component({
  selector: 'jhi-score-team-by-user-detail',
  templateUrl: './score-team-by-user-detail.component.html'
})
export class ScoreTeamByUserDetailComponent implements OnInit {
  scoreTeamByUser: IScoreTeamByUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scoreTeamByUser }) => (this.scoreTeamByUser = scoreTeamByUser));
  }

  previousState(): void {
    window.history.back();
  }
}
