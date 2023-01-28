import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIssueType, NewIssueType } from '../issue-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssueType for edit and NewIssueTypeFormGroupInput for create.
 */
type IssueTypeFormGroupInput = IIssueType | PartialWithRequiredKeyOf<NewIssueType>;

type IssueTypeFormDefaults = Pick<NewIssueType, 'id' | 'isActive'>;

type IssueTypeFormGroupContent = {
  id: FormControl<IIssueType['id'] | NewIssueType['id']>;
  issueTypeKey: FormControl<IIssueType['issueTypeKey']>;
  issueTypeWeight: FormControl<IIssueType['issueTypeWeight']>;
  issueType: FormControl<IIssueType['issueType']>;
  isActive: FormControl<IIssueType['isActive']>;
};

export type IssueTypeFormGroup = FormGroup<IssueTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssueTypeFormService {
  createIssueTypeFormGroup(issueType: IssueTypeFormGroupInput = { id: null }): IssueTypeFormGroup {
    const issueTypeRawValue = {
      ...this.getFormDefaults(),
      ...issueType,
    };
    return new FormGroup<IssueTypeFormGroupContent>({
      id: new FormControl(
        { value: issueTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      issueTypeKey: new FormControl(issueTypeRawValue.issueTypeKey, {
        validators: [Validators.required],
      }),
      issueTypeWeight: new FormControl(issueTypeRawValue.issueTypeWeight, {
        validators: [Validators.required],
      }),
      issueType: new FormControl(issueTypeRawValue.issueType, {
        validators: [Validators.required],
      }),
      isActive: new FormControl(issueTypeRawValue.isActive, {
        validators: [Validators.required],
      }),
    });
  }

  getIssueType(form: IssueTypeFormGroup): IIssueType | NewIssueType {
    return form.getRawValue() as IIssueType | NewIssueType;
  }

  resetForm(form: IssueTypeFormGroup, issueType: IssueTypeFormGroupInput): void {
    const issueTypeRawValue = { ...this.getFormDefaults(), ...issueType };
    form.reset(
      {
        ...issueTypeRawValue,
        id: { value: issueTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssueTypeFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
