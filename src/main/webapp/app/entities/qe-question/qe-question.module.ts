import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { QeQuestionComponent } from './list/qe-question.component';
import { QeQuestionDetailComponent } from './detail/qe-question-detail.component';
import { QeQuestionUpdateComponent } from './update/qe-question-update.component';
import { QeQuestionDeleteDialogComponent } from './delete/qe-question-delete-dialog.component';
import { QeQuestionRoutingModule } from './route/qe-question-routing.module';

@NgModule({
  imports: [SharedModule, QeQuestionRoutingModule],
  declarations: [QeQuestionComponent, QeQuestionDetailComponent, QeQuestionUpdateComponent, QeQuestionDeleteDialogComponent],
})
export class QeQuestionModule {}
