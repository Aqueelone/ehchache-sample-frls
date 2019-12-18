import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EhcacheFrlStestExampleTestModule } from '../../../test.module';
import { SLADetailComponent } from 'app/entities/sla/sla-detail.component';
import { SLA } from 'app/shared/model/sla.model';

describe('Component Tests', () => {
  describe('SLA Management Detail Component', () => {
    let comp: SLADetailComponent;
    let fixture: ComponentFixture<SLADetailComponent>;
    const route = ({ data: of({ sLA: new SLA(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [EhcacheFrlStestExampleTestModule],
        declarations: [SLADetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SLADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SLADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sLA).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
