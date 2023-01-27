import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueDepartment } from '../issue-department.model';

@Component({
  selector: 'microapp-issue-department-detail',
  templateUrl: './issue-department-detail.component.html',
})
export class IssueDepartmentDetailComponent implements OnInit {
  issueDepartment: IIssueDepartment | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueDepartment }) => {
      this.issueDepartment = issueDepartment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
