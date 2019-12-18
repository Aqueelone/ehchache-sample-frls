import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'ticket',
        loadChildren: () => import('./ticket/ticket.module').then(m => m.EhcacheFrlStestExampleTicketModule)
      },
      {
        path: 'sla',
        loadChildren: () => import('./sla/sla.module').then(m => m.EhcacheFrlStestExampleSLAModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EhcacheFrlStestExampleEntityModule {}
