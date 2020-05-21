import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { TeamDetailByUserUpdateComponent } from 'app/entities/team-detail-by-user/team-detail-by-user-update.component';
import { TeamDetailByUserService } from 'app/entities/team-detail-by-user/team-detail-by-user.service';
import { TeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';

describe('Component Tests', () => {
  describe('TeamDetailByUser Management Update Component', () => {
    let comp: TeamDetailByUserUpdateComponent;
    let fixture: ComponentFixture<TeamDetailByUserUpdateComponent>;
    let service: TeamDetailByUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TeamDetailByUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TeamDetailByUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamDetailByUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeamDetailByUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TeamDetailByUser('9fec3727-3421-4967-b213-ba36557ca194');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TeamDetailByUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
