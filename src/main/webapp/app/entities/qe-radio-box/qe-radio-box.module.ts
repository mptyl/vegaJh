import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeRadioBoxComponent } from './list/qe-radio-box.component';
import { QeRadioBoxDetailComponent } from './detail/qe-radio-box-detail.component';
import { QeRadioBoxUpdateComponent } from './update/qe-radio-box-update.component';
import { QeRadioBoxDeleteDialogComponent } from './delete/qe-radio-box-delete-dialog.component';
import { QeRadioBoxRoutingModule } from './route/qe-radio-box-routing.module';

@NgModule({
  imports: [SharedModule, QeRadioBoxRoutingModule],
  declarations: [QeRadioBoxComponent, QeRadioBoxDetailComponent, QeRadioBoxUpdateComponent, QeRadioBoxDeleteDialogComponent],
})
export class QeRadioBoxModule {}
