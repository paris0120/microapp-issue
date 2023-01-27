import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../issue-role.test-samples';

import { IssueRoleFormService } from './issue-role-form.service';

describe('IssueRole Form Service', () => {
  let service: IssueRoleFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IssueRoleFormService);
  });

  describe('Service methods', () => {
    describe('createIssueRoleFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIssueRoleFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueRoleKey: expect.any(Object),
            issueRole: expect.any(Object),
          })
        );
      });

      it('passing IIssueRole should create a new form with FormGroup', () => {
        const formGroup = service.createIssueRoleFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            issueRoleKey: expect.any(Object),
            issueRole: expect.any(Object),
          })
        );
      });
    });

    describe('getIssueRole', () => {
      it('should return NewIssueRole for default IssueRole initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIssueRoleFormGroup(sampleWithNewData);

        const issueRole = service.getIssueRole(formGroup) as any;

        expect(issueRole).toMatchObject(sampleWithNewData);
      });

      it('should return NewIssueRole for empty IssueRole initial value', () => {
        const formGroup = service.createIssueRoleFormGroup();

        const issueRole = service.getIssueRole(formGroup) as any;

        expect(issueRole).toMatchObject({});
      });

      it('should return IIssueRole', () => {
        const formGroup = service.createIssueRoleFormGroup(sampleWithRequiredData);

        const issueRole = service.getIssueRole(formGroup) as any;

        expect(issueRole).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIssueRole should not enable id FormControl', () => {
        const formGroup = service.createIssueRoleFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIssueRole should disable id FormControl', () => {
        const formGroup = service.createIssueRoleFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
