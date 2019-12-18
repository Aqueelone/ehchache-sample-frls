import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISLA } from 'app/shared/model/sla.model';

@Component({
  selector: 'jhi-sla-detail',
  templateUrl: './sla-detail.component.html'
})
export class SLADetailComponent implements OnInit {
  sLA: ISLA;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sLA }) => {
      this.sLA = sLA;
    });
  }

  previousState() {
    window.history.back();
  }
}
