import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssueRoleComponent } from './list/issue-role.component';
import { IssueRoleDetailComponent } from './detail/issue-role-detail.component';
import { IssueRoleUpdateComponent } from './update/issue-role-update.component';
import { IssueRoleDeleteDialogComponent } from './delete/issue-role-delete-dialog.component';
import { IssueRoleRoutingModule } from './route/issue-role-routing.module';

@NgModule({
  imports: [SharedModule, IssueRoleRoutingModule],
  declarations: [IssueRoleComponent, IssueRoleDetailComponent, IssueRoleUpdateComponent, IssueRoleDeleteDialogComponent],
})
export class IssueServerIssueRoleModule {}
