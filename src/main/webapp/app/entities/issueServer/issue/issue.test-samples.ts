import dayjs from 'dayjs/esm';

import { IIssue, NewIssue } from './issue.model';

export const sampleWithRequiredData: IIssue = {
  id: 52849,
  displayedUsername: 'Wooden JSON',
  issueTitle: 'deliverables Refined virtual',
  issueContent: '../fake-data/blob/hipster.txt',
  issueType: 'metrics bricks-and-clicks Avon',
  issueWorkflowStatus: 'strategic',
  issueWorkflowStatusKey: 'Avon',
  issuePriorityLevel: 41757,
  created: dayjs('2023-01-27T03:12'),
  modified: dayjs('2023-01-26T12:00'),
};

export const sampleWithPartialData: IIssue = {
  id: 67170,
  lastName: 'Wolff',
  displayedUsername: 'attitude-oriented',
  issueTitle: 'contextually-based',
  issueContent: '../fake-data/blob/hipster.txt',
  issueType: 'indexing',
  issueWorkflowStatus: 'withdrawal Customer',
  issueWorkflowStatusKey: 'Senior Card',
  issuePriorityLevel: 45966,
  created: dayjs('2023-01-27T00:55'),
  modified: dayjs('2023-01-26T12:12'),
};

export const sampleWithFullData: IIssue = {
  id: 45610,
  username: 'Australia Buckinghamshire Steel',
  firstName: 'Ralph',
  lastName: 'Greenfelder',
  displayedUsername: 'plum Borders',
  issueTitle: 'Movies digital neural-net',
  issueContent: '../fake-data/blob/hipster.txt',
  issueType: 'Money Customer-focused',
  issueWorkflowStatus: 'morph Rapids',
  issueWorkflowStatusKey: 'Reduced',
  issuePriorityLevel: 21501,
  issueUuid: '1a1558ec-e87d-4a7f-8dfe-88bb35c3a03f',
  created: dayjs('2023-01-26T18:26'),
  modified: dayjs('2023-01-26T21:29'),
  deleted: dayjs('2023-01-26T22:06'),
  closed: dayjs('2023-01-27T01:31'),
};

export const sampleWithNewData: NewIssue = {
  displayedUsername: 'schemas Shilling calculating',
  issueTitle: 'Managed',
  issueContent: '../fake-data/blob/hipster.txt',
  issueType: 'Granite',
  issueWorkflowStatus: 'THX override mindshare',
  issueWorkflowStatusKey: 'software',
  issuePriorityLevel: 51921,
  created: dayjs('2023-01-26T17:54'),
  modified: dayjs('2023-01-26T11:44'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
