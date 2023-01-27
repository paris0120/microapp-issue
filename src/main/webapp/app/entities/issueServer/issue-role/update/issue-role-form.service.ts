import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIssueRole, NewIssueRole } from '../issue-role.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssueRole for edit and NewIssueRoleFormGroupInput for create.
 */
type IssueRoleFormGroupInput = IIssueRole | PartialWithRequiredKeyOf<NewIssueRole>;

type IssueRoleFormDefaults = Pick<NewIssueRole, 'id'>;

type IssueRoleFormGroupContent = {
  id: FormControl<IIssueRole['id'] | NewIssueRole['id']>;
  issueRoleKey: FormControl<IIssueRole['issueRoleKey']>;
  issueRole: FormControl<IIssueRole['issueRole']>;
};

export type IssueRoleFormGroup = FormGroup<IssueRoleFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssueRoleFormService {
  createIssueRoleFormGroup(issueRole: IssueRoleFormGroupInput = { id: null }): IssueRoleFormGroup {
    const issueRoleRawValue = {
      ...this.getFormDefaults(),
      ...issueRole,
    };
    return new FormGroup<IssueRoleFormGroupContent>({
      id: new FormControl(
        { value: issueRoleRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      issueRoleKey: new FormControl(issueRoleRawValue.issueRoleKey, {
        validators: [Validators.required],
      }),
      issueRole: new FormControl(issueRoleRawValue.issueRole, {
        validators: [Validators.required],
      }),
    });
  }

  getIssueRole(form: IssueRoleFormGroup): IIssueRole | NewIssueRole {
    return form.getRawValue() as IIssueRole | NewIssueRole;
  }

  resetForm(form: IssueRoleFormGroup, issueRole: IssueRoleFormGroupInput): void {
    const issueRoleRawValue = { ...this.getFormDefaults(), ...issueRole };
    form.reset(
      {
        ...issueRoleRawValue,
        id: { value: issueRoleRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssueRoleFormDefaults {
    return {
      id: null,
    };
  }
}
