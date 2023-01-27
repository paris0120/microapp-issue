import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIssueEmployee, NewIssueEmployee } from '../issue-employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssueEmployee for edit and NewIssueEmployeeFormGroupInput for create.
 */
type IssueEmployeeFormGroupInput = IIssueEmployee | PartialWithRequiredKeyOf<NewIssueEmployee>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIssueEmployee | NewIssueEmployee> = Omit<
  T,
  'inOfficeFrom' | 'officeHourFrom' | 'officeHourTo' | 'created' | 'modified' | 'deleted'
> & {
  inOfficeFrom?: string | null;
  officeHourFrom?: string | null;
  officeHourTo?: string | null;
  created?: string | null;
  modified?: string | null;
  deleted?: string | null;
};

type IssueEmployeeFormRawValue = FormValueOf<IIssueEmployee>;

type NewIssueEmployeeFormRawValue = FormValueOf<NewIssueEmployee>;

type IssueEmployeeFormDefaults = Pick<
  NewIssueEmployee,
  'id' | 'isAvailable' | 'inOfficeFrom' | 'officeHourFrom' | 'officeHourTo' | 'created' | 'modified' | 'deleted'
>;

type IssueEmployeeFormGroupContent = {
  id: FormControl<IssueEmployeeFormRawValue['id'] | NewIssueEmployee['id']>;
  username: FormControl<IssueEmployeeFormRawValue['username']>;
  firstName: FormControl<IssueEmployeeFormRawValue['firstName']>;
  lastName: FormControl<IssueEmployeeFormRawValue['lastName']>;
  displayName: FormControl<IssueEmployeeFormRawValue['displayName']>;
  issueDepartment: FormControl<IssueEmployeeFormRawValue['issueDepartment']>;
  defaultIssueRoleKey: FormControl<IssueEmployeeFormRawValue['defaultIssueRoleKey']>;
  defaultDisplayedIssueRole: FormControl<IssueEmployeeFormRawValue['defaultDisplayedIssueRole']>;
  isAvailable: FormControl<IssueEmployeeFormRawValue['isAvailable']>;
  inOfficeFrom: FormControl<IssueEmployeeFormRawValue['inOfficeFrom']>;
  officeHourFrom: FormControl<IssueEmployeeFormRawValue['officeHourFrom']>;
  officeHourTo: FormControl<IssueEmployeeFormRawValue['officeHourTo']>;
  timezone: FormControl<IssueEmployeeFormRawValue['timezone']>;
  created: FormControl<IssueEmployeeFormRawValue['created']>;
  modified: FormControl<IssueEmployeeFormRawValue['modified']>;
  deleted: FormControl<IssueEmployeeFormRawValue['deleted']>;
};

