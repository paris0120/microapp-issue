import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIssueType } from '../issue-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../issue-type.test-samples';

import { IssueTypeService } from './issue-type.service';

const requireRestSample: IIssueType = {
  ...sampleWithRequiredData,
};

describe('IssueType Service', () => {
  let service: IssueTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IIssueType | IIssueType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssueTypeService);
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

    it('should create a IssueType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const issueType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(issueType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IssueType', () => {
      const issueType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(issueType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IssueType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IssueType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IssueType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIssueTypeToCollectionIfMissing', () => {
      it('should add a IssueType to an empty array', () => {
        const issueType: IIssueType = sampleWithRequiredData;
        expectedResult = service.addIssueTypeToCollectionIfMissing([], issueType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issueType);
      });

      it('should not add a IssueType to an array that contains it', () => {
        const issueType: IIssueType = sampleWithRequiredData;
        const issueTypeCollection: IIssueType[] = [
          {
            ...issueType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIssueTypeToCollectionIfMissing(issueTypeCollection, issueType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IssueType to an array that doesn't contain it", () => {
        const issueType: IIssueType = sampleWithRequiredData;
        const issueTypeCollection: IIssueType[] = [sampleWithPartialData];
        expectedResult = service.addIssueTypeToCollectionIfMissing(issueTypeCollection, issueType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issueType);
      });

      it('should add only unique IssueType to an array', () => {
        const issueTypeArray: IIssueType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const issueTypeCollection: IIssueType[] = [sampleWithRequiredData];
        expectedResult = service.addIssueTypeToCollectionIfMissing(issueTypeCollection, ...issueTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issueType: IIssueType = sampleWithRequiredData;
        const issueType2: IIssueType = sampleWithPartialData;
        expectedResult = service.addIssueTypeToCollectionIfMissing([], issueType, issueType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issueType);
        expect(expectedResult).toContain(issueType2);
      });

      it('should accept null and undefined values', () => {
        const issueType: IIssueType = sampleWithRequiredData;
        expectedResult = service.addIssueTypeToCollectionIfMissing([], null, issueType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issueType);
      });

      it('should return initial array if no IssueType is added', () => {
        const issueTypeCollection: IIssueType[] = [sampleWithRequiredData];
        expectedResult = service.addIssueTypeToCollectionIfMissing(issueTypeCollection, undefined, null);
        expect(expectedResult).toEqual(issueTypeCollection);
      });
    });

    describe('compareIssueType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIssueType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIssueType(entity1, entity2);
        const compareResult2 = service.compareIssueType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIssueType(entity1, entity2);
        const compareResult2 = service.compareIssueType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIssueType(entity1, entity2);
        const compareResult2 = service.compareIssueType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
