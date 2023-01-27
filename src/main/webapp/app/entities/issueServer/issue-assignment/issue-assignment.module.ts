import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssueAssignmentComponent } from './list/issue-assignment.component';
import { IssueAssignmentDetailComponent } from './detail/issue-assignment-detail.component';
import { IssueAssignmentUpdateComponent } from './update/issue-assignment-update.component';
import { IssueAssignmentDeleteDialogComponent } from './delete/issue-assignment-delete-dialog.component';
import { IssueAssignmentRoutingModule } from './route/issue-assignment-routing.module';

@NgModule({
  imports: [SharedModule, IssueAssignmentRoutingModule],
  declarations: [
    IssueAssignmentComponent,
    IssueAssignmentDetailComponent,
    IssueAssignmentUpdateComponent,
    IssueAssignmentDeleteDialogComponent,
  ],
})
export class IssueServerIssueAssignmentModule {}
