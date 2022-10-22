import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeReplyComponent } from './list/qe-reply.component';
import { QeReplyDetailComponent } from './detail/qe-reply-detail.component';
import { QeReplyUpdateComponent } from './update/qe-reply-update.component';
import { QeReplyDeleteDialogComponent } from './delete/qe-reply-delete-dialog.component';
import { QeReplyRoutingModule } from './route/qe-reply-routing.module';

@NgModule({
  imports: [SharedModule, QeReplyRoutingModule],
  declarations: [QeReplyComponent, QeReplyDetailComponent, QeReplyUpdateComponent, QeReplyDeleteDialogComponent],
})
export class QeReplyModule {}
