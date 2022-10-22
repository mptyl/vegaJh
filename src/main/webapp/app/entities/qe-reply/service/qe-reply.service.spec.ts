import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IQeReply } from '../qe-reply.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-reply.test-samples';

import { QeReplyService, RestQeReply } from './qe-reply.service';

const requireRestSample: RestQeReply = {
  ...sampleWithRequiredData,
  dateMinValue: sampleWithRequiredData.dateMinValue?.format(DATE_FORMAT),
  dateMaxValue: sampleWithRequiredData.dateMaxValue?.format(DATE_FORMAT),
};

describe('QeReply Service', () => {
  let service: QeReplyService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeReply | IQeReply[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeReplyService);
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

    it('should create a QeReply', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeReply = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeReply).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeReply', () => {
      const qeReply = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeReply).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeReply', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeReply', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeReply', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeReplyToCollectionIfMissing', () => {
      it('should add a QeReply to an empty array', () => {
        const qeReply: IQeReply = sampleWithRequiredData;
        expectedResult = service.addQeReplyToCollectionIfMissing([], qeReply);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeReply);
      });

      it('should not add a QeReply to an array that contains it', () => {
        const qeReply: IQeReply = sampleWithRequiredData;
        const qeReplyCollection: IQeReply[] = [
          {
            ...qeReply,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeReplyToCollectionIfMissing(qeReplyCollection, qeReply);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeReply to an array that doesn't contain it", () => {
        const qeReply: IQeReply = sampleWithRequiredData;
        const qeReplyCollection: IQeReply[] = [sampleWithPartialData];
        expectedResult = service.addQeReplyToCollectionIfMissing(qeReplyCollection, qeReply);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeReply);
      });

      it('should add only unique QeReply to an array', () => {
        const qeReplyArray: IQeReply[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeReplyCollection: IQeReply[] = [sampleWithRequiredData];
        expectedResult = service.addQeReplyToCollectionIfMissing(qeReplyCollection, ...qeReplyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeReply: IQeReply = sampleWithRequiredData;
        const qeReply2: IQeReply = sampleWithPartialData;
        expectedResult = service.addQeReplyToCollectionIfMissing([], qeReply, qeReply2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeReply);
        expect(expectedResult).toContain(qeReply2);
      });

      it('should accept null and undefined values', () => {
        const qeReply: IQeReply = sampleWithRequiredData;
        expectedResult = service.addQeReplyToCollectionIfMissing([], null, qeReply, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeReply);
      });

      it('should return initial array if no QeReply is added', () => {
        const qeReplyCollection: IQeReply[] = [sampleWithRequiredData];
        expectedResult = service.addQeReplyToCollectionIfMissing(qeReplyCollection, undefined, null);
        expect(expectedResult).toEqual(qeReplyCollection);
      });
    });

    describe('compareQeReply', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeReply(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeReply(entity1, entity2);
        const compareResult2 = service.compareQeReply(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeReply(entity1, entity2);
        const compareResult2 = service.compareQeReply(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeReply(entity1, entity2);
        const compareResult2 = service.compareQeReply(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
