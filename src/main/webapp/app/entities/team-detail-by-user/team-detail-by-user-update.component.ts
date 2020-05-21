import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITeamDetailByUser, TeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';
import { TeamDetailByUserService } from './team-detail-by-user.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-team-detail-by-user-update',
  templateUrl: './team-detail-by-user-update.component.html'
})
export class TeamDetailByUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userLogin: [],
    teamId: [],
    teamName: [],
    teamShell: [],
    teamShellContentType: [],
    teamScore: [],
    userJoinDate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected teamDetailByUserService: TeamDetailByUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamDetailByUser }) => {
      if (!teamDetailByUser.id) {
        const today = moment().startOf('day');
        teamDetailByUser.userJoinDate = today;
      }

      this.updateForm(teamDetailByUser);
    });
  }

  updateForm(teamDetailByUser: ITeamDetailByUser): void {
    this.editForm.patchValue({
      id: teamDetailByUser.id,
      userLogin: teamDetailByUser.userLogin,
      teamId: teamDetailByUser.teamId,
      teamName: teamDetailByUser.teamName,
      teamShell: teamDetailByUser.teamShell,
      teamShellContentType: teamDetailByUser.teamShellContentType,
      teamScore: teamDetailByUser.teamScore,
      userJoinDate: teamDetailByUser.userJoinDate ? teamDetailByUser.userJoinDate.format(DATE_TIME_FORMAT) : null
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('paizounApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamDetailByUser = this.createFromForm();
    if (teamDetailByUser.id !== undefined) {
      this.subscribeToSaveResponse(this.teamDetailByUserService.update(teamDetailByUser));
    } else {
      this.subscribeToSaveResponse(this.teamDetailByUserService.create(teamDetailByUser));
    }
  }

  private createFromForm(): ITeamDetailByUser {
    return {
      ...new TeamDetailByUser(),
      id: this.editForm.get(['id'])!.value,
      userLogin: this.editForm.get(['userLogin'])!.value,
      teamId: this.editForm.get(['teamId'])!.value,
      teamName: this.editForm.get(['teamName'])!.value,
      teamShellContentType: this.editForm.get(['teamShellContentType'])!.value,
      teamShell: this.editForm.get(['teamShell'])!.value,
      teamScore: this.editForm.get(['teamScore'])!.value,
      userJoinDate: this.editForm.get(['userJoinDate'])!.value
        ? moment(this.editForm.get(['userJoinDate'])!.value, DATE_TIME_FORMAT)
        : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamDetailByUser>>): void {
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
