import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';
import { ScoreTeamByUserService } from './score-team-by-user.service';
import { ScoreTeamByUserDeleteDialogComponent } from './score-team-by-user-delete-dialog.component';

@Component({
  selector: 'jhi-score-team-by-user',
  templateUrl: './score-team-by-user.component.html'
})
export class ScoreTeamByUserComponent implements OnInit, OnDestroy {
  scoreTeamByUsers?: IScoreTeamByUser[];
  eventSubscriber?: Subscription;

  constructor(
    protected scoreTeamByUserService: ScoreTeamByUserService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.scoreTeamByUserService.query().subscribe((res: HttpResponse<IScoreTeamByUser[]>) => (this.scoreTeamByUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInScoreTeamByUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IScoreTeamByUser): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInScoreTeamByUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('scoreTeamByUserListModification', () => this.loadAll());
  }

  delete(scoreTeamByUser: IScoreTeamByUser): void {
    const modalRef = this.modalService.open(ScoreTeamByUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scoreTeamByUser = scoreTeamByUser;
  }
}
