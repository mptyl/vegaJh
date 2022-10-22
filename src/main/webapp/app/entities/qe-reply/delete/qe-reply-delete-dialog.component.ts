import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeReply } from '../qe-reply.model';
import { QeReplyService } from '../service/qe-reply.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-reply-delete-dialog.component.html',
})
export class QeReplyDeleteDialogComponent {
  qeReply?: IQeReply;

  constructor(protected qeReplyService: QeReplyService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeReplyService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
