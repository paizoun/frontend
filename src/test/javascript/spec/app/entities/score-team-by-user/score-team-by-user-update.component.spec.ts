import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { ScoreTeamByUserUpdateComponent } from 'app/entities/score-team-by-user/score-team-by-user-update.component';
import { ScoreTeamByUserService } from 'app/entities/score-team-by-user/score-team-by-user.service';
import { ScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';

describe('Component Tests', () => {
  describe('ScoreTeamByUser Management Update Component', () => {
    let comp: ScoreTeamByUserUpdateComponent;
    let fixture: ComponentFixture<ScoreTeamByUserUpdateComponent>;
    let service: ScoreTeamByUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ScoreTeamByUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ScoreTeamByUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScoreTeamByUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScoreTeamByUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScoreTeamByUser('9fec3727-3421-4967-b213-ba36557ca194');
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
        const entity = new ScoreTeamByUser();
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
