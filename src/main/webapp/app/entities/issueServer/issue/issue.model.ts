import dayjs from 'dayjs/esm';

export interface IIssue {
  id: number;
  username?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  displayedUsername?: string | null;
  issueTitle?: string | null;
  issueContent?: string | null;
  issueType?: string | null;
  issueWorkflowStatus?: string | null;
  issueWorkflowStatusKey?: string | null;
  issuePriorityLevel?: number | null;
  issueUuid?: string | null;
  created?: dayjs.Dayjs | null;
  modified?: dayjs.Dayjs | null;
  deleted?: dayjs.Dayjs | null;
  closed?: dayjs.Dayjs | null;
}

export type NewIssue = Omit<IIssue, 'id'> & { id: null };
