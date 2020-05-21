import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScoreTeamByUser } from 'app/shared/model/score-team-by-user.model';

type EntityResponseType = HttpResponse<IScoreTeamByUser>;
type EntityArrayResponseType = HttpResponse<IScoreTeamByUser[]>;

@Injectable({ providedIn: 'root' })
export class ScoreTeamByUserService {
  public resourceUrl = SERVER_API_URL + 'api/score-team-by-users';

  constructor(protected http: HttpClient) {}

  create(scoreTeamByUser: IScoreTeamByUser): Observable<EntityResponseType> {
    return this.http.post<IScoreTeamByUser>(this.resourceUrl, scoreTeamByUser, { observe: 'response' });
  }

  update(scoreTeamByUser: IScoreTeamByUser): Observable<EntityResponseType> {
    return this.http.put<IScoreTeamByUser>(this.resourceUrl, scoreTeamByUser, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IScoreTeamByUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScoreTeamByUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
