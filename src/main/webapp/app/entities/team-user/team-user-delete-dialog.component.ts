import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeamUser } from 'app/shared/model/team-user.model';
import { TeamUserService } from './team-user.service';

@Component({
  templateUrl: './team-user-delete-dialog.component.html'
})
export class TeamUserDeleteDialogComponent {
  teamUser?: ITeamUser;

  constructor(protected teamUserService: TeamUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.teamUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('teamUserListModification');
      this.activeModal.close();
    });
  }
}
