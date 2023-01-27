import dayjs from 'dayjs/esm';

import { IIssueAssignment, NewIssueAssignment } from './issue-assignment.model';

export const sampleWithRequiredData: IIssueAssignment = {
  id: 32720,
  issueId: 19771,
  issueUuid: '5ae96b65-5ef7-41ad-9479-68917c307271',
  username: 'Koruna Salad',
  issueAssignmentDisplayedUsername: 'Account Rubber',
  issueRole: 'Awesome maroon Granite',
  displayedIssueRole: 'Integrated Fish',
  created: dayjs('2023-01-26T22:50'),
  modified: dayjs('2023-01-27T03:17'),
};

export const sampleWithPartialData: IIssueAssignment = {
  id: 70631,
  issueId: 53954,
  issueUuid: '45d9b04a-e0fa-4e34-917e-30bb57115b4d',
  username: 'California',
  issueAssignmentDisplayedUsername: 'Rubber connecting withdrawal',
  issueRole: 'infrastructure Granite bus',
  displayedIssueRole: 'index haptic',
  created: dayjs('2023-01-26T17:05'),
  modified: dayjs('2023-01-26T13:00'),
};

export const sampleWithFullData: IIssueAssignment = {
  id: 6063,
  issueId: 74136,
  issueUuid: '086b4f10-064d-4b33-bb0c-059ec53679c3',
  username: 'explicit virtual Avon',
  issueAssignmentDisplayedUsername: 'Trail Chicken',
  issueRole: 'Denmark Chicken',
  displayedIssueRole: 'Card Silver',
  created: dayjs('2023-01-26T05:57'),
  modified: dayjs('2023-01-26T12:35'),
  accepted: dayjs('2023-01-26T12:08'),
  deleted: dayjs('2023-01-27T04:40'),
};

export const sampleWithNewData: NewIssueAssignment = {
  issueId: 64341,
  issueUuid: 'bc4d2bbf-064f-49f3-9118-f29a23af6ec0',
  username: 'panel Morocco Berkshire',
  issueAssignmentDisplayedUsername: 'Dynamic Iraq Kiribati',
  issueRole: 'TCP Future-proofed Rustic',
  displayedIssueRole: 'Concrete',
  created: dayjs('2023-01-26T11:09'),
  modified: dayjs('2023-01-27T04:16'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
