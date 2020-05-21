import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScoreTeamByUser, ScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';
import { ScoreTeamByUserService } from './score-team-by-user.service';

@Component({
  selector: 'jhi-score-team-by-user-update',
  templateUrl: './score-team-by-user-update.component.html'
})
export class ScoreTeamByUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userLogin: [],
    teamId: [],
    score: []
  });

  constructor(
    protected scoreTeamByUserService: ScoreTeamByUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scoreTeamByUser }) => {
      this.updateForm(scoreTeamByUser);
    });
  }

  updateForm(scoreTeamByUser: IScoreTeamByUser): void {
    this.editForm.patchValue({
      id: scoreTeamByUser.id,
      userLogin: scoreTeamByUser.userLogin,
      teamId: scoreTeamByUser.teamId,
      score: scoreTeamByUser.score
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scoreTeamByUser = this.createFromForm();
    if (scoreTeamByUser.id !== undefined) {
      this.subscribeToSaveResponse(this.scoreTeamByUserService.update(scoreTeamByUser));
    } else {
      this.subscribeToSaveResponse(this.scoreTeamByUserService.create(scoreTeamByUser));
    }
  }

  private createFromForm(): IScoreTeamByUser {
    return {
      ...new ScoreTeamByUser(),
      id: this.editForm.get(['id'])!.value,
      userLogin: this.editForm.get(['userLogin'])!.value,
      teamId: this.editForm.get(['teamId'])!.value,
      score: this.editForm.get(['score'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScoreTeamByUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
