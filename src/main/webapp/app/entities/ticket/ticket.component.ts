import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITicket } from 'app/shared/model/ticket.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TicketService } from './ticket.service';
import { TicketDeleteDialogComponent } from './ticket-delete-dialog.component';

@Component({
  selector: 'jhi-ticket',
  templateUrl: './ticket.component.html'
})
export class TicketComponent implements OnInit, OnDestroy {
  tickets: ITicket[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected ticketService: TicketService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tickets = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.ticketService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITicket[]>) => this.paginateTickets(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.tickets = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInTickets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITicket) {
    return item.id;
  }

  registerChangeInTickets() {
    this.eventSubscriber = this.eventManager.subscribe('ticketListModification', () => this.reset());
  }

  delete(ticket: ITicket) {
    const modalRef = this.modalService.open(TicketDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.ticket = ticket;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTickets(data: ITicket[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.tickets.push(data[i]);
    }
  }
}
