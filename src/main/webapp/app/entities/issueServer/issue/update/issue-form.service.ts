import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIssue, NewIssue } from '../issue.model';
import { Account } from '../../../../core/auth/account.model';
import { AccountService } from '../../../../core/auth/account.service';
import { LoginService } from '../../../../login/login.service';
import { ActivatedRoute } from '@angular/router';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIssue for edit and NewIssueFormGroupInput for create.
 */
type IssueFormGroupInput = IIssue | PartialWithRequiredKeyOf<NewIssue>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIssue | NewIssue> = Omit<T, 'created' | 'modified' | 'deleted' | 'closed'> & {
  created?: string | null;
  modified?: string | null;
  deleted?: string | null;
  closed?: string | null;
};

type IssueFormRawValue = FormValueOf<IIssue>;

type NewIssueFormRawValue = FormValueOf<NewIssue>;

type IssueFormDefaults = Pick<NewIssue, 'id' | 'created' | 'modified' | 'deleted' | 'closed'>;

type IssueFormGroupContent = {
  id: FormControl<IssueFormRawValue['id'] | NewIssue['id']>;
  username: FormControl<IssueFormRawValue['username']>;
  firstName: FormControl<IssueFormRawValue['firstName']>;
  lastName: FormControl<IssueFormRawValue['lastName']>;
  displayedUsername: FormControl<IssueFormRawValue['displayedUsername']>;
  issueTitle: FormControl<IssueFormRawValue['issueTitle']>;
  issueContent: FormControl<IssueFormRawValue['issueContent']>;
  issueTypeKey: FormControl<IssueFormRawValue['issueTypeKey']>;
  issueWorkflowStatus: FormControl<IssueFormRawValue['issueWorkflowStatus']>;
  issueWorkflowStatusKey: FormControl<IssueFormRawValue['issueWorkflowStatusKey']>;
  issuePriorityLevel: FormControl<IssueFormRawValue['issuePriorityLevel']>;
  issueUuid: FormControl<IssueFormRawValue['issueUuid']>;
  created: FormControl<IssueFormRawValue['created']>;
  modified: FormControl<IssueFormRawValue['modified']>;
  deleted: FormControl<IssueFormRawValue['deleted']>;
  closed: FormControl<IssueFormRawValue['closed']>;
};

export type IssueFormGroup = FormGroup<IssueFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IssueFormService {
  createIssueFormGroup(issue: IssueFormGroupInput = { id: null }): IssueFormGroup {
    const issueRawValue = this.convertIssueToIssueRawValue({
      ...this.getFormDefaults(),
      ...issue,
    });
    if (issueRawValue.displayedUsername == null) issueRawValue.displayedUsername = 'name';
    if (issueRawValue.issuePriorityLevel == null) issueRawValue.issuePriorityLevel = 0;
    if (issueRawValue.issueWorkflowStatus == null) issueRawValue.issueWorkflowStatus = 'Open';
    if (issueRawValue.issueWorkflowStatusKey == null) issueRawValue.issueWorkflowStatusKey = 'OPEN';
    return new FormGroup<IssueFormGroupContent>({
      id: new FormControl(
        { value: issueRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      username: new FormControl(issueRawValue.username),
      firstName: new FormControl(issueRawValue.firstName),
      lastName: new FormControl(issueRawValue.lastName),
      displayedUsername: new FormControl(issueRawValue.displayedUsername, {
        validators: [Validators.required],
      }),
      issueTitle: new FormControl(issueRawValue.issueTitle, {
        validators: [Validators.required, Validators.minLength(2)],
      }),
      issueContent: new FormControl(issueRawValue.issueContent, {
        validators: [Validators.required],
      }),
      issueTypeKey: new FormControl(issueRawValue.issueTypeKey, {
        validators: [Validators.required],
      }),
      issueWorkflowStatus: new FormControl(issueRawValue.issueWorkflowStatus, {
        validators: [Validators.required],
      }),
      issueWorkflowStatusKey: new FormControl(issueRawValue.issueWorkflowStatusKey, {
        validators: [Validators.required],
      }),
      issuePriorityLevel: new FormControl(issueRawValue.issuePriorityLevel, {
        validators: [Validators.required],
      }),
      issueUuid: new FormControl(issueRawValue.issueUuid),
      created: new FormControl(issueRawValue.created, {
        validators: [Validators.required],
      }),
      modified: new FormControl(issueRawValue.modified, {
        validators: [Validators.required],
      }),
      deleted: new FormControl(issueRawValue.deleted),
      closed: new FormControl(issueRawValue.closed),
    });
  }

  getIssue(form: IssueFormGroup): IIssue | NewIssue {
    return this.convertIssueRawValueToIssue(form.getRawValue() as IssueFormRawValue | NewIssueFormRawValue);
  }

  resetForm(form: IssueFormGroup, issue: IssueFormGroupInput): void {
    const issueRawValue = this.convertIssueToIssueRawValue({ ...this.getFormDefaults(), ...issue });
    form.reset(
      {
        ...issueRawValue,
        id: { value: issueRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IssueFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      created: currentTime,
      modified: currentTime,
      deleted: currentTime,
      closed: currentTime,
    };
  }

  private convertIssueRawValueToIssue(rawIssue: IssueFormRawValue | NewIssueFormRawValue): IIssue | NewIssue {
    return {
      ...rawIssue,
      created: dayjs(rawIssue.created, DATE_TIME_FORMAT),
      modified: dayjs(rawIssue.modified, DATE_TIME_FORMAT),
      deleted: dayjs(rawIssue.deleted, DATE_TIME_FORMAT),
      closed: dayjs(rawIssue.closed, DATE_TIME_FORMAT),
    };
  }

  private convertIssueToIssueRawValue(
    issue: IIssue | (Partial<NewIssue> & IssueFormDefaults)
  ): IssueFormRawValue | PartialWithRequiredKeyOf<NewIssueFormRawValue> {
    return {
      ...issue,
      created: issue.created ? issue.created.format(DATE_TIME_FORMAT) : undefined,
      modified: issue.modified ? issue.modified.format(DATE_TIME_FORMAT) : undefined,
      deleted: issue.deleted ? issue.deleted.format(DATE_TIME_FORMAT) : undefined,
      closed: issue.closed ? issue.closed.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
