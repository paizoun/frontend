import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ScoreTeamByUserService } from 'app/entities/score-team-by-user/score-team-by-user.service';
import { IScoreTeamByUser, ScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';

describe('Service Tests', () => {
  describe('ScoreTeamByUser Service', () => {
    let injector: TestBed;
    let service: ScoreTeamByUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IScoreTeamByUser;
    let expectedResult: IScoreTeamByUser | IScoreTeamByUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ScoreTeamByUserService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ScoreTeamByUser('ID', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('9fec3727-3421-4967-b213-ba36557ca194').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ScoreTeamByUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ScoreTeamByUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ScoreTeamByUser', () => {
        const returnedFromService = Object.assign(
          {
            userLogin: 'BBBBBB',
            teamId: 'BBBBBB',
            score: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ScoreTeamByUser', () => {
        const returnedFromService = Object.assign(
          {
            userLogin: 'BBBBBB',
            teamId: 'BBBBBB',
            score: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ScoreTeamByUser', () => {
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
