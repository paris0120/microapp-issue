import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssueRoleComponent } from '../list/issue-role.component';
import { IssueRoleDetailComponent } from '../detail/issue-role-detail.component';
import { IssueRoleUpdateComponent } from '../update/issue-role-update.component';
import { IssueRoleRoutingResolveService } from './issue-role-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issueRoleRoute: Routes = [
  {
    path: '',
    component: IssueRoleComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssueRoleDetailComponent,
    resolve: {
      issueRole: IssueRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssueRoleUpdateComponent,
    resolve: {
      issueRole: IssueRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssueRoleUpdateComponent,
    resolve: {
      issueRole: IssueRoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issueRoleRoute)],
  exports: [RouterModule],
})
export class IssueRoleRoutingModule {}
