export interface IIssueRole {
  id: number;
  issueRoleKey?: string | null;
  issueRole?: string | null;
}

export type NewIssueRole = Omit<IIssueRole, 'id'> & { id: null };
