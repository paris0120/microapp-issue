import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssueAssignmentComponent } from '../list/issue-assignment.component';
import { IssueAssignmentDetailComponent } from '../detail/issue-assignment-detail.component';
import { IssueAssignmentUpdateComponent } from '../update/issue-assignment-update.component';
import { IssueAssignmentRoutingResolveService } from './issue-assignment-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issueAssignmentRoute: Routes = [
  {
    path: '',
    component: IssueAssignmentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssueAssignmentDetailComponent,
    resolve: {
      issueAssignment: IssueAssignmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssueAssignmentUpdateComponent,
    resolve: {
      issueAssignment: IssueAssignmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssueAssignmentUpdateComponent,
    resolve: {
      issueAssignment: IssueAssignmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issueAssignmentRoute)],
  exports: [RouterModule],
})
export class IssueAssignmentRoutingModule {}
