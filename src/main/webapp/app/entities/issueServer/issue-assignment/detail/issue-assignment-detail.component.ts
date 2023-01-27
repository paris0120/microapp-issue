import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueAssignment } from '../issue-assignment.model';

@Component({
  selector: 'microapp-issue-assignment-detail',
  templateUrl: './issue-assignment-detail.component.html',
})
export class IssueAssignmentDetailComponent implements OnInit {
  issueAssignment: IIssueAssignment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueAssignment }) => {
      this.issueAssignment = issueAssignment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
