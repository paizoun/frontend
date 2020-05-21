import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';
import { TeamDetailByUserService } from './team-detail-by-user.service';

@Component({
  templateUrl: './team-detail-by-user-delete-dialog.component.html'
})
export class TeamDetailByUserDeleteDialogComponent {
  teamDetailByUser?: ITeamDetailByUser;

  constructor(
    protected teamDetailByUserService: TeamDetailByUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.teamDetailByUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('teamDetailByUserListModification');
      this.activeModal.close();
    });
  }
}
