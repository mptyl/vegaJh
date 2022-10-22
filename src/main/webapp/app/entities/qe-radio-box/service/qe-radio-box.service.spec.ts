import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeRadioBox } from '../qe-radio-box.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-radio-box.test-samples';

import { QeRadioBoxService } from './qe-radio-box.service';

const requireRestSample: IQeRadioBox = {
  ...sampleWithRequiredData,
};

describe('QeRadioBox Service', () => {
  let service: QeRadioBoxService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeRadioBox | IQeRadioBox[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeRadioBoxService);
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

    it('should create a QeRadioBox', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeRadioBox = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeRadioBox).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeRadioBox', () => {
      const qeRadioBox = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeRadioBox).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeRadioBox', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeRadioBox', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeRadioBox', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeRadioBoxToCollectionIfMissing', () => {
      it('should add a QeRadioBox to an empty array', () => {
        const qeRadioBox: IQeRadioBox = sampleWithRequiredData;
        expectedResult = service.addQeRadioBoxToCollectionIfMissing([], qeRadioBox);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeRadioBox);
      });

      it('should not add a QeRadioBox to an array that contains it', () => {
        const qeRadioBox: IQeRadioBox = sampleWithRequiredData;
        const qeRadioBoxCollection: IQeRadioBox[] = [
          {
            ...qeRadioBox,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeRadioBoxToCollectionIfMissing(qeRadioBoxCollection, qeRadioBox);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeRadioBox to an array that doesn't contain it", () => {
        const qeRadioBox: IQeRadioBox = sampleWithRequiredData;
        const qeRadioBoxCollection: IQeRadioBox[] = [sampleWithPartialData];
        expectedResult = service.addQeRadioBoxToCollectionIfMissing(qeRadioBoxCollection, qeRadioBox);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeRadioBox);
      });

      it('should add only unique QeRadioBox to an array', () => {
        const qeRadioBoxArray: IQeRadioBox[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeRadioBoxCollection: IQeRadioBox[] = [sampleWithRequiredData];
        expectedResult = service.addQeRadioBoxToCollectionIfMissing(qeRadioBoxCollection, ...qeRadioBoxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeRadioBox: IQeRadioBox = sampleWithRequiredData;
        const qeRadioBox2: IQeRadioBox = sampleWithPartialData;
        expectedResult = service.addQeRadioBoxToCollectionIfMissing([], qeRadioBox, qeRadioBox2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeRadioBox);
        expect(expectedResult).toContain(qeRadioBox2);
      });

      it('should accept null and undefined values', () => {
        const qeRadioBox: IQeRadioBox = sampleWithRequiredData;
        expectedResult = service.addQeRadioBoxToCollectionIfMissing([], null, qeRadioBox, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeRadioBox);
      });

      it('should return initial array if no QeRadioBox is added', () => {
        const qeRadioBoxCollection: IQeRadioBox[] = [sampleWithRequiredData];
        expectedResult = service.addQeRadioBoxToCollectionIfMissing(qeRadioBoxCollection, undefined, null);
        expect(expectedResult).toEqual(qeRadioBoxCollection);
      });
    });

    describe('compareQeRadioBox', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeRadioBox(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeRadioBox(entity1, entity2);
        const compareResult2 = service.compareQeRadioBox(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeRadioBox(entity1, entity2);
        const compareResult2 = service.compareQeRadioBox(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeRadioBox(entity1, entity2);
        const compareResult2 = service.compareQeRadioBox(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
