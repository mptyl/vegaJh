import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-group.test-samples';

import { QeGroupFormService } from './qe-group-form.service';

describe('QeGroup Form Service', () => {
  let service: QeGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeGroupFormService);
  });

  describe('Service methods', () => {
    describe('createQeGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            random: expect.any(Object),
            position: expect.any(Object),
            questionnaire: expect.any(Object),
          })
        );
      });

      it('passing IQeGroup should create a new form with FormGroup', () => {
        const formGroup = service.createQeGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            random: expect.any(Object),
            position: expect.any(Object),
            questionnaire: expect.any(Object),
          })
        );
      });
    });

    describe('getQeGroup', () => {
      it('should return NewQeGroup for default QeGroup initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeGroupFormGroup(sampleWithNewData);

        const qeGroup = service.getQeGroup(formGroup) as any;

        expect(qeGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeGroup for empty QeGroup initial value', () => {
        const formGroup = service.createQeGroupFormGroup();

        const qeGroup = service.getQeGroup(formGroup) as any;

        expect(qeGroup).toMatchObject({});
      });

      it('should return IQeGroup', () => {
        const formGroup = service.createQeGroupFormGroup(sampleWithRequiredData);

        const qeGroup = service.getQeGroup(formGroup) as any;

        expect(qeGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeGroup should not enable id FormControl', () => {
        const formGroup = service.createQeGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeGroup should disable id FormControl', () => {
        const formGroup = service.createQeGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
