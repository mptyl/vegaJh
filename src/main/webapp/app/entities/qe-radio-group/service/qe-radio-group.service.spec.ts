import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeRadioGroup } from '../qe-radio-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-radio-group.test-samples';

import { QeRadioGroupService } from './qe-radio-group.service';

const requireRestSample: IQeRadioGroup = {
  ...sampleWithRequiredData,
};

describe('QeRadioGroup Service', () => {
  let service: QeRadioGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeRadioGroup | IQeRadioGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeRadioGroupService);
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

    it('should create a QeRadioGroup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeRadioGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeRadioGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeRadioGroup', () => {
      const qeRadioGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeRadioGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeRadioGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeRadioGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeRadioGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeRadioGroupToCollectionIfMissing', () => {
      it('should add a QeRadioGroup to an empty array', () => {
        const qeRadioGroup: IQeRadioGroup = sampleWithRequiredData;
        expectedResult = service.addQeRadioGroupToCollectionIfMissing([], qeRadioGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeRadioGroup);
      });

      it('should not add a QeRadioGroup to an array that contains it', () => {
        const qeRadioGroup: IQeRadioGroup = sampleWithRequiredData;
        const qeRadioGroupCollection: IQeRadioGroup[] = [
          {
            ...qeRadioGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeRadioGroupToCollectionIfMissing(qeRadioGroupCollection, qeRadioGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeRadioGroup to an array that doesn't contain it", () => {
        const qeRadioGroup: IQeRadioGroup = sampleWithRequiredData;
        const qeRadioGroupCollection: IQeRadioGroup[] = [sampleWithPartialData];
        expectedResult = service.addQeRadioGroupToCollectionIfMissing(qeRadioGroupCollection, qeRadioGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeRadioGroup);
      });

      it('should add only unique QeRadioGroup to an array', () => {
        const qeRadioGroupArray: IQeRadioGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeRadioGroupCollection: IQeRadioGroup[] = [sampleWithRequiredData];
        expectedResult = service.addQeRadioGroupToCollectionIfMissing(qeRadioGroupCollection, ...qeRadioGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeRadioGroup: IQeRadioGroup = sampleWithRequiredData;
        const qeRadioGroup2: IQeRadioGroup = sampleWithPartialData;
        expectedResult = service.addQeRadioGroupToCollectionIfMissing([], qeRadioGroup, qeRadioGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeRadioGroup);
        expect(expectedResult).toContain(qeRadioGroup2);
      });

      it('should accept null and undefined values', () => {
        const qeRadioGroup: IQeRadioGroup = sampleWithRequiredData;
        expectedResult = service.addQeRadioGroupToCollectionIfMissing([], null, qeRadioGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeRadioGroup);
      });

      it('should return initial array if no QeRadioGroup is added', () => {
        const qeRadioGroupCollection: IQeRadioGroup[] = [sampleWithRequiredData];
        expectedResult = service.addQeRadioGroupToCollectionIfMissing(qeRadioGroupCollection, undefined, null);
        expect(expectedResult).toEqual(qeRadioGroupCollection);
      });
    });

    describe('compareQeRadioGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeRadioGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeRadioGroup(entity1, entity2);
        const compareResult2 = service.compareQeRadioGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeRadioGroup(entity1, entity2);
        const compareResult2 = service.compareQeRadioGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeRadioGroup(entity1, entity2);
        const compareResult2 = service.compareQeRadioGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
