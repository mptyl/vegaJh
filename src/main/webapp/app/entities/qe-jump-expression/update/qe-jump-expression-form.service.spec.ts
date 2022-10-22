import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-jump-expression.test-samples';

import { QeJumpExpressionFormService } from './qe-jump-expression-form.service';

describe('QeJumpExpression Form Service', () => {
  let service: QeJumpExpressionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeJumpExpressionFormService);
  });

  describe('Service methods', () => {
    describe('createQeJumpExpressionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeJumpExpressionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            expression: expect.any(Object),
            jumpTo: expect.any(Object),
            position: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });

      it('passing IQeJumpExpression should create a new form with FormGroup', () => {
        const formGroup = service.createQeJumpExpressionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            expression: expect.any(Object),
            jumpTo: expect.any(Object),
            position: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });
    });

    describe('getQeJumpExpression', () => {
      it('should return NewQeJumpExpression for default QeJumpExpression initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeJumpExpressionFormGroup(sampleWithNewData);

        const qeJumpExpression = service.getQeJumpExpression(formGroup) as any;

        expect(qeJumpExpression).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeJumpExpression for empty QeJumpExpression initial value', () => {
        const formGroup = service.createQeJumpExpressionFormGroup();

        const qeJumpExpression = service.getQeJumpExpression(formGroup) as any;

        expect(qeJumpExpression).toMatchObject({});
      });

      it('should return IQeJumpExpression', () => {
        const formGroup = service.createQeJumpExpressionFormGroup(sampleWithRequiredData);

        const qeJumpExpression = service.getQeJumpExpression(formGroup) as any;

        expect(qeJumpExpression).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeJumpExpression should not enable id FormControl', () => {
        const formGroup = service.createQeJumpExpressionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeJumpExpression should disable id FormControl', () => {
        const formGroup = service.createQeJumpExpressionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
