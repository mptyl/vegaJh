import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeCheckBox } from '../qe-check-box.model';
import { QeCheckBoxService } from '../service/qe-check-box.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-check-box-delete-dialog.component.html',
})
export class QeCheckBoxDeleteDialogComponent {
  qeCheckBox?: IQeCheckBox;

  constructor(protected qeCheckBoxService: QeCheckBoxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeCheckBoxService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
