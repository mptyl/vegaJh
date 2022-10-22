import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeRadioBox } from '../qe-radio-box.model';
import { QeRadioBoxService } from '../service/qe-radio-box.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-radio-box-delete-dialog.component.html',
})
export class QeRadioBoxDeleteDialogComponent {
  qeRadioBox?: IQeRadioBox;

  constructor(protected qeRadioBoxService: QeRadioBoxService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeRadioBoxService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
