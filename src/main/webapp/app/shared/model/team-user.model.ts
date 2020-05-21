import { Moment } from 'moment';

export interface ITeamUser {
  id?: string;
  userLogin?: string;
  teamId?: string;
  joinDate?: Moment;
}

export class TeamUser implements ITeamUser {
  constructor(public id?: string, public userLogin?: string, public teamId?: string, public joinDate?: Moment) {}
}
