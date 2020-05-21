import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';
import { TeamDetailByUserService } from './team-detail-by-user.service';
import { TeamDetailByUserDeleteDialogComponent } from './team-detail-by-user-delete-dialog.component';

@Component({
  selector: 'jhi-team-detail-by-user',
  templateUrl: './team-detail-by-user.component.html'
})
export class TeamDetailByUserComponent implements OnInit, OnDestroy {
  teamDetailByUsers?: ITeamDetailByUser[];
  eventSubscriber?: Subscription;

  constructor(
    protected teamDetailByUserService: TeamDetailByUserService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.teamDetailByUserService.query().subscribe((res: HttpResponse<ITeamDetailByUser[]>) => (this.teamDetailByUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTeamDetailByUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITeamDetailByUser): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInTeamDetailByUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('teamDetailByUserListModification', () => this.loadAll());
  }

  delete(teamDetailByUser: ITeamDetailByUser): void {
    const modalRef = this.modalService.open(TeamDetailByUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.teamDetailByUser = teamDetailByUser;
  }
}
