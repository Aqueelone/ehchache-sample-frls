import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EhcacheFrlStestExampleSharedModule } from 'app/shared/shared.module';
import { SLAComponent } from './sla.component';
import { SLADetailComponent } from './sla-detail.component';
import { SLAUpdateComponent } from './sla-update.component';
import { SLADeleteDialogComponent } from './sla-delete-dialog.component';
import { sLARoute } from './sla.route';

@NgModule({
  imports: [EhcacheFrlStestExampleSharedModule, RouterModule.forChild(sLARoute)],
  declarations: [SLAComponent, SLADetailComponent, SLAUpdateComponent, SLADeleteDialogComponent],
  entryComponents: [SLADeleteDialogComponent]
})
export class EhcacheFrlStestExampleSLAModule {}
