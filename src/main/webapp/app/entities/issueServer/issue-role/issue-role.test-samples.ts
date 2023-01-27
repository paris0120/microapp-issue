import { IIssueRole, NewIssueRole } from './issue-role.model';

export const sampleWithRequiredData: IIssueRole = {
  id: 36780,
  issueRoleKey: 'system',
  issueRole: 'Sleek program',
};

export const sampleWithPartialData: IIssueRole = {
  id: 32963,
  issueRoleKey: 'Frozen',
  issueRole: 'quantify Direct',
};

export const sampleWithFullData: IIssueRole = {
  id: 36452,
  issueRoleKey: 'protocol',
  issueRole: 'orange Fantastic neural',
};

export const sampleWithNewData: NewIssueRole = {
  issueRoleKey: 'Mauritius invoice Cotton',
  issueRole: 'Fish Loan',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
