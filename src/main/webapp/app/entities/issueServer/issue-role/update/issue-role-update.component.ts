import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IssueRoleFormService, IssueRoleFormGroup } from './issue-role-form.service';
import { IIssueRole } from '../issue-role.model';
import { IssueRoleService } from '../service/issue-role.service';

@Component({
  selector: 'microapp-issue-role-update',
  templateUrl: './issue-role-update.component.html',
})
export class IssueRoleUpdateComponent implements OnInit {
  isSaving = false;
  issueRole: IIssueRole | null = null;

  editForm: IssueRoleFormGroup = this.issueRoleFormService.createIssueRoleFormGroup();

  constructor(
    protected issueRoleService: IssueRoleService,
    protected issueRoleFormService: IssueRoleFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueRole }) => {
      this.issueRole = issueRole;
      if (issueRole) {
        this.updateForm(issueRole);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueRole = this.issueRoleFormService.getIssueRole(this.editForm);
    if (issueRole.id !== null) {
      this.subscribeToSaveResponse(this.issueRoleService.update(issueRole));
    } else {
      this.subscribeToSaveResponse(this.issueRoleService.create(issueRole));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueRole>>): void {
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

  protected updateForm(issueRole: IIssueRole): void {
    this.issueRole = issueRole;
    this.issueRoleFormService.resetForm(this.editForm, issueRole);
  }
}
