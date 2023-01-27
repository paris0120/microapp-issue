import dayjs from 'dayjs/esm';

export interface IIssueAssignment {
  id: number;
  issueId?: number | null;
  issueUuid?: string | null;
  username?: string | null;
  issueAssignmentDisplayedUsername?: string | null;
  issueRole?: string | null;
  displayedIssueRole?: string | null;
  created?: dayjs.Dayjs | null;
  modified?: dayjs.Dayjs | null;
  accepted?: dayjs.Dayjs | null;
  deleted?: dayjs.Dayjs | null;
}

export type NewIssueAssignment = Omit<IIssueAssignment, 'id'> & { id: null };
