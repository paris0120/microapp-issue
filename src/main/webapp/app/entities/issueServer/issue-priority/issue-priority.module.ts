import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssuePriorityComponent } from './list/issue-priority.component';
import { IssuePriorityDetailComponent } from './detail/issue-priority-detail.component';
import { IssuePriorityUpdateComponent } from './update/issue-priority-update.component';
import { IssuePriorityDeleteDialogComponent } from './delete/issue-priority-delete-dialog.component';
import { IssuePriorityRoutingModule } from './route/issue-priority-routing.module';

@NgModule({
  imports: [SharedModule, IssuePriorityRoutingModule],
  declarations: [IssuePriorityComponent, IssuePriorityDetailComponent, IssuePriorityUpdateComponent, IssuePriorityDeleteDialogComponent],
})
export class IssueServerIssuePriorityModule {}
