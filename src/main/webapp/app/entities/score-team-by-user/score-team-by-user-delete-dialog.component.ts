import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';
import { ScoreTeamByUserService } from './score-team-by-user.service';

@Component({
  templateUrl: './score-team-by-user-delete-dialog.component.html'
})
export class ScoreTeamByUserDeleteDialogComponent {
  scoreTeamByUser?: IScoreTeamByUser;

  constructor(
    protected scoreTeamByUserService: ScoreTeamByUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.scoreTeamByUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scoreTeamByUserListModification');
      this.activeModal.close();
    });
  }
}
