import { IIssueType, NewIssueType } from './issue-type.model';

export const sampleWithRequiredData: IIssueType = {
  id: 28998,
  issueTypeKey: 'bypassing white',
  issueType: 'Specialist Bike payment',
};

export const sampleWithPartialData: IIssueType = {
  id: 3341,
  issueTypeKey: 'online yellow',
  issueType: 'Unit THX',
};

export const sampleWithFullData: IIssueType = {
  id: 20427,
  issueTypeKey: 'Stand-alone invoice Rupee',
  issueType: 'Keyboard data-warehouse static',
};

export const sampleWithNewData: NewIssueType = {
  issueTypeKey: 'Product neural',
  issueType: 'Fantastic capacitor Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
