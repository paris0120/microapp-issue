import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../issue-priority.test-samples';

import { IssuePriorityFormService } from './issue-priority-form.service';

describe('IssuePriority Form Service', () => {
  let service: IssuePriorityFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssuePriorityFormService);
  });

  describe('Service methods', () => {
    describe('createIssuePriorityFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssuePriorityFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issuePriority: expect.any(Object),
            issuePriorityLevel: expect.any(Object),
          })
        );
      });

      it('passing IIssuePriority should create a new form with FormGroup', () => {
        const formGroup = service.createIssuePriorityFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issuePriority: expect.any(Object),
            issuePriorityLevel: expect.any(Object),
          })
        );
      });
    });

    describe('getIssuePriority', () => {
      it('should return NewIssuePriority for default IssuePriority initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssuePriorityFormGroup(sampleWithNewData);

        const issuePriority = service.getIssuePriority(formGroup) as any;

        expect(issuePriority).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssuePriority for empty IssuePriority initial value', () => {
        const formGroup = service.createIssuePriorityFormGroup();

        const issuePriority = service.getIssuePriority(formGroup) as any;

        expect(issuePriority).toMatchObject({});
      });

      it('should return IIssuePriority', () => {
        const formGroup = service.createIssuePriorityFormGroup(sampleWithRequiredData);

        const issuePriority = service.getIssuePriority(formGroup) as any;

        expect(issuePriority).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssuePriority should not enable id FormControl', () => {
        const formGroup = service.createIssuePriorityFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssuePriority should disable id FormControl', () => {
        const formGroup = service.createIssuePriorityFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
