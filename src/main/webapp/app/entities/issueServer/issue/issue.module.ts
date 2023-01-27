import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssueComponent } from './list/issue.component';
import { IssueDetailComponent } from './detail/issue-detail.component';
import { IssueUpdateComponent } from './update/issue-update.component';
import { IssueDeleteDialogComponent } from './delete/issue-delete-dialog.component';
import { IssueRoutingModule } from './route/issue-routing.module';
import { EditorModule } from 'primeng/editor';

@NgModule({
  imports: [SharedModule, IssueRoutingModule, EditorModule],
  declarations: [IssueComponent, IssueDetailComponent, IssueUpdateComponent, IssueDeleteDialogComponent],
})
export class IssueServerIssueModule {}
