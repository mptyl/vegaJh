import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-radio-box.test-samples';

import { QeRadioBoxFormService } from './qe-radio-box-form.service';

describe('QeRadioBox Form Service', () => {
  let service: QeRadioBoxFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeRadioBoxFormService);
  });

  describe('Service methods', () => {
    describe('createQeRadioBoxFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeRadioBoxFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
            boxvalue: expect.any(Object),
            checked: expect.any(Object),
            position: expect.any(Object),
            qeRadioGroup: expect.any(Object),
          })
        );
      });

      it('passing IQeRadioBox should create a new form with FormGroup', () => {
        const formGroup = service.createQeRadioBoxFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
            boxvalue: expect.any(Object),
            checked: expect.any(Object),
            position: expect.any(Object),
            qeRadioGroup: expect.any(Object),
          })
        );
      });
    });

    describe('getQeRadioBox', () => {
      it('should return NewQeRadioBox for default QeRadioBox initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeRadioBoxFormGroup(sampleWithNewData);

        const qeRadioBox = service.getQeRadioBox(formGroup) as any;

        expect(qeRadioBox).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeRadioBox for empty QeRadioBox initial value', () => {
        const formGroup = service.createQeRadioBoxFormGroup();

        const qeRadioBox = service.getQeRadioBox(formGroup) as any;

        expect(qeRadioBox).toMatchObject({});
      });

      it('should return IQeRadioBox', () => {
        const formGroup = service.createQeRadioBoxFormGroup(sampleWithRequiredData);

        const qeRadioBox = service.getQeRadioBox(formGroup) as any;

        expect(qeRadioBox).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeRadioBox should not enable id FormControl', () => {
        const formGroup = service.createQeRadioBoxFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeRadioBox should disable id FormControl', () => {
        const formGroup = service.createQeRadioBoxFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
