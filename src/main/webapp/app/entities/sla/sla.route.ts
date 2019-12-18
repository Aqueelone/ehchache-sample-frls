import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SLA } from 'app/shared/model/sla.model';
import { SLAService } from './sla.service';
import { SLAComponent } from './sla.component';
import { SLADetailComponent } from './sla-detail.component';
import { SLAUpdateComponent } from './sla-update.component';
import { ISLA } from 'app/shared/model/sla.model';

@Injectable({ providedIn: 'root' })
export class SLAResolve implements Resolve<ISLA> {
  constructor(private service: SLAService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISLA> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((sLA: HttpResponse<SLA>) => sLA.body));
    }
    return of(new SLA());
  }
}

export const sLARoute: Routes = [
  {
    path: '',
    component: SLAComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ehcacheFrlStestExampleApp.sLA.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SLADetailComponent,
    resolve: {
      sLA: SLAResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ehcacheFrlStestExampleApp.sLA.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SLAUpdateComponent,
    resolve: {
      sLA: SLAResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ehcacheFrlStestExampleApp.sLA.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SLAUpdateComponent,
    resolve: {
      sLA: SLAResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ehcacheFrlStestExampleApp.sLA.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
