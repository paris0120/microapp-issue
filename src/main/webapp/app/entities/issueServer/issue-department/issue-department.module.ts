import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IssueDepartmentComponent } from './list/issue-department.component';
import { IssueDepartmentDetailComponent } from './detail/issue-department-detail.component';
import { IssueDepartmentUpdateComponent } from './update/issue-department-update.component';
import { IssueDepartmentDeleteDialogComponent } from './delete/issue-department-delete-dialog.component';
import { IssueDepartmentRoutingModule } from './route/issue-department-routing.module';

@NgModule({
  imports: [SharedModule, IssueDepartmentRoutingModule],
  declarations: [
    IssueDepartmentComponent,
    IssueDepartmentDetailComponent,
    IssueDepartmentUpdateComponent,
    IssueDepartmentDeleteDialogComponent,
  ],
})
export class IssueServerIssueDepartmentModule {}
