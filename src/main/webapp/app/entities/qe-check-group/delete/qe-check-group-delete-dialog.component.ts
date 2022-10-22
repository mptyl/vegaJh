import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeCheckGroup } from '../qe-check-group.model';
import { QeCheckGroupService } from '../service/qe-check-group.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-check-group-delete-dialog.component.html',
})
export class QeCheckGroupDeleteDialogComponent {
  qeCheckGroup?: IQeCheckGroup;

  constructor(protected qeCheckGroupService: QeCheckGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeCheckGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
