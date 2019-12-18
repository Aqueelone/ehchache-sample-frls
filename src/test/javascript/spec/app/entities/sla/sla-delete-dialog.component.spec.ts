import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { EhcacheFrlStestExampleTestModule } from '../../../test.module';
import { SLADeleteDialogComponent } from 'app/entities/sla/sla-delete-dialog.component';
import { SLAService } from 'app/entities/sla/sla.service';

describe('Component Tests', () => {
  describe('SLA Management Delete Component', () => {
    let comp: SLADeleteDialogComponent;
    let fixture: ComponentFixture<SLADeleteDialogComponent>;
    let service: SLAService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EhcacheFrlStestExampleTestModule],
        declarations: [SLADeleteDialogComponent]
      })
        .overrideTemplate(SLADeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SLADeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SLAService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
