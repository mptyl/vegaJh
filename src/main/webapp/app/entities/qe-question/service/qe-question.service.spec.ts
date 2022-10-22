import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeQuestion } from '../qe-question.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-question.test-samples';

import { QeQuestionService } from './qe-question.service';

const requireRestSample: IQeQuestion = {
  ...sampleWithRequiredData,
};

describe('QeQuestion Service', () => {
  let service: QeQuestionService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeQuestion | IQeQuestion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeQuestionService);
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

    it('should create a QeQuestion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeQuestion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeQuestion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeQuestion', () => {
      const qeQuestion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeQuestion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeQuestion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeQuestion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeQuestion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeQuestionToCollectionIfMissing', () => {
      it('should add a QeQuestion to an empty array', () => {
        const qeQuestion: IQeQuestion = sampleWithRequiredData;
        expectedResult = service.addQeQuestionToCollectionIfMissing([], qeQuestion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeQuestion);
      });

      it('should not add a QeQuestion to an array that contains it', () => {
        const qeQuestion: IQeQuestion = sampleWithRequiredData;
        const qeQuestionCollection: IQeQuestion[] = [
          {
            ...qeQuestion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeQuestionToCollectionIfMissing(qeQuestionCollection, qeQuestion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeQuestion to an array that doesn't contain it", () => {
        const qeQuestion: IQeQuestion = sampleWithRequiredData;
        const qeQuestionCollection: IQeQuestion[] = [sampleWithPartialData];
        expectedResult = service.addQeQuestionToCollectionIfMissing(qeQuestionCollection, qeQuestion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeQuestion);
      });

      it('should add only unique QeQuestion to an array', () => {
        const qeQuestionArray: IQeQuestion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeQuestionCollection: IQeQuestion[] = [sampleWithRequiredData];
        expectedResult = service.addQeQuestionToCollectionIfMissing(qeQuestionCollection, ...qeQuestionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeQuestion: IQeQuestion = sampleWithRequiredData;
        const qeQuestion2: IQeQuestion = sampleWithPartialData;
        expectedResult = service.addQeQuestionToCollectionIfMissing([], qeQuestion, qeQuestion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeQuestion);
        expect(expectedResult).toContain(qeQuestion2);
      });

      it('should accept null and undefined values', () => {
        const qeQuestion: IQeQuestion = sampleWithRequiredData;
        expectedResult = service.addQeQuestionToCollectionIfMissing([], null, qeQuestion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeQuestion);
      });

      it('should return initial array if no QeQuestion is added', () => {
        const qeQuestionCollection: IQeQuestion[] = [sampleWithRequiredData];
        expectedResult = service.addQeQuestionToCollectionIfMissing(qeQuestionCollection, undefined, null);
        expect(expectedResult).toEqual(qeQuestionCollection);
      });
    });

    describe('compareQeQuestion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeQuestion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeQuestion(entity1, entity2);
        const compareResult2 = service.compareQeQuestion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeQuestion(entity1, entity2);
        const compareResult2 = service.compareQeQuestion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeQuestion(entity1, entity2);
        const compareResult2 = service.compareQeQuestion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
