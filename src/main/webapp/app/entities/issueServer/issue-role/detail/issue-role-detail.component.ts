import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueRole } from '../issue-role.model';

@Component({
  selector: 'microapp-issue-role-detail',
  templateUrl: './issue-role-detail.component.html',
})
export class IssueRoleDetailComponent implements OnInit {
  issueRole: IIssueRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueRole }) => {
      this.issueRole = issueRole;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
