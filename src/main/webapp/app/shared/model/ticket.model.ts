import { IUser } from 'app/core/user/user.model';
import { TicketStatus } from 'app/shared/model/enumerations/ticket-status.model';

export interface ITicket {
  id?: number;
  description?: string;
  status?: TicketStatus;
  users?: IUser[];
}

export class Ticket implements ITicket {
  constructor(public id?: number, public description?: string, public status?: TicketStatus, public users?: IUser[]) {}
}
