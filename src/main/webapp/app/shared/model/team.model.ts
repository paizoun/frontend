import { Moment } from 'moment';

export interface ITeam {
  id?: string;
  name?: string;
  description?: string;
  shellContentType?: string;
  shell?: any;
  createTeamDate?: Moment;
  userLogin?: string;
  lastUserLogin?: string;
  quantity?: number;
  score?: number;
}

export class Team implements ITeam {
  constructor(
    public id?: string,
    public name?: string,
    public description?: string,
    public shellContentType?: string,
    public shell?: any,
    public createTeamDate?: Moment,
    public userLogin?: string,
    public lastUserLogin?: string,
    public quantity?: number,
    public score?: number
  ) {}
}
