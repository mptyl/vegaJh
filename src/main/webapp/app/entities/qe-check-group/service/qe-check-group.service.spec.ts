import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeCheckGroup } from '../qe-check-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-check-group.test-samples';

import { QeCheckGroupService } from './qe-check-group.service';

const requireRestSample: IQeCheckGroup = {
  ...sampleWithRequiredData,
};

describe('QeCheckGroup Service', () => {
  let service: QeCheckGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeCheckGroup | IQeCheckGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeCheckGroupService);
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

    it('should create a QeCheckGroup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeCheckGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeCheckGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeCheckGroup', () => {
      const qeCheckGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeCheckGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeCheckGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeCheckGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeCheckGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeCheckGroupToCollectionIfMissing', () => {
      it('should add a QeCheckGroup to an empty array', () => {
        const qeCheckGroup: IQeCheckGroup = sampleWithRequiredData;
        expectedResult = service.addQeCheckGroupToCollectionIfMissing([], qeCheckGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeCheckGroup);
      });

      it('should not add a QeCheckGroup to an array that contains it', () => {
        const qeCheckGroup: IQeCheckGroup = sampleWithRequiredData;
        const qeCheckGroupCollection: IQeCheckGroup[] = [
          {
            ...qeCheckGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeCheckGroupToCollectionIfMissing(qeCheckGroupCollection, qeCheckGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeCheckGroup to an array that doesn't contain it", () => {
        const qeCheckGroup: IQeCheckGroup = sampleWithRequiredData;
        const qeCheckGroupCollection: IQeCheckGroup[] = [sampleWithPartialData];
        expectedResult = service.addQeCheckGroupToCollectionIfMissing(qeCheckGroupCollection, qeCheckGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeCheckGroup);
      });

      it('should add only unique QeCheckGroup to an array', () => {
        const qeCheckGroupArray: IQeCheckGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeCheckGroupCollection: IQeCheckGroup[] = [sampleWithRequiredData];
        expectedResult = service.addQeCheckGroupToCollectionIfMissing(qeCheckGroupCollection, ...qeCheckGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeCheckGroup: IQeCheckGroup = sampleWithRequiredData;
        const qeCheckGroup2: IQeCheckGroup = sampleWithPartialData;
        expectedResult = service.addQeCheckGroupToCollectionIfMissing([], qeCheckGroup, qeCheckGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeCheckGroup);
        expect(expectedResult).toContain(qeCheckGroup2);
      });

      it('should accept null and undefined values', () => {
        const qeCheckGroup: IQeCheckGroup = sampleWithRequiredData;
        expectedResult = service.addQeCheckGroupToCollectionIfMissing([], null, qeCheckGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeCheckGroup);
      });

      it('should return initial array if no QeCheckGroup is added', () => {
        const qeCheckGroupCollection: IQeCheckGroup[] = [sampleWithRequiredData];
        expectedResult = service.addQeCheckGroupToCollectionIfMissing(qeCheckGroupCollection, undefined, null);
        expect(expectedResult).toEqual(qeCheckGroupCollection);
      });
    });

    describe('compareQeCheckGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeCheckGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeCheckGroup(entity1, entity2);
        const compareResult2 = service.compareQeCheckGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeCheckGroup(entity1, entity2);
        const compareResult2 = service.compareQeCheckGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeCheckGroup(entity1, entity2);
        const compareResult2 = service.compareQeCheckGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
