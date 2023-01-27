import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../issue-assignment.test-samples';

import { IssueAssignmentFormService } from './issue-assignment-form.service';

describe('IssueAssignment Form Service', () => {
  let service: IssueAssignmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueAssignmentFormService);
  });

  describe('Service methods', () => {
    describe('createIssueAssignmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssueAssignmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueId: expect.any(Object),
            issueUuid: expect.any(Object),
            username: expect.any(Object),
            issueAssignmentDisplayedUsername: expect.any(Object),
            issueRole: expect.any(Object),
            displayedIssueRole: expect.any(Object),
            created: expect.any(Object),
            modified: expect.any(Object),
            accepted: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });

      it('passing IIssueAssignment should create a new form with FormGroup', () => {
        const formGroup = service.createIssueAssignmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueId: expect.any(Object),
            issueUuid: expect.any(Object),
            username: expect.any(Object),
            issueAssignmentDisplayedUsername: expect.any(Object),
            issueRole: expect.any(Object),
            displayedIssueRole: expect.any(Object),
            created: expect.any(Object),
            modified: expect.any(Object),
            accepted: expect.any(Object),
            deleted: expect.any(Object),
          })
        );
      });
    });

    describe('getIssueAssignment', () => {
      it('should return NewIssueAssignment for default IssueAssignment initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssueAssignmentFormGroup(sampleWithNewData);

        const issueAssignment = service.getIssueAssignment(formGroup) as any;

        expect(issueAssignment).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssueAssignment for empty IssueAssignment initial value', () => {
        const formGroup = service.createIssueAssignmentFormGroup();

        const issueAssignment = service.getIssueAssignment(formGroup) as any;

        expect(issueAssignment).toMatchObject({});
      });

      it('should return IIssueAssignment', () => {
        const formGroup = service.createIssueAssignmentFormGroup(sampleWithRequiredData);

        const issueAssignment = service.getIssueAssignment(formGroup) as any;

        expect(issueAssignment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssueAssignment should not enable id FormControl', () => {
        const formGroup = service.createIssueAssignmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssueAssignment should disable id FormControl', () => {
        const formGroup = service.createIssueAssignmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
