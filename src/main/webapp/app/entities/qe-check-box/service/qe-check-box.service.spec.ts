import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeCheckBox } from '../qe-check-box.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-check-box.test-samples';

import { QeCheckBoxService } from './qe-check-box.service';

const requireRestSample: IQeCheckBox = {
  ...sampleWithRequiredData,
};

describe('QeCheckBox Service', () => {
  let service: QeCheckBoxService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeCheckBox | IQeCheckBox[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeCheckBoxService);
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

    it('should create a QeCheckBox', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeCheckBox = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeCheckBox).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeCheckBox', () => {
      const qeCheckBox = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeCheckBox).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeCheckBox', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeCheckBox', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeCheckBox', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeCheckBoxToCollectionIfMissing', () => {
      it('should add a QeCheckBox to an empty array', () => {
        const qeCheckBox: IQeCheckBox = sampleWithRequiredData;
        expectedResult = service.addQeCheckBoxToCollectionIfMissing([], qeCheckBox);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeCheckBox);
      });

      it('should not add a QeCheckBox to an array that contains it', () => {
        const qeCheckBox: IQeCheckBox = sampleWithRequiredData;
        const qeCheckBoxCollection: IQeCheckBox[] = [
          {
            ...qeCheckBox,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeCheckBoxToCollectionIfMissing(qeCheckBoxCollection, qeCheckBox);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeCheckBox to an array that doesn't contain it", () => {
        const qeCheckBox: IQeCheckBox = sampleWithRequiredData;
        const qeCheckBoxCollection: IQeCheckBox[] = [sampleWithPartialData];
        expectedResult = service.addQeCheckBoxToCollectionIfMissing(qeCheckBoxCollection, qeCheckBox);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeCheckBox);
      });

      it('should add only unique QeCheckBox to an array', () => {
        const qeCheckBoxArray: IQeCheckBox[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeCheckBoxCollection: IQeCheckBox[] = [sampleWithRequiredData];
        expectedResult = service.addQeCheckBoxToCollectionIfMissing(qeCheckBoxCollection, ...qeCheckBoxArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeCheckBox: IQeCheckBox = sampleWithRequiredData;
        const qeCheckBox2: IQeCheckBox = sampleWithPartialData;
        expectedResult = service.addQeCheckBoxToCollectionIfMissing([], qeCheckBox, qeCheckBox2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeCheckBox);
        expect(expectedResult).toContain(qeCheckBox2);
      });

      it('should accept null and undefined values', () => {
        const qeCheckBox: IQeCheckBox = sampleWithRequiredData;
        expectedResult = service.addQeCheckBoxToCollectionIfMissing([], null, qeCheckBox, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeCheckBox);
      });

      it('should return initial array if no QeCheckBox is added', () => {
        const qeCheckBoxCollection: IQeCheckBox[] = [sampleWithRequiredData];
        expectedResult = service.addQeCheckBoxToCollectionIfMissing(qeCheckBoxCollection, undefined, null);
        expect(expectedResult).toEqual(qeCheckBoxCollection);
      });
    });

    describe('compareQeCheckBox', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeCheckBox(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeCheckBox(entity1, entity2);
        const compareResult2 = service.compareQeCheckBox(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeCheckBox(entity1, entity2);
        const compareResult2 = service.compareQeCheckBox(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeCheckBox(entity1, entity2);
        const compareResult2 = service.compareQeCheckBox(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
