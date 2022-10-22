import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeQuestion } from '../qe-question.model';
import { QeQuestionService } from '../service/qe-question.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-question-delete-dialog.component.html',
})
export class QeQuestionDeleteDialogComponent {
  qeQuestion?: IQeQuestion;

  constructor(protected qeQuestionService: QeQuestionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeQuestionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
