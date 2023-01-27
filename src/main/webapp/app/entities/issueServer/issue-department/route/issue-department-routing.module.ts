import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IssueDepartmentComponent } from '../list/issue-department.component';
import { IssueDepartmentDetailComponent } from '../detail/issue-department-detail.component';
import { IssueDepartmentUpdateComponent } from '../update/issue-department-update.component';
import { IssueDepartmentRoutingResolveService } from './issue-department-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const issueDepartmentRoute: Routes = [
  {
    path: '',
    component: IssueDepartmentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IssueDepartmentDetailComponent,
    resolve: {
      issueDepartment: IssueDepartmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IssueDepartmentUpdateComponent,
    resolve: {
      issueDepartment: IssueDepartmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IssueDepartmentUpdateComponent,
    resolve: {
      issueDepartment: IssueDepartmentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(issueDepartmentRoute)],
  exports: [RouterModule],
})
export class IssueDepartmentRoutingModule {}
