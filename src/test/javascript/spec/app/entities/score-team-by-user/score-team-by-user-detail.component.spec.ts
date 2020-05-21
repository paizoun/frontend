import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { ScoreTeamByUserDetailComponent } from 'app/entities/score-team-by-user/score-team-by-user-detail.component';
import { ScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';

describe('Component Tests', () => {
  describe('ScoreTeamByUser Management Detail Component', () => {
    let comp: ScoreTeamByUserDetailComponent;
    let fixture: ComponentFixture<ScoreTeamByUserDetailComponent>;
    const route = ({ data: of({ scoreTeamByUser: new ScoreTeamByUser('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [ScoreTeamByUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ScoreTeamByUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScoreTeamByUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scoreTeamByUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scoreTeamByUser).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
