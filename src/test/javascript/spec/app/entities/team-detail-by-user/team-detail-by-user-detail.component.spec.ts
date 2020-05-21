import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PaizounTestModule } from '../../../test.module';
import { TeamDetailByUserDetailComponent } from 'app/entities/team-detail-by-user/team-detail-by-user-detail.component';
import { TeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';

describe('Component Tests', () => {
  describe('TeamDetailByUser Management Detail Component', () => {
    let comp: TeamDetailByUserDetailComponent;
    let fixture: ComponentFixture<TeamDetailByUserDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({
      data: of({ teamDetailByUser: new TeamDetailByUser('9fec3727-3421-4967-b213-ba36557ca194') })
    } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaizounTestModule],
        declarations: [TeamDetailByUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TeamDetailByUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeamDetailByUserDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load teamDetailByUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.teamDetailByUser).toEqual(jasmine.objectContaining({ id: '9fec3727-3421-4967-b213-ba36557ca194' }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
