import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISLA } from 'app/shared/model/sla.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SLAService } from './sla.service';
import { SLADeleteDialogComponent } from './sla-delete-dialog.component';

@Component({
  selector: 'jhi-sla',
  templateUrl: './sla.component.html'
})
export class SLAComponent implements OnInit, OnDestroy {
  sLAS: ISLA[];
  eventSubscriber: Subscription;
  itemsPerPage: number;
  links: any;
  page: any;
  predicate: any;
  reverse: any;
  totalItems: number;

  constructor(
    protected sLAService: SLAService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sLAS = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.reverse = true;
  }

  loadAll() {
    this.sLAService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISLA[]>) => this.paginateSLAS(res.body, res.headers));
  }

  reset() {
    this.page = 0;
    this.sLAS = [];
    this.loadAll();
  }

  loadPage(page) {
    this.page = page;
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSLAS();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISLA) {
    return item.id;
  }

  registerChangeInSLAS() {
    this.eventSubscriber = this.eventManager.subscribe('sLAListModification', () => this.reset());
  }

  delete(sLA: ISLA) {
    const modalRef = this.modalService.open(SLADeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sLA = sLA;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSLAS(data: ISLA[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    for (let i = 0; i < data.length; i++) {
      this.sLAS.push(data[i]);
    }
  }
}
