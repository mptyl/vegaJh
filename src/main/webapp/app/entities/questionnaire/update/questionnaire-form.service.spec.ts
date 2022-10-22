import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../questionnaire.test-samples';

import { QuestionnaireFormService } from './questionnaire-form.service';

describe('Questionnaire Form Service', () => {
  let service: QuestionnaireFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuestionnaireFormService);
  });

  describe('Service methods', () => {
    describe('createQuestionnaireFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createQuestionnaireFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            version: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            notes: expect.any(Object),
            image: expect.any(Object),
            imageAlt: expect.any(Object),
            instructions: expect.any(Object),
            compilationTime: expect.any(Object),
            forcedTerminationTime: expect.any(Object),
            usedSeconds: expect.any(Object),
            status: expect.any(Object),
            xml: expect.any(Object),
            json: expect.any(Object),
            saveText: expect.any(Object),
            searchText: expect.any(Object),
            subjectToEvaluation: expect.any(Object),
            questionnaireType: expect.any(Object),
            attachments: expect.any(Object),
            questionnaireGroup: expect.any(Object),
            questionnaireProfile: expect.any(Object),
          })
        );
      });

      it('passing IQuestionnaire should create a new form with FormGroup', () => {
        const formGroup = service.createQuestionnaireFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            version: expect.any(Object),
            title: expect.any(Object),
            subTitle: expect.any(Object),
            notes: expect.any(Object),
            image: expect.any(Object),
            imageAlt: expect.any(Object),
            instructions: expect.any(Object),
            compilationTime: expect.any(Object),
            forcedTerminationTime: expect.any(Object),
            usedSeconds: expect.any(Object),
            status: expect.any(Object),
            xml: expect.any(Object),
            json: expect.any(Object),
            saveText: expect.any(Object),
            searchText: expect.any(Object),
            subjectToEvaluation: expect.any(Object),
            questionnaireType: expect.any(Object),
            attachments: expect.any(Object),
            questionnaireGroup: expect.any(Object),
            questionnaireProfile: expect.any(Object),
          })
        );
      });
    });

    describe('getQuestionnaire', () => {
      it('should return NewQuestionnaire for default Questionnaire initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createQuestionnaireFormGroup(sampleWithNewData);

        const questionnaire = service.getQuestionnaire(formGroup) as any;

        expect(questionnaire).toMatchObject(sampleWithNewData);
      });

      it('should return NewQuestionnaire for empty Questionnaire initial value', () => {
        const formGroup = service.createQuestionnaireFormGroup();

        const questionnaire = service.getQuestionnaire(formGroup) as any;

        expect(questionnaire).toMatchObject({});
      });

      it('should return IQuestionnaire', () => {
        const formGroup = service.createQuestionnaireFormGroup(sampleWithRequiredData);

        const questionnaire = service.getQuestionnaire(formGroup) as any;

        expect(questionnaire).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IQuestionnaire should not enable id FormControl', () => {
        const formGroup = service.createQuestionnaireFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewQuestionnaire should disable id FormControl', () => {
        const formGroup = service.createQuestionnaireFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
