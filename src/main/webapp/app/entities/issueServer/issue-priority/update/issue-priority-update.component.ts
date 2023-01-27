import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IssuePriorityFormService, IssuePriorityFormGroup } from './issue-priority-form.service';
import { IIssuePriority } from '../issue-priority.model';
import { IssuePriorityService } from '../service/issue-priority.service';

@Component({
  selector: 'microapp-issue-priority-update',
  templateUrl: './issue-priority-update.component.html',
})
export class IssuePriorityUpdateComponent implements OnInit {
  isSaving = false;
  issuePriority: IIssuePriority | null = null;

  editForm: IssuePriorityFormGroup = this.issuePriorityFormService.createIssuePriorityFormGroup();

  constructor(
    protected issuePriorityService: IssuePriorityService,
    protected issuePriorityFormService: IssuePriorityFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issuePriority }) => {
      this.issuePriority = issuePriority;
      if (issuePriority) {
        this.updateForm(issuePriority);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issuePriority = this.issuePriorityFormService.getIssuePriority(this.editForm);
    if (issuePriority.id !== null) {
      this.subscribeToSaveResponse(this.issuePriorityService.update(issuePriority));
    } else {
      this.subscribeToSaveResponse(this.issuePriorityService.create(issuePriority));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssuePriority>>): void {
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

  protected updateForm(issuePriority: IIssuePriority): void {
    this.issuePriority = issuePriority;
    this.issuePriorityFormService.resetForm(this.editForm, issuePriority);
  }
}
