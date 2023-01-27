import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssueTypeComponent } from '../list/issue-type.component';
import { IssueTypeDetailComponent } from '../detail/issue-type-detail.component';
import { IssueTypeUpdateComponent } from '../update/issue-type-update.component';
import { IssueTypeRoutingResolveService } from './issue-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issueTypeRoute: Routes = [
  {
    path: '',
    component: IssueTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssueTypeDetailComponent,
    resolve: {
      issueType: IssueTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssueTypeUpdateComponent,
    resolve: {
      issueType: IssueTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssueTypeUpdateComponent,
    resolve: {
      issueType: IssueTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issueTypeRoute)],
  exports: [RouterModule],
})
export class IssueTypeRoutingModule {}
