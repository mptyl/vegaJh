import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeCheckBoxComponent } from './list/qe-check-box.component';
import { QeCheckBoxDetailComponent } from './detail/qe-check-box-detail.component';
import { QeCheckBoxUpdateComponent } from './update/qe-check-box-update.component';
import { QeCheckBoxDeleteDialogComponent } from './delete/qe-check-box-delete-dialog.component';
import { QeCheckBoxRoutingModule } from './route/qe-check-box-routing.module';

@NgModule({
  imports: [SharedModule, QeCheckBoxRoutingModule],
  declarations: [QeCheckBoxComponent, QeCheckBoxDetailComponent, QeCheckBoxUpdateComponent, QeCheckBoxDeleteDialogComponent],
})
export class QeCheckBoxModule {}
