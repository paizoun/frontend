import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITeamUser } from 'app/shared/model/team-user.model';

type EntityResponseType = HttpResponse<ITeamUser>;
type EntityArrayResponseType = HttpResponse<ITeamUser[]>;

@Injectable({ providedIn: 'root' })
export class TeamUserService {
  public resourceUrl = SERVER_API_URL + 'api/team-users';

  constructor(protected http: HttpClient) {}

  create(teamUser: ITeamUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamUser);
    return this.http
      .post<ITeamUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(teamUser: ITeamUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamUser);
    return this.http
      .put<ITeamUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITeamUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(teamUser: ITeamUser): ITeamUser {
    const copy: ITeamUser = Object.assign({}, teamUser, {
      joinDate: teamUser.joinDate && teamUser.joinDate.isValid() ? teamUser.joinDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.joinDate = res.body.joinDate ? moment(res.body.joinDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((teamUser: ITeamUser) => {
        teamUser.joinDate = teamUser.joinDate ? moment(teamUser.joinDate) : undefined;
      });
    }
    return res;
  }
}
