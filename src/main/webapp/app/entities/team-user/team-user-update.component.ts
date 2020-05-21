import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITeamUser, TeamUser } from 'app/shared/model/team-user.model';
import { TeamUserService } from './team-user.service';

@Component({
  selector: 'jhi-team-user-update',
  templateUrl: './team-user-update.component.html'
})
export class TeamUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userLogin: [],
    teamId: [],
    joinDate: []
  });

  constructor(protected teamUserService: TeamUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamUser }) => {
      if (!teamUser.id) {
        const today = moment().startOf('day');
        teamUser.joinDate = today;
      }

      this.updateForm(teamUser);
    });
  }

  updateForm(teamUser: ITeamUser): void {
    this.editForm.patchValue({
      id: teamUser.id,
      userLogin: teamUser.userLogin,
      teamId: teamUser.teamId,
      joinDate: teamUser.joinDate ? teamUser.joinDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamUser = this.createFromForm();
    if (teamUser.id !== undefined) {
      this.subscribeToSaveResponse(this.teamUserService.update(teamUser));
    } else {
      this.subscribeToSaveResponse(this.teamUserService.create(teamUser));
    }
  }

  private createFromForm(): ITeamUser {
    return {
      ...new TeamUser(),
      id: this.editForm.get(['id'])!.value,
      userLogin: this.editForm.get(['userLogin'])!.value,
      teamId: this.editForm.get(['teamId'])!.value,
      joinDate: this.editForm.get(['joinDate'])!.value ? moment(this.editForm.get(['joinDate'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamUser>>): void {
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
