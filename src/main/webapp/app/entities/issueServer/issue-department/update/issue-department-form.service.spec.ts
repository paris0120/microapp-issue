import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../issue-department.test-samples';

import { IssueDepartmentFormService } from './issue-department-form.service';

describe('IssueDepartment Form Service', () => {
  let service: IssueDepartmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueDepartmentFormService);
  });

  describe('Service methods', () => {
    describe('createIssueDepartmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssueDepartmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueDepartmentKey: expect.any(Object),
            issueDepartment: expect.any(Object),
          })
        );
      });

      it('passing IIssueDepartment should create a new form with FormGroup', () => {
        const formGroup = service.createIssueDepartmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueDepartmentKey: expect.any(Object),
            issueDepartment: expect.any(Object),
          })
        );
      });
    });

    describe('getIssueDepartment', () => {
      it('should return NewIssueDepartment for default IssueDepartment initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssueDepartmentFormGroup(sampleWithNewData);

        const issueDepartment = service.getIssueDepartment(formGroup) as any;

        expect(issueDepartment).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssueDepartment for empty IssueDepartment initial value', () => {
        const formGroup = service.createIssueDepartmentFormGroup();

        const issueDepartment = service.getIssueDepartment(formGroup) as any;

        expect(issueDepartment).toMatchObject({});
      });

      it('should return IIssueDepartment', () => {
        const formGroup = service.createIssueDepartmentFormGroup(sampleWithRequiredData);

        const issueDepartment = service.getIssueDepartment(formGroup) as any;

        expect(issueDepartment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssueDepartment should not enable id FormControl', () => {
        const formGroup = service.createIssueDepartmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssueDepartment should disable id FormControl', () => {
        const formGroup = service.createIssueDepartmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
