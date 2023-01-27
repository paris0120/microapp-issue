import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIssuePriority } from '../issue-priority.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../issue-priority.test-samples';

import { IssuePriorityService } from './issue-priority.service';

const requireRestSample: IIssuePriority = {
  ...sampleWithRequiredData,
};

describe('IssuePriority Service', () => {
  let service: IssuePriorityService;
  let httpMock: HttpTestingController;
  let expectedResult: IIssuePriority | IIssuePriority[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssuePriorityService);
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

    it('should create a IssuePriority', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const issuePriority = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(issuePriority).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IssuePriority', () => {
      const issuePriority = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(issuePriority).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IssuePriority', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IssuePriority', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IssuePriority', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIssuePriorityToCollectionIfMissing', () => {
      it('should add a IssuePriority to an empty array', () => {
        const issuePriority: IIssuePriority = sampleWithRequiredData;
        expectedResult = service.addIssuePriorityToCollectionIfMissing([], issuePriority);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issuePriority);
      });

      it('should not add a IssuePriority to an array that contains it', () => {
        const issuePriority: IIssuePriority = sampleWithRequiredData;
        const issuePriorityCollection: IIssuePriority[] = [
          {
            ...issuePriority,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIssuePriorityToCollectionIfMissing(issuePriorityCollection, issuePriority);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IssuePriority to an array that doesn't contain it", () => {
        const issuePriority: IIssuePriority = sampleWithRequiredData;
        const issuePriorityCollection: IIssuePriority[] = [sampleWithPartialData];
        expectedResult = service.addIssuePriorityToCollectionIfMissing(issuePriorityCollection, issuePriority);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issuePriority);
      });

      it('should add only unique IssuePriority to an array', () => {
        const issuePriorityArray: IIssuePriority[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const issuePriorityCollection: IIssuePriority[] = [sampleWithRequiredData];
        expectedResult = service.addIssuePriorityToCollectionIfMissing(issuePriorityCollection, ...issuePriorityArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issuePriority: IIssuePriority = sampleWithRequiredData;
        const issuePriority2: IIssuePriority = sampleWithPartialData;
        expectedResult = service.addIssuePriorityToCollectionIfMissing([], issuePriority, issuePriority2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issuePriority);
        expect(expectedResult).toContain(issuePriority2);
      });

      it('should accept null and undefined values', () => {
        const issuePriority: IIssuePriority = sampleWithRequiredData;
        expectedResult = service.addIssuePriorityToCollectionIfMissing([], null, issuePriority, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issuePriority);
      });

      it('should return initial array if no IssuePriority is added', () => {
        const issuePriorityCollection: IIssuePriority[] = [sampleWithRequiredData];
        expectedResult = service.addIssuePriorityToCollectionIfMissing(issuePriorityCollection, undefined, null);
        expect(expectedResult).toEqual(issuePriorityCollection);
      });
    });

    describe('compareIssuePriority', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIssuePriority(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIssuePriority(entity1, entity2);
        const compareResult2 = service.compareIssuePriority(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIssuePriority(entity1, entity2);
        const compareResult2 = service.compareIssuePriority(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIssuePriority(entity1, entity2);
        const compareResult2 = service.compareIssuePriority(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
