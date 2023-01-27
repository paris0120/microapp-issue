import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssuePriority } from '../issue-priority.model';

@Component({
  selector: 'microapp-issue-priority-detail',
  templateUrl: './issue-priority-detail.component.html',
})
export class IssuePriorityDetailComponent implements OnInit {
  issuePriority: IIssuePriority | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issuePriority }) => {
      this.issuePriority = issuePriority;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
