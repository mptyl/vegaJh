import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeJumpExpressionComponent } from './list/qe-jump-expression.component';
import { QeJumpExpressionDetailComponent } from './detail/qe-jump-expression-detail.component';
import { QeJumpExpressionUpdateComponent } from './update/qe-jump-expression-update.component';
import { QeJumpExpressionDeleteDialogComponent } from './delete/qe-jump-expression-delete-dialog.component';
import { QeJumpExpressionRoutingModule } from './route/qe-jump-expression-routing.module';

@NgModule({
  imports: [SharedModule, QeJumpExpressionRoutingModule],
  declarations: [
    QeJumpExpressionComponent,
    QeJumpExpressionDetailComponent,
    QeJumpExpressionUpdateComponent,
    QeJumpExpressionDeleteDialogComponent,
  ],
})
export class QeJumpExpressionModule {}
