import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIssueAssignment, NewIssueAssignment } from '../issue-assignment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssueAssignment for edit and NewIssueAssignmentFormGroupInput for create.
 */
type IssueAssignmentFormGroupInput = IIssueAssignment | PartialWithRequiredKeyOf<NewIssueAssignment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIssueAssignment | NewIssueAssignment> = Omit<T, 'created' | 'modified' | 'accepted' | 'deleted'> & {
  created?: string | null;
  modified?: string | null;
  accepted?: string | null;
  deleted?: string | null;
};

type IssueAssignmentFormRawValue = FormValueOf<IIssueAssignment>;

type NewIssueAssignmentFormRawValue = FormValueOf<NewIssueAssignment>;

type IssueAssignmentFormDefaults = Pick<NewIssueAssignment, 'id' | 'created' | 'modified' | 'accepted' | 'deleted'>;

type IssueAssignmentFormGroupContent = {
  id: FormControl<IssueAssignmentFormRawValue['id'] | NewIssueAssignment['id']>;
  issueId: FormControl<IssueAssignmentFormRawValue['issueId']>;
  issueUuid: FormControl<IssueAssignmentFormRawValue['issueUuid']>;
  username: FormControl<IssueAssignmentFormRawValue['username']>;
  issueAssignmentDisplayedUsername: FormControl<IssueAssignmentFormRawValue['issueAssignmentDisplayedUsername']>;
  issueRole: FormControl<IssueAssignmentFormRawValue['issueRole']>;
  displayedIssueRole: FormControl<IssueAssignmentFormRawValue['displayedIssueRole']>;
  created: FormControl<IssueAssignmentFormRawValue['created']>;
  modified: FormControl<IssueAssignmentFormRawValue['modified']>;
  accepted: FormControl<IssueAssignmentFormRawValue['accepted']>;
  deleted: FormControl<IssueAssignmentFormRawValue['deleted']>;
};

export type IssueAssignmentFormGroup = FormGroup<IssueAssignmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssueAssignmentFormService {
  createIssueAssignmentFormGroup(issueAssignment: IssueAssignmentFormGroupInput = { id: null }): IssueAssignmentFormGroup {
    const issueAssignmentRawValue = this.convertIssueAssignmentToIssueAssignmentRawValue({
      ...this.getFormDefaults(),
      ...issueAssignment,
    });
    return new FormGroup<IssueAssignmentFormGroupContent>({
      id: new FormControl(
        { value: issueAssignmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      issueId: new FormControl(issueAssignmentRawValue.issueId, {
        validators: [Validators.required],
      }),
      issueUuid: new FormControl(issueAssignmentRawValue.issueUuid, {
        validators: [Validators.required],
      }),
      username: new FormControl(issueAssignmentRawValue.username, {
        validators: [Validators.required],
      }),
      issueAssignmentDisplayedUsername: new FormControl(issueAssignmentRawValue.issueAssignmentDisplayedUsername, {
        validators: [Validators.required],
      }),
      issueRole: new FormControl(issueAssignmentRawValue.issueRole, {
        validators: [Validators.required],
      }),
      displayedIssueRole: new FormControl(issueAssignmentRawValue.displayedIssueRole, {
        validators: [Validators.required],
      }),
      created: new FormControl(issueAssignmentRawValue.created, {
        validators: [Validators.required],
      }),
      modified: new FormControl(issueAssignmentRawValue.modified, {
        validators: [Validators.required],
      }),
      accepted: new FormControl(issueAssignmentRawValue.accepted),
      deleted: new FormControl(issueAssignmentRawValue.deleted),
    });
  }

  getIssueAssignment(form: IssueAssignmentFormGroup): IIssueAssignment | NewIssueAssignment {
    return this.convertIssueAssignmentRawValueToIssueAssignment(
      form.getRawValue() as IssueAssignmentFormRawValue | NewIssueAssignmentFormRawValue
    );
  }

  resetForm(form: IssueAssignmentFormGroup, issueAssignment: IssueAssignmentFormGroupInput): void {
    const issueAssignmentRawValue = this.convertIssueAssignmentToIssueAssignmentRawValue({ ...this.getFormDefaults(), ...issueAssignment });
    form.reset(
      {
        ...issueAssignmentRawValue,
        id: { value: issueAssignmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssueAssignmentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      modified: currentTime,
      accepted: currentTime,
      deleted: currentTime,
    };
  }

  private convertIssueAssignmentRawValueToIssueAssignment(
    rawIssueAssignment: IssueAssignmentFormRawValue | NewIssueAssignmentFormRawValue
  ): IIssueAssignment | NewIssueAssignment {
    return {
      ...rawIssueAssignment,
      created: dayjs(rawIssueAssignment.created, DATE_TIME_FORMAT),
      modified: dayjs(rawIssueAssignment.modified, DATE_TIME_FORMAT),
      accepted: dayjs(rawIssueAssignment.accepted, DATE_TIME_FORMAT),
      deleted: dayjs(rawIssueAssignment.deleted, DATE_TIME_FORMAT),
    };
  }

  private convertIssueAssignmentToIssueAssignmentRawValue(
    issueAssignment: IIssueAssignment | (Partial<NewIssueAssignment> & IssueAssignmentFormDefaults)
  ): IssueAssignmentFormRawValue | PartialWithRequiredKeyOf<NewIssueAssignmentFormRawValue> {
    return {
      ...issueAssignment,
      created: issueAssignment.created ? issueAssignment.created.format(DATE_TIME_FORMAT) : undefined,
      modified: issueAssignment.modified ? issueAssignment.modified.format(DATE_TIME_FORMAT) : undefined,
      accepted: issueAssignment.accepted ? issueAssignment.accepted.format(DATE_TIME_FORMAT) : undefined,
      deleted: issueAssignment.deleted ? issueAssignment.deleted.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
