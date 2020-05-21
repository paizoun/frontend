import { Moment } from 'moment';

export interface ITeamDetailByUser {
  id?: string;
  userLogin?: string;
  teamId?: string;
  teamName?: string;
  teamShellContentType?: string;
  teamShell?: any;
  teamScore?: number;
  userJoinDate?: Moment;
}

export class TeamDetailByUser implements ITeamDetailByUser {
  constructor(
    public id?: string,
    public userLogin?: string,
    public teamId?: string,
    public teamName?: string,
    public teamShellContentType?: string,
    public teamShell?: any,
    public teamScore?: number,
    public userJoinDate?: Moment
  ) {}
}
