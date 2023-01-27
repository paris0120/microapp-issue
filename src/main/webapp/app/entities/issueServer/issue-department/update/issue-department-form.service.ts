import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIssueDepartment, NewIssueDepartment } from '../issue-department.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssueDepartment for edit and NewIssueDepartmentFormGroupInput for create.
 */
type IssueDepartmentFormGroupInput = IIssueDepartment | PartialWithRequiredKeyOf<NewIssueDepartment>;

type IssueDepartmentFormDefaults = Pick<NewIssueDepartment, 'id'>;

type IssueDepartmentFormGroupContent = {
  id: FormControl<IIssueDepartment['id'] | NewIssueDepartment['id']>;
  issueDepartmentKey: FormControl<IIssueDepartment['issueDepartmentKey']>;
  issueDepartment: FormControl<IIssueDepartment['issueDepartment']>;
};

export type IssueDepartmentFormGroup = FormGroup<IssueDepartmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssueDepartmentFormService {
  createIssueDepartmentFormGroup(issueDepartment: IssueDepartmentFormGroupInput = { id: null }): IssueDepartmentFormGroup {
    const issueDepartmentRawValue = {
      ...this.getFormDefaults(),
      ...issueDepartment,
    };
    return new FormGroup<IssueDepartmentFormGroupContent>({
      id: new FormControl(
        { value: issueDepartmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      issueDepartmentKey: new FormControl(issueDepartmentRawValue.issueDepartmentKey, {
        validators: [Validators.required],
      }),
      issueDepartment: new FormControl(issueDepartmentRawValue.issueDepartment, {
        validators: [Validators.required],
      }),
    });
  }

  getIssueDepartment(form: IssueDepartmentFormGroup): IIssueDepartment | NewIssueDepartment {
    return form.getRawValue() as IIssueDepartment | NewIssueDepartment;
  }

  resetForm(form: IssueDepartmentFormGroup, issueDepartment: IssueDepartmentFormGroupInput): void {
    const issueDepartmentRawValue = { ...this.getFormDefaults(), ...issueDepartment };
    form.reset(
      {
        ...issueDepartmentRawValue,
        id: { value: issueDepartmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssueDepartmentFormDefaults {
    return {
      id: null,
    };
  }
}
