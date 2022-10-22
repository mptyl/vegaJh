import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeRadioGroup } from '../qe-radio-group.model';
import { QeRadioGroupService } from '../service/qe-radio-group.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-radio-group-delete-dialog.component.html',
})
export class QeRadioGroupDeleteDialogComponent {
  qeRadioGroup?: IQeRadioGroup;

  constructor(protected qeRadioGroupService: QeRadioGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeRadioGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
