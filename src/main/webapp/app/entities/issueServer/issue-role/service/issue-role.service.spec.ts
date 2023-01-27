import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIssueRole } from '../issue-role.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../issue-role.test-samples';

import { IssueRoleService } from './issue-role.service';

const requireRestSample: IIssueRole = {
  ...sampleWithRequiredData,
};

describe('IssueRole Service', () => {
  let service: IssueRoleService;
  let httpMock: HttpTestingController;
  let expectedResult: IIssueRole | IIssueRole[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssueRoleService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a IssueRole', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const issueRole = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(issueRole).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IssueRole', () => {
      const issueRole = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(issueRole).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IssueRole', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IssueRole', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IssueRole', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIssueRoleToCollectionIfMissing', () => {
      it('should add a IssueRole to an empty array', () => {
        const issueRole: IIssueRole = sampleWithRequiredData;
        expectedResult = service.addIssueRoleToCollectionIfMissing([], issueRole);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issueRole);
      });

      it('should not add a IssueRole to an array that contains it', () => {
        const issueRole: IIssueRole = sampleWithRequiredData;
        const issueRoleCollection: IIssueRole[] = [
          {
            ...issueRole,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIssueRoleToCollectionIfMissing(issueRoleCollection, issueRole);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IssueRole to an array that doesn't contain it", () => {
        const issueRole: IIssueRole = sampleWithRequiredData;
        const issueRoleCollection: IIssueRole[] = [sampleWithPartialData];
        expectedResult = service.addIssueRoleToCollectionIfMissing(issueRoleCollection, issueRole);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issueRole);
      });

      it('should add only unique IssueRole to an array', () => {
        const issueRoleArray: IIssueRole[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const issueRoleCollection: IIssueRole[] = [sampleWithRequiredData];
        expectedResult = service.addIssueRoleToCollectionIfMissing(issueRoleCollection, ...issueRoleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issueRole: IIssueRole = sampleWithRequiredData;
        const issueRole2: IIssueRole = sampleWithPartialData;
        expectedResult = service.addIssueRoleToCollectionIfMissing([], issueRole, issueRole2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issueRole);
        expect(expectedResult).toContain(issueRole2);
      });

      it('should accept null and undefined values', () => {
        const issueRole: IIssueRole = sampleWithRequiredData;
        expectedResult = service.addIssueRoleToCollectionIfMissing([], null, issueRole, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issueRole);
      });

      it('should return initial array if no IssueRole is added', () => {
        const issueRoleCollection: IIssueRole[] = [sampleWithRequiredData];
        expectedResult = service.addIssueRoleToCollectionIfMissing(issueRoleCollection, undefined, null);
        expect(expectedResult).toEqual(issueRoleCollection);
      });
    });

    describe('compareIssueRole', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIssueRole(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIssueRole(entity1, entity2);
        const compareResult2 = service.compareIssueRole(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIssueRole(entity1, entity2);
        const compareResult2 = service.compareIssueRole(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIssueRole(entity1, entity2);
        const compareResult2 = service.compareIssueRole(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
