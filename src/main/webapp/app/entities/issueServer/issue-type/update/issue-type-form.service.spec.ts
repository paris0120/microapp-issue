import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../issue-type.test-samples';

import { IssueTypeFormService } from './issue-type-form.service';

describe('IssueType Form Service', () => {
  let service: IssueTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueTypeFormService);
  });

  describe('Service methods', () => {
    describe('createIssueTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssueTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueTypeKey: expect.any(Object),
            issueTypeWeight: expect.any(Object),
            issueType: expect.any(Object),
            isActive: expect.any(Object),
          })
        );
      });

      it('passing IIssueType should create a new form with FormGroup', () => {
        const formGroup = service.createIssueTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueTypeKey: expect.any(Object),
            issueTypeWeight: expect.any(Object),
            issueType: expect.any(Object),
            isActive: expect.any(Object),
          })
        );
      });
    });

    describe('getIssueType', () => {
      it('should return NewIssueType for default IssueType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssueTypeFormGroup(sampleWithNewData);

        const issueType = service.getIssueType(formGroup) as any;

        expect(issueType).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssueType for empty IssueType initial value', () => {
        const formGroup = service.createIssueTypeFormGroup();

        const issueType = service.getIssueType(formGroup) as any;

        expect(issueType).toMatchObject({});
      });

      it('should return IIssueType', () => {
        const formGroup = service.createIssueTypeFormGroup(sampleWithRequiredData);

        const issueType = service.getIssueType(formGroup) as any;

        expect(issueType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssueType should not enable id FormControl', () => {
        const formGroup = service.createIssueTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssueType should disable id FormControl', () => {
        const formGroup = service.createIssueTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
