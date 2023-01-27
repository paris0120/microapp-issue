import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IssueTypeFormService, IssueTypeFormGroup } from './issue-type-form.service';
import { IIssueType } from '../issue-type.model';
import { IssueTypeService } from '../service/issue-type.service';

@Component({
  selector: 'microapp-issue-type-update',
  templateUrl: './issue-type-update.component.html',
})
export class IssueTypeUpdateComponent implements OnInit {
  isSaving = false;
  issueType: IIssueType | null = null;

  editForm: IssueTypeFormGroup = this.issueTypeFormService.createIssueTypeFormGroup();

  constructor(
    protected issueTypeService: IssueTypeService,
    protected issueTypeFormService: IssueTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueType }) => {
      this.issueType = issueType;
      if (issueType) {
        this.updateForm(issueType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueType = this.issueTypeFormService.getIssueType(this.editForm);
    if (issueType.id !== null) {
      this.subscribeToSaveResponse(this.issueTypeService.update(issueType));
    } else {
      this.subscribeToSaveResponse(this.issueTypeService.create(issueType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueType>>): void {
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

  protected updateForm(issueType: IIssueType): void {
    this.issueType = issueType;
    this.issueTypeFormService.resetForm(this.editForm, issueType);
  }
}
