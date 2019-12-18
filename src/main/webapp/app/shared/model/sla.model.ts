export interface ISLA {
  id?: number;
  rts?: number;
  userId?: number;
}

export class SLA implements ISLA {
  constructor(public id?: number, public rts?: number, public userId?: number) {}
}
