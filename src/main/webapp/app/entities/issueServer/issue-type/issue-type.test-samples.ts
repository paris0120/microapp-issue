import { IIssueType, NewIssueType } from './issue-type.model';

export const sampleWithRequiredData: IIssueType = {
  id: 28998,
  issueTypeKey: 'bypassing white',
  issueTypeWeight: 75450,
  issueType: 'client-server Tools Dynamic',
};

export const sampleWithPartialData: IIssueType = {
  id: 45213,
  issueTypeKey: 'Loan',
  issueTypeWeight: 16354,
  issueType: 'Metal',
};

export const sampleWithFullData: IIssueType = {
  id: 68214,
  issueTypeKey: 'invoice deposit Stand-alone',
  issueTypeWeight: 63643,
  issueType: 'Hat Keyboard data-warehouse',
};

export const sampleWithNewData: NewIssueType = {
  issueTypeKey: 'programming',
  issueTypeWeight: 90660,
  issueType: 'Cheese',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
