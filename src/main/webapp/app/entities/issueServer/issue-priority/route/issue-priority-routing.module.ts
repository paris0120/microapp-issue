import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssuePriorityComponent } from '../list/issue-priority.component';
import { IssuePriorityDetailComponent } from '../detail/issue-priority-detail.component';
import { IssuePriorityUpdateComponent } from '../update/issue-priority-update.component';
import { IssuePriorityRoutingResolveService } from './issue-priority-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issuePriorityRoute: Routes = [
  {
    path: '',
    component: IssuePriorityComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssuePriorityDetailComponent,
    resolve: {
      issuePriority: IssuePriorityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssuePriorityUpdateComponent,
    resolve: {
      issuePriority: IssuePriorityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssuePriorityUpdateComponent,
    resolve: {
      issuePriority: IssuePriorityRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issuePriorityRoute)],
  exports: [RouterModule],
})
export class IssuePriorityRoutingModule {}
