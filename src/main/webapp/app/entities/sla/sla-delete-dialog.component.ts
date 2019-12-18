import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISLA } from 'app/shared/model/sla.model';
import { SLAService } from './sla.service';

@Component({
  templateUrl: './sla-delete-dialog.component.html'
})
export class SLADeleteDialogComponent {
  sLA: ISLA;

  constructor(protected sLAService: SLAService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sLAService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'sLAListModification',
        content: 'Deleted an sLA'
      });
      this.activeModal.dismiss(true);
    });
  }
}