export type IssueEmployeeFormGroup = FormGroup<IssueEmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssueEmployeeFormService {
  createIssueEmployeeFormGroup(issueEmployee: IssueEmployeeFormGroupInput = { id: null }): IssueEmployeeFormGroup {
    const issueEmployeeRawValue = this.convertIssueEmployeeToIssueEmployeeRawValue({
      ...this.getFormDefaults(),
      ...issueEmployee,
    });
    return new FormGroup<IssueEmployeeFormGroupContent>({
      id: new FormControl(
        { value: issueEmployeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      username: new FormControl(issueEmployeeRawValue.username, {
        validators: [Validators.required],
      }),
      firstName: new FormControl(issueEmployeeRawValue.firstName, {
        validators: [Validators.required],
      }),
      lastName: new FormControl(issueEmployeeRawValue.lastName, {
        validators: [Validators.required],
      }),
      displayName: new FormControl(issueEmployeeRawValue.displayName, {
        validators: [Validators.required],
      }),
      issueDepartment: new FormControl(issueEmployeeRawValue.issueDepartment, {
        validators: [Validators.required],
      }),
      defaultIssueRoleKey: new FormControl(issueEmployeeRawValue.defaultIssueRoleKey, {
        validators: [Validators.required],
      }),
      defaultDisplayedIssueRole: new FormControl(issueEmployeeRawValue.defaultDisplayedIssueRole, {
        validators: [Validators.required],
      }),
      isAvailable: new FormControl(issueEmployeeRawValue.isAvailable, {
        validators: [Validators.required],
      }),
      inOfficeFrom: new FormControl(issueEmployeeRawValue.inOfficeFrom, {
        validators: [Validators.required],
      }),
      officeHourFrom: new FormControl(issueEmployeeRawValue.officeHourFrom, {
        validators: [Validators.required],
      }),
      officeHourTo: new FormControl(issueEmployeeRawValue.officeHourTo, {
        validators: [Validators.required],
      }),
      timezone: new FormControl(issueEmployeeRawValue.timezone, {
        validators: [Validators.required],
      }),
      created: new FormControl(issueEmployeeRawValue.created, {
        validators: [Validators.required],
      }),
      modified: new FormControl(issueEmployeeRawValue.modified, {
        validators: [Validators.required],
      }),
      deleted: new FormControl(issueEmployeeRawValue.deleted),
    });
  }

  getIssueEmployee(form: IssueEmployeeFormGroup): IIssueEmployee | NewIssueEmployee {
    return this.convertIssueEmployeeRawValueToIssueEmployee(form.getRawValue() as IssueEmployeeFormRawValue | NewIssueEmployeeFormRawValue);
  }

  resetForm(form: IssueEmployeeFormGroup, issueEmployee: IssueEmployeeFormGroupInput): void {
    const issueEmployeeRawValue = this.convertIssueEmployeeToIssueEmployeeRawValue({ ...this.getFormDefaults(), ...issueEmployee });
    form.reset(
      {
        ...issueEmployeeRawValue,
        id: { value: issueEmployeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssueEmployeeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      isAvailable: false,
      inOfficeFrom: currentTime,
      officeHourFrom: currentTime,
      officeHourTo: currentTime,
      created: currentTime,
      modified: currentTime,
      deleted: currentTime,
    };
  }

  private convertIssueEmployeeRawValueToIssueEmployee(
    rawIssueEmployee: IssueEmployeeFormRawValue | NewIssueEmployeeFormRawValue
  ): IIssueEmployee | NewIssueEmployee {
    return {
      ...rawIssueEmployee,
      inOfficeFrom: dayjs(rawIssueEmployee.inOfficeFrom, DATE_TIME_FORMAT),
      officeHourFrom: dayjs(rawIssueEmployee.officeHourFrom, DATE_TIME_FORMAT),
      officeHourTo: dayjs(rawIssueEmployee.officeHourTo, DATE_TIME_FORMAT),
      created: dayjs(rawIssueEmployee.created, DATE_TIME_FORMAT),
      modified: dayjs(rawIssueEmployee.modified, DATE_TIME_FORMAT),
      deleted: dayjs(rawIssueEmployee.deleted, DATE_TIME_FORMAT),
    };
  }

  private convertIssueEmployeeToIssueEmployeeRawValue(
    issueEmployee: IIssueEmployee | (Partial<NewIssueEmployee> & IssueEmployeeFormDefaults)
  ): IssueEmployeeFormRawValue | PartialWithRequiredKeyOf<NewIssueEmployeeFormRawValue> {
    return {
      ...issueEmployee,
      inOfficeFrom: issueEmployee.inOfficeFrom ? issueEmployee.inOfficeFrom.format(DATE_TIME_FORMAT) : undefined,
      officeHourFrom: issueEmployee.officeHourFrom ? issueEmployee.officeHourFrom.format(DATE_TIME_FORMAT) : undefined,
      officeHourTo: issueEmployee.officeHourTo ? issueEmployee.officeHourTo.format(DATE_TIME_FORMAT) : undefined,
      created: issueEmployee.created ? issueEmployee.created.format(DATE_TIME_FORMAT) : undefined,
      modified: issueEmployee.modified ? issueEmployee.modified.format(DATE_TIME_FORMAT) : undefined,
      deleted: issueEmployee.deleted ? issueEmployee.deleted.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
