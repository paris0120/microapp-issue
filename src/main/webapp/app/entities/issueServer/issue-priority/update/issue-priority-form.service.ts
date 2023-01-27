import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIssuePriority, NewIssuePriority } from '../issue-priority.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssuePriority for edit and NewIssuePriorityFormGroupInput for create.
 */
type IssuePriorityFormGroupInput = IIssuePriority | PartialWithRequiredKeyOf<NewIssuePriority>;

type IssuePriorityFormDefaults = Pick<NewIssuePriority, 'id'>;

type IssuePriorityFormGroupContent = {
  id: FormControl<IIssuePriority['id'] | NewIssuePriority['id']>;
  issuePriority: FormControl<IIssuePriority['issuePriority']>;
  issuePriorityLevel: FormControl<IIssuePriority['issuePriorityLevel']>;
};

export type IssuePriorityFormGroup = FormGroup<IssuePriorityFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssuePriorityFormService {
  createIssuePriorityFormGroup(issuePriority: IssuePriorityFormGroupInput = { id: null }): IssuePriorityFormGroup {
    const issuePriorityRawValue = {
      ...this.getFormDefaults(),
      ...issuePriority,
    };
    return new FormGroup<IssuePriorityFormGroupContent>({
      id: new FormControl(
        { value: issuePriorityRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      issuePriority: new FormControl(issuePriorityRawValue.issuePriority, {
        validators: [Validators.required],
      }),
      issuePriorityLevel: new FormControl(issuePriorityRawValue.issuePriorityLevel, {
        validators: [Validators.required],
      }),
    });
  }

  getIssuePriority(form: IssuePriorityFormGroup): IIssuePriority | NewIssuePriority {
    return form.getRawValue() as IIssuePriority | NewIssuePriority;
  }

  resetForm(form: IssuePriorityFormGroup, issuePriority: IssuePriorityFormGroupInput): void {
    const issuePriorityRawValue = { ...this.getFormDefaults(), ...issuePriority };
    form.reset(
      {
        ...issuePriorityRawValue,
        id: { value: issuePriorityRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssuePriorityFormDefaults {
    return {
      id: null,
    };
  }
}
