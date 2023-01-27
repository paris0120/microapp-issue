import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIssueRole } from '../issue-role.model';
import { IssueRoleService } from '../service/issue-role.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './issue-role-delete-dialog.component.html',
})
export class IssueRoleDeleteDialogComponent {
  issueRole?: IIssueRole;

  constructor(protected issueRoleService: IssueRoleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueRoleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
