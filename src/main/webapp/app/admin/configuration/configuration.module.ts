import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EhcacheFrlStestExampleSharedModule } from 'app/shared/shared.module';

import { JhiConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [EhcacheFrlStestExampleSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [JhiConfigurationComponent]
})
export class ConfigurationModule {}
