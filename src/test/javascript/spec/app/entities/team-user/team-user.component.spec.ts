import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PaizounTestModule } from '../../../test.module';
import { TeamUserComponent } from 'app/entities/team-user/team-user.component';
import { TeamUserService } from 'app/entities/team-user/team-user.service';
import { TeamUser } from 'app/shared/model/team-user.model';

describe('Component Tests', () => {
  describe('TeamUser Management Component', () => {
    let comp: TeamUserComponent;
    let fixture: ComponentFixture<TeamUserComponent>;
    let service: TeamUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TeamUserComponent]
      })
        .overrideTemplate(TeamUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeamUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TeamUser('9fec3727-3421-4967-b213-ba36557ca194')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.teamUsers && comp.teamUsers[0]).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
    });
  });
});
