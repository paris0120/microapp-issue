export interface IIssuePriority {
  id: number;
  issuePriority?: string | null;
  issuePriorityLevel?: number | null;
}

export type NewIssuePriority = Omit<IIssuePriority, 'id'> & { id: null };
