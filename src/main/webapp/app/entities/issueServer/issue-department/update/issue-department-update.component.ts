import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IssueDepartmentFormService, IssueDepartmentFormGroup } from './issue-department-form.service';
import { IIssueDepartment } from '../issue-department.model';
import { IssueDepartmentService } from '../service/issue-department.service';

@Component({
  selector: 'microapp-issue-department-update',
  templateUrl: './issue-department-update.component.html',
})
export class IssueDepartmentUpdateComponent implements OnInit {
  isSaving = false;
  issueDepartment: IIssueDepartment | null = null;

  editForm: IssueDepartmentFormGroup = this.issueDepartmentFormService.createIssueDepartmentFormGroup();

  constructor(
    protected issueDepartmentService: IssueDepartmentService,
    protected issueDepartmentFormService: IssueDepartmentFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueDepartment }) => {
      this.issueDepartment = issueDepartment;
      if (issueDepartment) {
        this.updateForm(issueDepartment);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueDepartment = this.issueDepartmentFormService.getIssueDepartment(this.editForm);
    if (issueDepartment.id !== null) {
      this.subscribeToSaveResponse(this.issueDepartmentService.update(issueDepartment));
    } else {
      this.subscribeToSaveResponse(this.issueDepartmentService.create(issueDepartment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueDepartment>>): void {
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

  protected updateForm(issueDepartment: IIssueDepartment): void {
    this.issueDepartment = issueDepartment;
    this.issueDepartmentFormService.resetForm(this.editForm, issueDepartment);
  }
}
