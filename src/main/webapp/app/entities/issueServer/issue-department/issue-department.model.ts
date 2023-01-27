export interface IIssueDepartment {
  id: number;
  issueDepartmentKey?: string | null;
  issueDepartment?: string | null;
}

export type NewIssueDepartment = Omit<IIssueDepartment, 'id'> & { id: null };
