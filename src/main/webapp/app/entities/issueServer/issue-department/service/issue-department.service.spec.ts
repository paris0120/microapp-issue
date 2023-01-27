import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIssueDepartment } from '../issue-department.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../issue-department.test-samples';

import { IssueDepartmentService } from './issue-department.service';

const requireRestSample: IIssueDepartment = {
  ...sampleWithRequiredData,
};

describe('IssueDepartment Service', () => {
  let service: IssueDepartmentService;
  let httpMock: HttpTestingController;
  let expectedResult: IIssueDepartment | IIssueDepartment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IssueDepartmentService);
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

    it('should create a IssueDepartment', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const issueDepartment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(issueDepartment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IssueDepartment', () => {
      const issueDepartment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(issueDepartment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IssueDepartment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IssueDepartment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IssueDepartment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIssueDepartmentToCollectionIfMissing', () => {
      it('should add a IssueDepartment to an empty array', () => {
        const issueDepartment: IIssueDepartment = sampleWithRequiredData;
        expectedResult = service.addIssueDepartmentToCollectionIfMissing([], issueDepartment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issueDepartment);
      });

      it('should not add a IssueDepartment to an array that contains it', () => {
        const issueDepartment: IIssueDepartment = sampleWithRequiredData;
        const issueDepartmentCollection: IIssueDepartment[] = [
          {
            ...issueDepartment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIssueDepartmentToCollectionIfMissing(issueDepartmentCollection, issueDepartment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IssueDepartment to an array that doesn't contain it", () => {
        const issueDepartment: IIssueDepartment = sampleWithRequiredData;
        const issueDepartmentCollection: IIssueDepartment[] = [sampleWithPartialData];
        expectedResult = service.addIssueDepartmentToCollectionIfMissing(issueDepartmentCollection, issueDepartment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issueDepartment);
      });

      it('should add only unique IssueDepartment to an array', () => {
        const issueDepartmentArray: IIssueDepartment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const issueDepartmentCollection: IIssueDepartment[] = [sampleWithRequiredData];
        expectedResult = service.addIssueDepartmentToCollectionIfMissing(issueDepartmentCollection, ...issueDepartmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const issueDepartment: IIssueDepartment = sampleWithRequiredData;
        const issueDepartment2: IIssueDepartment = sampleWithPartialData;
        expectedResult = service.addIssueDepartmentToCollectionIfMissing([], issueDepartment, issueDepartment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(issueDepartment);
        expect(expectedResult).toContain(issueDepartment2);
      });

      it('should accept null and undefined values', () => {
        const issueDepartment: IIssueDepartment = sampleWithRequiredData;
        expectedResult = service.addIssueDepartmentToCollectionIfMissing([], null, issueDepartment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(issueDepartment);
      });

      it('should return initial array if no IssueDepartment is added', () => {
        const issueDepartmentCollection: IIssueDepartment[] = [sampleWithRequiredData];
        expectedResult = service.addIssueDepartmentToCollectionIfMissing(issueDepartmentCollection, undefined, null);
        expect(expectedResult).toEqual(issueDepartmentCollection);
      });
    });

    describe('compareIssueDepartment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIssueDepartment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIssueDepartment(entity1, entity2);
        const compareResult2 = service.compareIssueDepartment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIssueDepartment(entity1, entity2);
        const compareResult2 = service.compareIssueDepartment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIssueDepartment(entity1, entity2);
        const compareResult2 = service.compareIssueDepartment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
