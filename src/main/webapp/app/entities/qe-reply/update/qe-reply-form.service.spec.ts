import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-reply.test-samples';

import { QeReplyFormService } from './qe-reply-form.service';

describe('QeReply Form Service', () => {
  let service: QeReplyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeReplyFormService);
  });

  describe('Service methods', () => {
    describe('createQeReplyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeReplyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            title: expect.any(Object),
            label: expect.any(Object),
            replyType: expect.any(Object),
            dateMinValue: expect.any(Object),
            dateMaxValue: expect.any(Object),
            integerMinValue: expect.any(Object),
            integerMaxValue: expect.any(Object),
            doubleMinValue: expect.any(Object),
            doubleMaxValue: expect.any(Object),
            rangeMinValue: expect.any(Object),
            rangeMaxValue: expect.any(Object),
            selectList: expect.any(Object),
            step: expect.any(Object),
            replyPattern: expect.any(Object),
            multiple: expect.any(Object),
            placeHolder: expect.any(Object),
            replyRequired: expect.any(Object),
            booleanValue: expect.any(Object),
            withComment: expect.any(Object),
            position: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });

      it('passing IQeReply should create a new form with FormGroup', () => {
        const formGroup = service.createQeReplyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            title: expect.any(Object),
            label: expect.any(Object),
            replyType: expect.any(Object),
            dateMinValue: expect.any(Object),
            dateMaxValue: expect.any(Object),
            integerMinValue: expect.any(Object),
            integerMaxValue: expect.any(Object),
            doubleMinValue: expect.any(Object),
            doubleMaxValue: expect.any(Object),
            rangeMinValue: expect.any(Object),
            rangeMaxValue: expect.any(Object),
            selectList: expect.any(Object),
            step: expect.any(Object),
            replyPattern: expect.any(Object),
            multiple: expect.any(Object),
            placeHolder: expect.any(Object),
            replyRequired: expect.any(Object),
            booleanValue: expect.any(Object),
            withComment: expect.any(Object),
            position: expect.any(Object),
            qeQuestion: expect.any(Object),
          })
        );
      });
    });

    describe('getQeReply', () => {
      it('should return NewQeReply for default QeReply initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeReplyFormGroup(sampleWithNewData);

        const qeReply = service.getQeReply(formGroup) as any;

        expect(qeReply).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeReply for empty QeReply initial value', () => {
        const formGroup = service.createQeReplyFormGroup();

        const qeReply = service.getQeReply(formGroup) as any;

        expect(qeReply).toMatchObject({});
      });

      it('should return IQeReply', () => {
        const formGroup = service.createQeReplyFormGroup(sampleWithRequiredData);

        const qeReply = service.getQeReply(formGroup) as any;

        expect(qeReply).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeReply should not enable id FormControl', () => {
        const formGroup = service.createQeReplyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeReply should disable id FormControl', () => {
        const formGroup = service.createQeReplyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
