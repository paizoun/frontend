import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaizounTestModule } from '../../../test.module';
import { TeamDetailByUserComponent } from 'app/entities/team-detail-by-user/team-detail-by-user.component';
import { TeamDetailByUserService } from 'app/entities/team-detail-by-user/team-detail-by-user.service';
import { TeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';

describe('Component Tests', () => {
  describe('TeamDetailByUser Management Component', () => {
    let comp: TeamDetailByUserComponent;
    let fixture: ComponentFixture<TeamDetailByUserComponent>;
    let service: TeamDetailByUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TeamDetailByUserComponent]
      })
        .overrideTemplate(TeamDetailByUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamDetailByUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeamDetailByUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TeamDetailByUser('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.teamDetailByUsers && comp.teamDetailByUsers[0]).toEqual(
        jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' })
      );
    });
  });
});
