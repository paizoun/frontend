import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITeamDetailByUser } from 'app/shared/model/team-detail-by-user.model';

type EntityResponseType = HttpResponse<ITeamDetailByUser>;
type EntityArrayResponseType = HttpResponse<ITeamDetailByUser[]>;

@Injectable({ providedIn: 'root' })
export class TeamDetailByUserService {
  public resourceUrl = SERVER_API_URL + 'api/team-detail-by-users';

  constructor(protected http: HttpClient) {}

  create(teamDetailByUser: ITeamDetailByUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamDetailByUser);
    return this.http
      .post<ITeamDetailByUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(teamDetailByUser: ITeamDetailByUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(teamDetailByUser);
    return this.http
      .put<ITeamDetailByUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ITeamDetailByUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITeamDetailByUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(teamDetailByUser: ITeamDetailByUser): ITeamDetailByUser {
    const copy: ITeamDetailByUser = Object.assign({}, teamDetailByUser, {
      userJoinDate:
        teamDetailByUser.userJoinDate && teamDetailByUser.userJoinDate.isValid() ? teamDetailByUser.userJoinDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.userJoinDate = res.body.userJoinDate ? moment(res.body.userJoinDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((teamDetailByUser: ITeamDetailByUser) => {
        teamDetailByUser.userJoinDate = teamDetailByUser.userJoinDate ? moment(teamDetailByUser.userJoinDate) : undefined;
      });
    }
    return res;
  }
}
