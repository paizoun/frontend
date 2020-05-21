import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { TeamUserUpdateComponent } from 'app/entities/team-user/team-user-update.component';
import { TeamUserService } from 'app/entities/team-user/team-user.service';
import { TeamUser } from 'app/shared/model/team-user.model';

describe('Component Tests', () => {
  describe('TeamUser Management Update Component', () => {
    let comp: TeamUserUpdateComponent;
    let fixture: ComponentFixture<TeamUserUpdateComponent>;
    let service: TeamUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TeamUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TeamUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeamUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TeamUser('9fec3727-3421-4967-b213-ba36557ca194');
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
        const entity = new TeamUser();
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
