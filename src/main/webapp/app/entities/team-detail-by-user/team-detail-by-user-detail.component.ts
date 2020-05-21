import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';

@Component({
  selector: 'jhi-team-detail-by-user-detail',
  templateUrl: './team-detail-by-user-detail.component.html'
})
export class TeamDetailByUserDetailComponent implements OnInit {
  teamDetailByUser: ITeamDetailByUser | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamDetailByUser }) => (this.teamDetailByUser = teamDetailByUser));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
