import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeCheckGroupComponent } from './list/qe-check-group.component';
import { QeCheckGroupDetailComponent } from './detail/qe-check-group-detail.component';
import { QeCheckGroupUpdateComponent } from './update/qe-check-group-update.component';
import { QeCheckGroupDeleteDialogComponent } from './delete/qe-check-group-delete-dialog.component';
import { QeCheckGroupRoutingModule } from './route/qe-check-group-routing.module';

@NgModule({
  imports: [SharedModule, QeCheckGroupRoutingModule],
  declarations: [QeCheckGroupComponent, QeCheckGroupDetailComponent, QeCheckGroupUpdateComponent, QeCheckGroupDeleteDialogComponent],
})
export class QeCheckGroupModule {}
