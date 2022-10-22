import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeRadioGroupComponent } from './list/qe-radio-group.component';
import { QeRadioGroupDetailComponent } from './detail/qe-radio-group-detail.component';
import { QeRadioGroupUpdateComponent } from './update/qe-radio-group-update.component';
import { QeRadioGroupDeleteDialogComponent } from './delete/qe-radio-group-delete-dialog.component';
import { QeRadioGroupRoutingModule } from './route/qe-radio-group-routing.module';

@NgModule({
  imports: [SharedModule, QeRadioGroupRoutingModule],
  declarations: [QeRadioGroupComponent, QeRadioGroupDetailComponent, QeRadioGroupUpdateComponent, QeRadioGroupDeleteDialogComponent],
})
export class QeRadioGroupModule {}
