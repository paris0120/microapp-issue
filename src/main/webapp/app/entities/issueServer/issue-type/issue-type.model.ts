export interface IIssueType {
  id: number;
  issueTypeKey?: string | null;
  issueTypeWeight?: number | null;
  issueType?: string | null;
}

export type NewIssueType = Omit<IIssueType, 'id'> & { id: null };
