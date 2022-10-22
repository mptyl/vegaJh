import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeQuestion, NewQeQuestion } from '../qe-question.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeQuestion for edit and NewQeQuestionFormGroupInput for create.
 */
type QeQuestionFormGroupInput = IQeQuestion | PartialWithRequiredKeyOf<NewQeQuestion>;

type QeQuestionFormDefaults = Pick<NewQeQuestion, 'id' | 'randomRepliesOrder'>;

type QeQuestionFormGroupContent = {
  id: FormControl<IQeQuestion['id'] | NewQeQuestion['id']>;
  nodeId: FormControl<IQeQuestion['nodeId']>;
  text: FormControl<IQeQuestion['text']>;
  title: FormControl<IQeQuestion['title']>;
  questionText: FormControl<IQeQuestion['questionText']>;
  note: FormControl<IQeQuestion['note']>;
  minReplyNumber: FormControl<IQeQuestion['minReplyNumber']>;
  maxReplyNumber: FormControl<IQeQuestion['maxReplyNumber']>;
  randomRepliesOrder: FormControl<IQeQuestion['randomRepliesOrder']>;
  valueOfAnswerSum: FormControl<IQeQuestion['valueOfAnswerSum']>;
  attachmentsRequired: FormControl<IQeQuestion['attachmentsRequired']>;
  image64: FormControl<IQeQuestion['image64']>;
  imageAlt: FormControl<IQeQuestion['imageAlt']>;
  nodeToExpand: FormControl<IQeQuestion['nodeToExpand']>;
  position: FormControl<IQeQuestion['position']>;
  qeGroup: FormControl<IQeQuestion['qeGroup']>;
};

export type QeQuestionFormGroup = FormGroup<QeQuestionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeQuestionFormService {
  createQeQuestionFormGroup(qeQuestion: QeQuestionFormGroupInput = { id: null }): QeQuestionFormGroup {
    const qeQuestionRawValue = {
      ...this.getFormDefaults(),
      ...qeQuestion,
    };
    return new FormGroup<QeQuestionFormGroupContent>({
      id: new FormControl(
        { value: qeQuestionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nodeId: new FormControl(qeQuestionRawValue.nodeId),
      text: new FormControl(qeQuestionRawValue.text),
      title: new FormControl(qeQuestionRawValue.title),
      questionText: new FormControl(qeQuestionRawValue.questionText),
      note: new FormControl(qeQuestionRawValue.note),
      minReplyNumber: new FormControl(qeQuestionRawValue.minReplyNumber),
      maxReplyNumber: new FormControl(qeQuestionRawValue.maxReplyNumber),
      randomRepliesOrder: new FormControl(qeQuestionRawValue.randomRepliesOrder),
      valueOfAnswerSum: new FormControl(qeQuestionRawValue.valueOfAnswerSum),
      attachmentsRequired: new FormControl(qeQuestionRawValue.attachmentsRequired),
      image64: new FormControl(qeQuestionRawValue.image64),
      imageAlt: new FormControl(qeQuestionRawValue.imageAlt),
      nodeToExpand: new FormControl(qeQuestionRawValue.nodeToExpand),
      position: new FormControl(qeQuestionRawValue.position),
      qeGroup: new FormControl(qeQuestionRawValue.qeGroup),
    });
  }

  getQeQuestion(form: QeQuestionFormGroup): IQeQuestion | NewQeQuestion {
    return form.getRawValue() as IQeQuestion | NewQeQuestion;
  }

  resetForm(form: QeQuestionFormGroup, qeQuestion: QeQuestionFormGroupInput): void {
    const qeQuestionRawValue = { ...this.getFormDefaults(), ...qeQuestion };
    form.reset(
      {
        ...qeQuestionRawValue,
        id: { value: qeQuestionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeQuestionFormDefaults {
    return {
      id: null,
      randomRepliesOrder: false,
    };
  }
}
