import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IssueAssignmentFormService, IssueAssignmentFormGroup } from './issue-assignment-form.service';
import { IIssueAssignment } from '../issue-assignment.model';
import { IssueAssignmentService } from '../service/issue-assignment.service';

@Component({
  selector: 'microapp-issue-assignment-update',
  templateUrl: './issue-assignment-update.component.html',
})
export class IssueAssignmentUpdateComponent implements OnInit {
  isSaving = false;
  issueAssignment: IIssueAssignment | null = null;

  editForm: IssueAssignmentFormGroup = this.issueAssignmentFormService.createIssueAssignmentFormGroup();

  constructor(
    protected issueAssignmentService: IssueAssignmentService,
    protected issueAssignmentFormService: IssueAssignmentFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueAssignment }) => {
      this.issueAssignment = issueAssignment;
      if (issueAssignment) {
        this.updateForm(issueAssignment);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueAssignment = this.issueAssignmentFormService.getIssueAssignment(this.editForm);
    if (issueAssignment.id !== null) {
      this.subscribeToSaveResponse(this.issueAssignmentService.update(issueAssignment));
    } else {
      this.subscribeToSaveResponse(this.issueAssignmentService.create(issueAssignment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueAssignment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(issueAssignment: IIssueAssignment): void {
    this.issueAssignment = issueAssignment;
    this.issueAssignmentFormService.resetForm(this.editForm, issueAssignment);
  }
}
