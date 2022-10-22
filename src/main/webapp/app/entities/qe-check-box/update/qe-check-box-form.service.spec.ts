import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-check-box.test-samples';

import { QeCheckBoxFormService } from './qe-check-box-form.service';

describe('QeCheckBox Form Service', () => {
  let service: QeCheckBoxFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeCheckBoxFormService);
  });

  describe('Service methods', () => {
    describe('createQeCheckBoxFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeCheckBoxFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
            boxvalue: expect.any(Object),
            checked: expect.any(Object),
            position: expect.any(Object),
            qeCheckGroup: expect.any(Object),
          })
        );
      });

      it('passing IQeCheckBox should create a new form with FormGroup', () => {
        const formGroup = service.createQeCheckBoxFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            label: expect.any(Object),
            boxvalue: expect.any(Object),
            checked: expect.any(Object),
            position: expect.any(Object),
            qeCheckGroup: expect.any(Object),
          })
        );
      });
    });

    describe('getQeCheckBox', () => {
      it('should return NewQeCheckBox for default QeCheckBox initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeCheckBoxFormGroup(sampleWithNewData);

        const qeCheckBox = service.getQeCheckBox(formGroup) as any;

        expect(qeCheckBox).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeCheckBox for empty QeCheckBox initial value', () => {
        const formGroup = service.createQeCheckBoxFormGroup();

        const qeCheckBox = service.getQeCheckBox(formGroup) as any;

        expect(qeCheckBox).toMatchObject({});
      });

      it('should return IQeCheckBox', () => {
        const formGroup = service.createQeCheckBoxFormGroup(sampleWithRequiredData);

        const qeCheckBox = service.getQeCheckBox(formGroup) as any;

        expect(qeCheckBox).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeCheckBox should not enable id FormControl', () => {
        const formGroup = service.createQeCheckBoxFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeCheckBox should disable id FormControl', () => {
        const formGroup = service.createQeCheckBoxFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
