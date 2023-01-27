import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueType } from '../issue-type.model';

@Component({
  selector: 'microapp-issue-type-detail',
  templateUrl: './issue-type-detail.component.html',
})
export class IssueTypeDetailComponent implements OnInit {
  issueType: IIssueType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueType }) => {
      this.issueType = issueType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
