import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IQeGroup } from '../qe-group.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../qe-group.test-samples';

import { QeGroupService } from './qe-group.service';

const requireRestSample: IQeGroup = {
  ...sampleWithRequiredData,
};

describe('QeGroup Service', () => {
  let service: QeGroupService;
  let httpMock: HttpTestingController;
  let expectedResult: IQeGroup | IQeGroup[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(QeGroupService);
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

    it('should create a QeGroup', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const qeGroup = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(qeGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a QeGroup', () => {
      const qeGroup = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(qeGroup).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a QeGroup', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of QeGroup', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a QeGroup', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addQeGroupToCollectionIfMissing', () => {
      it('should add a QeGroup to an empty array', () => {
        const qeGroup: IQeGroup = sampleWithRequiredData;
        expectedResult = service.addQeGroupToCollectionIfMissing([], qeGroup);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeGroup);
      });

      it('should not add a QeGroup to an array that contains it', () => {
        const qeGroup: IQeGroup = sampleWithRequiredData;
        const qeGroupCollection: IQeGroup[] = [
          {
            ...qeGroup,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addQeGroupToCollectionIfMissing(qeGroupCollection, qeGroup);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a QeGroup to an array that doesn't contain it", () => {
        const qeGroup: IQeGroup = sampleWithRequiredData;
        const qeGroupCollection: IQeGroup[] = [sampleWithPartialData];
        expectedResult = service.addQeGroupToCollectionIfMissing(qeGroupCollection, qeGroup);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeGroup);
      });

      it('should add only unique QeGroup to an array', () => {
        const qeGroupArray: IQeGroup[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const qeGroupCollection: IQeGroup[] = [sampleWithRequiredData];
        expectedResult = service.addQeGroupToCollectionIfMissing(qeGroupCollection, ...qeGroupArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const qeGroup: IQeGroup = sampleWithRequiredData;
        const qeGroup2: IQeGroup = sampleWithPartialData;
        expectedResult = service.addQeGroupToCollectionIfMissing([], qeGroup, qeGroup2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(qeGroup);
        expect(expectedResult).toContain(qeGroup2);
      });

      it('should accept null and undefined values', () => {
        const qeGroup: IQeGroup = sampleWithRequiredData;
        expectedResult = service.addQeGroupToCollectionIfMissing([], null, qeGroup, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(qeGroup);
      });

      it('should return initial array if no QeGroup is added', () => {
        const qeGroupCollection: IQeGroup[] = [sampleWithRequiredData];
        expectedResult = service.addQeGroupToCollectionIfMissing(qeGroupCollection, undefined, null);
        expect(expectedResult).toEqual(qeGroupCollection);
      });
    });

    describe('compareQeGroup', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareQeGroup(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareQeGroup(entity1, entity2);
        const compareResult2 = service.compareQeGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareQeGroup(entity1, entity2);
        const compareResult2 = service.compareQeGroup(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareQeGroup(entity1, entity2);
        const compareResult2 = service.compareQeGroup(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
