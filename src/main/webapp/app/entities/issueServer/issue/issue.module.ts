import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssueComponent } from './list/issue.component';
import { IssueDetailComponent } from './detail/issue-detail.component';
import { IssueUpdateComponent } from './update/issue-update.component';
import { IssueDeleteDialogComponent } from './delete/issue-delete-dialog.component';
import { IssueRoutingModule } from './route/issue-routing.module';
import { EditorModule } from 'primeng/editor';
import { RadioButtonModule } from 'primeng/radiobutton';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';

@NgModule({
  imports: [SharedModule, IssueRoutingModule, EditorModule, RadioButtonModule, TableModule, ButtonModule],
  declarations: [IssueComponent, IssueDetailComponent, IssueUpdateComponent, IssueDeleteDialogComponent],
})
export class IssueServerIssueModule {}
