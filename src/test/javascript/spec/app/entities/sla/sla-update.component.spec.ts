import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { EhcacheFrlStestExampleTestModule } from '../../../test.module';
import { SLAUpdateComponent } from 'app/entities/sla/sla-update.component';
import { SLAService } from 'app/entities/sla/sla.service';
import { SLA } from 'app/shared/model/sla.model';

describe('Component Tests', () => {
  describe('SLA Management Update Component', () => {
    let comp: SLAUpdateComponent;
    let fixture: ComponentFixture<SLAUpdateComponent>;
    let service: SLAService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EhcacheFrlStestExampleTestModule],
        declarations: [SLAUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SLAUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SLAUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SLAService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SLA(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SLA();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
