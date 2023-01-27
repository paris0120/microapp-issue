import { IIssuePriority, NewIssuePriority } from './issue-priority.model';

export const sampleWithRequiredData: IIssuePriority = {
  id: 51389,
  issuePriority: 'Fresh Home',
  issuePriorityLevel: 7242,
};

export const sampleWithPartialData: IIssuePriority = {
  id: 96504,
  issuePriority: 'Automated',
  issuePriorityLevel: 20858,
};

export const sampleWithFullData: IIssuePriority = {
  id: 79610,
  issuePriority: 'online',
  issuePriorityLevel: 34091,
};

export const sampleWithNewData: NewIssuePriority = {
  issuePriority: 'Greece 1080p',
  issuePriorityLevel: 16005,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
