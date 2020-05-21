import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TeamUserService } from 'app/entities/team-user/team-user.service';
import { ITeamUser, TeamUser } from 'app/shared/model/team-user.model';

describe('Service Tests', () => {
  describe('TeamUser Service', () => {
    let injector: TestBed;
    let service: TeamUserService;
    let httpMock: HttpTestingController;
    let elemDefault: ITeamUser;
    let expectedResult: ITeamUser | ITeamUser[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TeamUserService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TeamUser('ID', 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            joinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TeamUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            joinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joinDate: currentDate
          },
          returnedFromService
        );

        service.create(new TeamUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TeamUser', () => {
        const returnedFromService = Object.assign(
          {
            userLogin: 'BBBBBB',
            teamId: 'BBBBBB',
            joinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joinDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TeamUser', () => {
        const returnedFromService = Object.assign(
          {
            userLogin: 'BBBBBB',
            teamId: 'BBBBBB',
            joinDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joinDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TeamUser', () => {
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
