import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../qe-question.test-samples';

import { QeQuestionFormService } from './qe-question-form.service';

describe('QeQuestion Form Service', () => {
  let service: QeQuestionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QeQuestionFormService);
  });

  describe('Service methods', () => {
    describe('createQeQuestionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQeQuestionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            title: expect.any(Object),
            questionText: expect.any(Object),
            note: expect.any(Object),
            minReplyNumber: expect.any(Object),
            maxReplyNumber: expect.any(Object),
            randomRepliesOrder: expect.any(Object),
            valueOfAnswerSum: expect.any(Object),
            attachmentsRequired: expect.any(Object),
            image64: expect.any(Object),
            imageAlt: expect.any(Object),
            nodeToExpand: expect.any(Object),
            position: expect.any(Object),
            qeGroup: expect.any(Object),
          })
        );
      });

      it('passing IQeQuestion should create a new form with FormGroup', () => {
        const formGroup = service.createQeQuestionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nodeId: expect.any(Object),
            text: expect.any(Object),
            title: expect.any(Object),
            questionText: expect.any(Object),
            note: expect.any(Object),
            minReplyNumber: expect.any(Object),
            maxReplyNumber: expect.any(Object),
            randomRepliesOrder: expect.any(Object),
            valueOfAnswerSum: expect.any(Object),
            attachmentsRequired: expect.any(Object),
            image64: expect.any(Object),
            imageAlt: expect.any(Object),
            nodeToExpand: expect.any(Object),
            position: expect.any(Object),
            qeGroup: expect.any(Object),
          })
        );
      });
    });

    describe('getQeQuestion', () => {
      it('should return NewQeQuestion for default QeQuestion initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQeQuestionFormGroup(sampleWithNewData);

        const qeQuestion = service.getQeQuestion(formGroup) as any;

        expect(qeQuestion).toMatchObject(sampleWithNewData);
      });

      it('should return NewQeQuestion for empty QeQuestion initial value', () => {
        const formGroup = service.createQeQuestionFormGroup();

        const qeQuestion = service.getQeQuestion(formGroup) as any;

        expect(qeQuestion).toMatchObject({});
      });

      it('should return IQeQuestion', () => {
        const formGroup = service.createQeQuestionFormGroup(sampleWithRequiredData);

        const qeQuestion = service.getQeQuestion(formGroup) as any;

        expect(qeQuestion).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQeQuestion should not enable id FormControl', () => {
        const formGroup = service.createQeQuestionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQeQuestion should disable id FormControl', () => {
        const formGroup = service.createQeQuestionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
