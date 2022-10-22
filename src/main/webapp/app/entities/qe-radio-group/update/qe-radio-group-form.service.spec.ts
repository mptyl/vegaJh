import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-radio-group.test-samples';

import { QeRadioGroupFormService } from './qe-radio-group-form.service';

describe('QeRadioGroup Form Service', () => {
  let service: QeRadioGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeRadioGroupFormService);
  });

  describe('Service methods', () => {
    describe('createQeRadioGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeRadioGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            radioboxGroupName: expect.any(Object),
            orientation: expect.any(Object),
            position: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });

      it('passing IQeRadioGroup should create a new form with FormGroup', () => {
        const formGroup = service.createQeRadioGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            radioboxGroupName: expect.any(Object),
            orientation: expect.any(Object),
            position: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });
    });

    describe('getQeRadioGroup', () => {
      it('should return NewQeRadioGroup for default QeRadioGroup initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeRadioGroupFormGroup(sampleWithNewData);

        const qeRadioGroup = service.getQeRadioGroup(formGroup) as any;

        expect(qeRadioGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeRadioGroup for empty QeRadioGroup initial value', () => {
        const formGroup = service.createQeRadioGroupFormGroup();

        const qeRadioGroup = service.getQeRadioGroup(formGroup) as any;

        expect(qeRadioGroup).toMatchObject({});
      });

      it('should return IQeRadioGroup', () => {
        const formGroup = service.createQeRadioGroupFormGroup(sampleWithRequiredData);

        const qeRadioGroup = service.getQeRadioGroup(formGroup) as any;

        expect(qeRadioGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeRadioGroup should not enable id FormControl', () => {
        const formGroup = service.createQeRadioGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeRadioGroup should disable id FormControl', () => {
        const formGroup = service.createQeRadioGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
