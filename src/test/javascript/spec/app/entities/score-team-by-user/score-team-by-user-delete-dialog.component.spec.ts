import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaizounTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { ScoreTeamByUserDeleteDialogComponent } from 'app/entities/score-team-by-user/score-team-by-user-delete-dialog.component';
import { ScoreTeamByUserService } from 'app/entities/score-team-by-user/score-team-by-user.service';

describe('Component Tests', () => {
  describe('ScoreTeamByUser Management Delete Component', () => {
    let comp: ScoreTeamByUserDeleteDialogComponent;
    let fixture: ComponentFixture<ScoreTeamByUserDeleteDialogComponent>;
    let service: ScoreTeamByUserService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ScoreTeamByUserDeleteDialogComponent]
      })
        .overrideTemplate(ScoreTeamByUserDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScoreTeamByUserDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScoreTeamByUserService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('9fec3727-3421-4967-b213-ba36557ca194');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('9fec3727-3421-4967-b213-ba36557ca194');
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
