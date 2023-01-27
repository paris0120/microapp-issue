import dayjs from 'dayjs/esm';

import { IIssueEmployee, NewIssueEmployee } from './issue-employee.model';

export const sampleWithRequiredData: IIssueEmployee = {
  id: 36166,
  username: 'Rustic Puerto',
  firstName: 'Amalia',
  lastName: 'Towne',
  displayName: 'Hawaii Ports',
  issueDepartment: 'Account Chips',
  defaultIssueRoleKey: 'magnetic',
  defaultDisplayedIssueRole: 'Gambia',
  isAvailable: false,
  inOfficeFrom: dayjs('2023-01-26T06:06'),
  officeHourFrom: dayjs('2023-01-26T13:17'),
  officeHourTo: dayjs('2023-01-26T15:32'),
  timezone: '1080p',
  created: dayjs('2023-01-26T08:16'),
  modified: dayjs('2023-01-26T13:51'),
};

export const sampleWithPartialData: IIssueEmployee = {
  id: 38448,
  username: 'Brand',
  firstName: 'Theo',
  lastName: 'Grady',
  displayName: 'Gloves Quality',
  issueDepartment: 'open-source',
  defaultIssueRoleKey: 'out-of-the-box',
  defaultDisplayedIssueRole: 'blue innovative Iowa',
  isAvailable: true,
  inOfficeFrom: dayjs('2023-01-26T11:54'),
  officeHourFrom: dayjs('2023-01-26T14:56'),
  officeHourTo: dayjs('2023-01-27T00:00'),
  timezone: 'Account Savings HDD',
  created: dayjs('2023-01-26T21:50'),
  modified: dayjs('2023-01-26T19:42'),
};

export const sampleWithFullData: IIssueEmployee = {
  id: 23750,
  username: 'Keyboard Baby technologies',
  firstName: 'Freddy',
  lastName: 'Hegmann',
  displayName: 'Cuban Berkshire',
  issueDepartment: 'Soap',
  defaultIssueRoleKey: 'port Isle conglomeration',
  defaultDisplayedIssueRole: 'Front-line Hills',
  isAvailable: false,
  inOfficeFrom: dayjs('2023-01-27T00:06'),
  officeHourFrom: dayjs('2023-01-27T04:21'),
  officeHourTo: dayjs('2023-01-26T19:10'),
  timezone: 'Lao silver generate',
  created: dayjs('2023-01-27T01:51'),
  modified: dayjs('2023-01-27T02:24'),
  deleted: dayjs('2023-01-26T15:12'),
};

export const sampleWithNewData: NewIssueEmployee = {
  username: 'Loan scalable Sweden',
  firstName: 'Jaylen',
  lastName: 'Nicolas',
  displayName: 'Account Buckinghamshire',
  issueDepartment: 'transmit well-modulated',
  defaultIssueRoleKey: 'Cayman lavender input',
  defaultDisplayedIssueRole: 'Islands,',
  isAvailable: false,
  inOfficeFrom: dayjs('2023-01-26T18:41'),
  officeHourFrom: dayjs('2023-01-26T23:04'),
  officeHourTo: dayjs('2023-01-27T03:54'),
  timezone: 'Rial program Checking',
  created: dayjs('2023-01-26T16:56'),
  modified: dayjs('2023-01-26T10:22'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
