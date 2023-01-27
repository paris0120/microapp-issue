import dayjs from 'dayjs/esm';

export interface IIssueEmployee {
  id: number;
  username?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  displayName?: string | null;
  issueDepartment?: string | null;
  defaultIssueRoleKey?: string | null;
  defaultDisplayedIssueRole?: string | null;
  isAvailable?: boolean | null;
  inOfficeFrom?: dayjs.Dayjs | null;
  officeHourFrom?: dayjs.Dayjs | null;
  officeHourTo?: dayjs.Dayjs | null;
  timezone?: string | null;
  created?: dayjs.Dayjs | null;
  modified?: dayjs.Dayjs | null;
  deleted?: dayjs.Dayjs | null;
}

export type NewIssueEmployee = Omit<IIssueEmployee, 'id'> & { id: null };
