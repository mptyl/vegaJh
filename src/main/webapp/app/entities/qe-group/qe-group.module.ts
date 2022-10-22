import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeGroupComponent } from './list/qe-group.component';
import { QeGroupDetailComponent } from './detail/qe-group-detail.component';
import { QeGroupUpdateComponent } from './update/qe-group-update.component';
import { QeGroupDeleteDialogComponent } from './delete/qe-group-delete-dialog.component';
import { QeGroupRoutingModule } from './route/qe-group-routing.module';

@NgModule({
  imports: [SharedModule, QeGroupRoutingModule],
  declarations: [QeGroupComponent, QeGroupDetailComponent, QeGroupUpdateComponent, QeGroupDeleteDialogComponent],
})
export class QeGroupModule {}
