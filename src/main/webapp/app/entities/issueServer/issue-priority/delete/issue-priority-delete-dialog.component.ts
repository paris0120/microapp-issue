import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssuePriority } from '../issue-priority.model';
import { IssuePriorityService } from '../service/issue-priority.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './issue-priority-delete-dialog.component.html',
})
export class IssuePriorityDeleteDialogComponent {
  issuePriority?: IIssuePriority;

  constructor(protected issuePriorityService: IssuePriorityService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issuePriorityService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
