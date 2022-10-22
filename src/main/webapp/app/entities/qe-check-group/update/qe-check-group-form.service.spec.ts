import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-check-group.test-samples';

import { QeCheckGroupFormService } from './qe-check-group-form.service';

describe('QeCheckGroup Form Service', () => {
  let service: QeCheckGroupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeCheckGroupFormService);
  });

  describe('Service methods', () => {
    describe('createQeCheckGroupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeCheckGroupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            radioboxGroupName: expect.any(Object),
            orientation: expect.any(Object),
            positio: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });

      it('passing IQeCheckGroup should create a new form with FormGroup', () => {
        const formGroup = service.createQeCheckGroupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            radioboxGroupName: expect.any(Object),
            orientation: expect.any(Object),
            positio: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });
    });

    describe('getQeCheckGroup', () => {
      it('should return NewQeCheckGroup for default QeCheckGroup initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeCheckGroupFormGroup(sampleWithNewData);

        const qeCheckGroup = service.getQeCheckGroup(formGroup) as any;

        expect(qeCheckGroup).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeCheckGroup for empty QeCheckGroup initial value', () => {
        const formGroup = service.createQeCheckGroupFormGroup();

        const qeCheckGroup = service.getQeCheckGroup(formGroup) as any;

        expect(qeCheckGroup).toMatchObject({});
      });

      it('should return IQeCheckGroup', () => {
        const formGroup = service.createQeCheckGroupFormGroup(sampleWithRequiredData);

        const qeCheckGroup = service.getQeCheckGroup(formGroup) as any;

        expect(qeCheckGroup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeCheckGroup should not enable id FormControl', () => {
        const formGroup = service.createQeCheckGroupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeCheckGroup should disable id FormControl', () => {
        const formGroup = service.createQeCheckGroupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
