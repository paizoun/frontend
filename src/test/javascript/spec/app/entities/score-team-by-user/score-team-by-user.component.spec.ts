import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaizounTestModule } from '../../../test.module';
import { ScoreTeamByUserComponent } from 'app/entities/score-team-by-user/score-team-by-user.component';
import { ScoreTeamByUserService } from 'app/entities/score-team-by-user/score-team-by-user.service';
import { ScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';

describe('Component Tests', () => {
  describe('ScoreTeamByUser Management Component', () => {
    let comp: ScoreTeamByUserComponent;
    let fixture: ComponentFixture<ScoreTeamByUserComponent>;
    let service: ScoreTeamByUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ScoreTeamByUserComponent]
      })
        .overrideTemplate(ScoreTeamByUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScoreTeamByUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScoreTeamByUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ScoreTeamByUser('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.scoreTeamByUsers && comp.scoreTeamByUsers[0]).toEqual(
        jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' })
      );
    });
  });
});
