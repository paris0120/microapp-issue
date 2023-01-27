import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssueDepartment } from '../issue-department.model';
import { IssueDepartmentService } from '../service/issue-department.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './issue-department-delete-dialog.component.html',
})
export class IssueDepartmentDeleteDialogComponent {
  issueDepartment?: IIssueDepartment;

  constructor(protected issueDepartmentService: IssueDepartmentService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueDepartmentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
