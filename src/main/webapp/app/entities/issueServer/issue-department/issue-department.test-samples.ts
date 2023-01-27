import { IIssueDepartment, NewIssueDepartment } from './issue-department.model';

export const sampleWithRequiredData: IIssueDepartment = {
  id: 89471,
  issueDepartmentKey: 'Shoes Refined virtual',
  issueDepartment: 'blockchains program',
};

export const sampleWithPartialData: IIssueDepartment = {
  id: 3102,
  issueDepartmentKey: 'rich Director digital',
  issueDepartment: 'Buckinghamshire Belize navigate',
};

export const sampleWithFullData: IIssueDepartment = {
  id: 32795,
  issueDepartmentKey: 'engage Berkshire',
  issueDepartment: 'indigo Turkmenistan',
};

export const sampleWithNewData: NewIssueDepartment = {
  issueDepartmentKey: 'monitor',
  issueDepartment: 'Operations Market panel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
