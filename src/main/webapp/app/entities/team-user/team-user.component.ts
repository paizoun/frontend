import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamUser } from 'app/shared/model/team-user.model';
import { TeamUserService } from './team-user.service';
import { TeamUserDeleteDialogComponent } from './team-user-delete-dialog.component';

@Component({
  selector: 'jhi-team-user',
  templateUrl: './team-user.component.html'
})
export class TeamUserComponent implements OnInit, OnDestroy {
  teamUsers?: ITeamUser[];
  eventSubscriber?: Subscription;

  constructor(protected teamUserService: TeamUserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.teamUserService.query().subscribe((res: HttpResponse<ITeamUser[]>) => (this.teamUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTeamUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITeamUser): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTeamUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('teamUserListModification', () => this.loadAll());
  }

  delete(teamUser: ITeamUser): void {
    const modalRef = this.modalService.open(TeamUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.teamUser = teamUser;
  }
}
