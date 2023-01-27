import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'issue',
        data: { pageTitle: 'issueServerApp.issueServerIssue.home.title' },
        loadChildren: () => import('./issueServer/issue/issue.module').then(m => m.IssueServerIssueModule),
      },
      {
        path: 'issue-assignment',
        data: { pageTitle: 'issueServerApp.issueServerIssueAssignment.home.title' },
        loadChildren: () => import('./issueServer/issue-assignment/issue-assignment.module').then(m => m.IssueServerIssueAssignmentModule),
      },
      {
        path: 'issue-employee',
        data: { pageTitle: 'issueServerApp.issueServerIssueEmployee.home.title' },
        loadChildren: () => import('./issueServer/issue-employee/issue-employee.module').then(m => m.IssueServerIssueEmployeeModule),
      },
      {
        path: 'issue-tag',
        data: { pageTitle: 'issueServerApp.issueServerIssueTag.home.title' },
        loadChildren: () => import('./issueServer/issue-tag/issue-tag.module').then(m => m.IssueServerIssueTagModule),
      },
      {
        path: 'issue-type',
        data: { pageTitle: 'issueServerApp.issueServerIssueType.home.title' },
        loadChildren: () => import('./issueServer/issue-type/issue-type.module').then(m => m.IssueServerIssueTypeModule),
      },
      {
        path: 'issue-department',
        data: { pageTitle: 'issueServerApp.issueServerIssueDepartment.home.title' },
        loadChildren: () => import('./issueServer/issue-department/issue-department.module').then(m => m.IssueServerIssueDepartmentModule),
      },
      {
        path: 'issue-role',
        data: { pageTitle: 'issueServerApp.issueServerIssueRole.home.title' },
        loadChildren: () => import('./issueServer/issue-role/issue-role.module').then(m => m.IssueServerIssueRoleModule),
      },
      {
        path: 'issue-priority',
        data: { pageTitle: 'issueServerApp.issueServerIssuePriority.home.title' },
        loadChildren: () => import('./issueServer/issue-priority/issue-priority.module').then(m => m.IssueServerIssuePriorityModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
