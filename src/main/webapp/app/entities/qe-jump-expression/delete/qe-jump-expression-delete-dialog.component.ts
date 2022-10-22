import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeJumpExpression } from '../qe-jump-expression.model';
import { QeJumpExpressionService } from '../service/qe-jump-expression.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-jump-expression-delete-dialog.component.html',
})
export class QeJumpExpressionDeleteDialogComponent {
  qeJumpExpression?: IQeJumpExpression;

  constructor(protected qeJumpExpressionService: QeJumpExpressionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeJumpExpressionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
