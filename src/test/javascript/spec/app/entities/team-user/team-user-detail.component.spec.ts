import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaizounTestModule } from '../../../test.module';
import { TeamUserDetailComponent } from 'app/entities/team-user/team-user-detail.component';
import { TeamUser } from 'app/shared/model/team-user.model';

describe('Component Tests', () => {
  describe('TeamUser Management Detail Component', () => {
    let comp: TeamUserDetailComponent;
    let fixture: ComponentFixture<TeamUserDetailComponent>;
    const route = ({ data: of({ teamUser: new TeamUser('9fec3727-3421-4967-b213-ba36557ca194') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TeamUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TeamUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeamUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load teamUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.teamUser).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });
  });
});
