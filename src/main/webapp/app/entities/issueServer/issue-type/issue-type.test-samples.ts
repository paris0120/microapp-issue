import { IIssueType, NewIssueType } from './issue-type.model';

export const sampleWithRequiredData: IIssueType = {
  id: 28998,
  issueTypeKey: 'bypassing white',
  issueTypeWeight: 75450,
  issueType: 'client-server Tools Dynamic',
  isActive: false,
};

export const sampleWithPartialData: IIssueType = {
  id: 3341,
  issueTypeKey: 'online yellow',
  issueTypeWeight: 46341,
  issueType: 'Dynamic payment overriding',
  isActive: true,
};

export const sampleWithFullData: IIssueType = {
  id: 63643,
  issueTypeKey: 'Hat Keyboard data-warehouse',
  issueTypeWeight: 28225,
  issueType: 'Identity client-driven hacking',
  isActive: false,
};

export const sampleWithNewData: NewIssueType = {
  issueTypeKey: 'Fish Investor',
  issueTypeWeight: 83568,
  issueType: 'sky',
  isActive: false,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
