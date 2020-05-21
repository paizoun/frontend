import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TeamDetailByUserService } from 'app/entities/team-detail-by-user/team-detail-by-user.service';
import { ITeamDetailByUser, TeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';

describe('Service Tests', () => {
  describe('TeamDetailByUser Service', () => {
    let injector: TestBed;
    let service: TeamDetailByUserService;
    let httpMock: HttpTestingController;
    let elemDefault: ITeamDetailByUser;
    let expectedResult: ITeamDetailByUser | ITeamDetailByUser[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TeamDetailByUserService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TeamDetailByUser('ID', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            userJoinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TeamDetailByUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            userJoinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            userJoinDate: currentDate
          },
          returnedFromService
        );

        service.create(new TeamDetailByUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TeamDetailByUser', () => {
        const returnedFromService = Object.assign(
          {
            userLogin: 'BBBBBB',
            teamId: 'BBBBBB',
            teamName: 'BBBBBB',
            teamShell: 'BBBBBB',
            teamScore: 1,
            userJoinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            userJoinDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TeamDetailByUser', () => {
        const returnedFromService = Object.assign(
          {
            userLogin: 'BBBBBB',
            teamId: 'BBBBBB',
            teamName: 'BBBBBB',
            teamShell: 'BBBBBB',
            teamScore: 1,
            userJoinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            userJoinDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TeamDetailByUser', () => {
        service.delete('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
