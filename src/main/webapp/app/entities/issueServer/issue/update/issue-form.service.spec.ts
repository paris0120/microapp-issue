import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../issue.test-samples';

import { IssueFormService } from './issue-form.service';

describe('Issue Form Service', () => {
  let service: IssueFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueFormService);
  });

  describe('Service methods', () => {
    describe('createIssueFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssueFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            username: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            displayedUsername: expect.any(Object),
            issueTitle: expect.any(Object),
            issueContent: expect.any(Object),
            issueType: expect.any(Object),
            issueWorkflowStatus: expect.any(Object),
            issueWorkflowStatusKey: expect.any(Object),
            issuePriorityLevel: expect.any(Object),
            issueUuid: expect.any(Object),
            created: expect.any(Object),
            modified: expect.any(Object),
            deleted: expect.any(Object),
            closed: expect.any(Object),
          })
        );
      });

      it('passing IIssue should create a new form with FormGroup', () => {
        const formGroup = service.createIssueFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            username: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            displayedUsername: expect.any(Object),
            issueTitle: expect.any(Object),
            issueContent: expect.any(Object),
            issueType: expect.any(Object),
            issueWorkflowStatus: expect.any(Object),
            issueWorkflowStatusKey: expect.any(Object),
            issuePriorityLevel: expect.any(Object),
            issueUuid: expect.any(Object),
            created: expect.any(Object),
            modified: expect.any(Object),
            deleted: expect.any(Object),
            closed: expect.any(Object),
          })
        );
      });
    });

    describe('getIssue', () => {
      it('should return NewIssue for default Issue initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssueFormGroup(sampleWithNewData);

        const issue = service.getIssue(formGroup) as any;

        expect(issue).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssue for empty Issue initial value', () => {
        const formGroup = service.createIssueFormGroup();

        const issue = service.getIssue(formGroup) as any;

        expect(issue).toMatchObject({});
      });

      it('should return IIssue', () => {
        const formGroup = service.createIssueFormGroup(sampleWithRequiredData);

        const issue = service.getIssue(formGroup) as any;

        expect(issue).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssue should not enable id FormControl', () => {
        const formGroup = service.createIssueFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssue should disable id FormControl', () => {
        const formGroup = service.createIssueFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
