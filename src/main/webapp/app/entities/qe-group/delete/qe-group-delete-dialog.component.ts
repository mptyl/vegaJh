import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IQeGroup } from '../qe-group.model';
import { QeGroupService } from '../service/qe-group.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './qe-group-delete-dialog.component.html',
})
export class QeGroupDeleteDialogComponent {
  qeGroup?: IQeGroup;

  constructor(protected qeGroupService: QeGroupService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.qeGroupService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
