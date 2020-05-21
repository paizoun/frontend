export interface IScoreTeamByUser {
  id?: string;
  userLogin?: string;
  teamId?: string;
  score?: number;
}

export class ScoreTeamByUser implements IScoreTeamByUser {
  constructor(public id?: string, public userLogin?: string, public teamId?: string, public score?: number) {}
}
