import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssueTypeComponent } from './list/issue-type.component';
import { IssueTypeDetailComponent } from './detail/issue-type-detail.component';
import { IssueTypeUpdateComponent } from './update/issue-type-update.component';
import { IssueTypeDeleteDialogComponent } from './delete/issue-type-delete-dialog.component';
import { IssueTypeRoutingModule } from './route/issue-type-routing.module';

@NgModule({
  imports: [SharedModule, IssueTypeRoutingModule],
  declarations: [IssueTypeComponent, IssueTypeDetailComponent, IssueTypeUpdateComponent, IssueTypeDeleteDialogComponent],
})
export class IssueServerIssueTypeModule {}
