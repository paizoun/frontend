import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITeam, Team } from 'app/shared/model/team.model';
import { TeamService } from './team.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

import { Location } from '@angular/common';

@Component({
  selector: 'jhi-team-update',
  templateUrl: './team-update.component.html'
})
export class TeamUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    shell: [],
    shellContentType: [],
    createTeamDate: [],
    userLogin: [],
    lastUserLogin: [],
    quantity: [],
    score: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected teamService: TeamService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public location: Location
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ team }) => {
      if (!team.id) {
        const today = moment().startOf('day');
        team.createTeamDate = today;
      }

      this.updateForm(team);
    });
  }

  updateForm(team: ITeam): void {
    this.editForm.patchValue({
      id: team.id,
      name: team.name,
      description: team.description,
      shell: team.shell,
      shellContentType: team.shellContentType,
      createTeamDate: team.createTeamDate ? team.createTeamDate.format(DATE_TIME_FORMAT) : null,
      userLogin: team.userLogin,
      lastUserLogin: team.lastUserLogin,
      quantity: team.quantity,
      score: team.score
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
    const team = this.createFromForm();
    if (team.id !== undefined) {
      this.subscribeToSaveResponse(this.teamService.update(team));
    } else {
      this.subscribeToSaveResponse(this.teamService.create(team));
    }
  }

  private createFromForm(): ITeam {
    return {
      ...new Team(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      shellContentType: this.editForm.get(['shellContentType'])!.value,
      shell: this.editForm.get(['shell'])!.value,
      createTeamDate: this.editForm.get(['createTeamDate'])!.value
        ? moment(this.editForm.get(['createTeamDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      userLogin: this.editForm.get(['userLogin'])!.value,
      lastUserLogin: this.editForm.get(['lastUserLogin'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      score: this.editForm.get(['score'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeam>>): void {
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

  public isNewTeam(): boolean {
    let titlee = this.location.prepareExternalUrl(this.location.path());
    if (titlee.charAt(0).startsWith('#')) {
      titlee = titlee.slice(1);
    }
    return titlee === '/team/new';
  }
}
