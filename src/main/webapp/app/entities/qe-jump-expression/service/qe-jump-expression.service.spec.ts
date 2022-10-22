import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeJumpExpression } from '../qe-jump-expression.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-jump-expression.test-samples';

import { QeJumpExpressionService } from './qe-jump-expression.service';

const requireRestSample: IQeJumpExpression = {
  ...sampleWithRequiredData,
};

describe('QeJumpExpression Service', () => {
  let service: QeJumpExpressionService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeJumpExpression | IQeJumpExpression[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeJumpExpressionService);
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

    it('should create a QeJumpExpression', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeJumpExpression = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeJumpExpression).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeJumpExpression', () => {
      const qeJumpExpression = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeJumpExpression).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeJumpExpression', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeJumpExpression', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeJumpExpression', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeJumpExpressionToCollectionIfMissing', () => {
      it('should add a QeJumpExpression to an empty array', () => {
        const qeJumpExpression: IQeJumpExpression = sampleWithRequiredData;
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing([], qeJumpExpression);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeJumpExpression);
      });

      it('should not add a QeJumpExpression to an array that contains it', () => {
        const qeJumpExpression: IQeJumpExpression = sampleWithRequiredData;
        const qeJumpExpressionCollection: IQeJumpExpression[] = [
          {
            ...qeJumpExpression,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing(qeJumpExpressionCollection, qeJumpExpression);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeJumpExpression to an array that doesn't contain it", () => {
        const qeJumpExpression: IQeJumpExpression = sampleWithRequiredData;
        const qeJumpExpressionCollection: IQeJumpExpression[] = [sampleWithPartialData];
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing(qeJumpExpressionCollection, qeJumpExpression);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeJumpExpression);
      });

      it('should add only unique QeJumpExpression to an array', () => {
        const qeJumpExpressionArray: IQeJumpExpression[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeJumpExpressionCollection: IQeJumpExpression[] = [sampleWithRequiredData];
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing(qeJumpExpressionCollection, ...qeJumpExpressionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeJumpExpression: IQeJumpExpression = sampleWithRequiredData;
        const qeJumpExpression2: IQeJumpExpression = sampleWithPartialData;
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing([], qeJumpExpression, qeJumpExpression2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeJumpExpression);
        expect(expectedResult).toContain(qeJumpExpression2);
      });

      it('should accept null and undefined values', () => {
        const qeJumpExpression: IQeJumpExpression = sampleWithRequiredData;
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing([], null, qeJumpExpression, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeJumpExpression);
      });

      it('should return initial array if no QeJumpExpression is added', () => {
        const qeJumpExpressionCollection: IQeJumpExpression[] = [sampleWithRequiredData];
        expectedResult = service.addQeJumpExpressionToCollectionIfMissing(qeJumpExpressionCollection, undefined, null);
        expect(expectedResult).toEqual(qeJumpExpressionCollection);
      });
    });

    describe('compareQeJumpExpression', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeJumpExpression(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeJumpExpression(entity1, entity2);
        const compareResult2 = service.compareQeJumpExpression(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeJumpExpression(entity1, entity2);
        const compareResult2 = service.compareQeJumpExpression(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeJumpExpression(entity1, entity2);
        const compareResult2 = service.compareQeJumpExpression(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
