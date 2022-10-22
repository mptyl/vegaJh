import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeReply, NewQeReply } from '../qe-reply.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeReply for edit and NewQeReplyFormGroupInput for create.
 */
type QeReplyFormGroupInput = IQeReply | PartialWithRequiredKeyOf<NewQeReply>;

type QeReplyFormDefaults = Pick<NewQeReply, 'id' | 'multiple' | 'replyRequired' | 'booleanValue' | 'withComment'>;

type QeReplyFormGroupContent = {
  id: FormControl<IQeReply['id'] | NewQeReply['id']>;
  nodeId: FormControl<IQeReply['nodeId']>;
  text: FormControl<IQeReply['text']>;
  title: FormControl<IQeReply['title']>;
  label: FormControl<IQeReply['label']>;
  replyType: FormControl<IQeReply['replyType']>;
  dateMinValue: FormControl<IQeReply['dateMinValue']>;
  dateMaxValue: FormControl<IQeReply['dateMaxValue']>;
  integerMinValue: FormControl<IQeReply['integerMinValue']>;
  integerMaxValue: FormControl<IQeReply['integerMaxValue']>;
  doubleMinValue: FormControl<IQeReply['doubleMinValue']>;
  doubleMaxValue: FormControl<IQeReply['doubleMaxValue']>;
  rangeMinValue: FormControl<IQeReply['rangeMinValue']>;
  rangeMaxValue: FormControl<IQeReply['rangeMaxValue']>;
  selectList: FormControl<IQeReply['selectList']>;
  step: FormControl<IQeReply['step']>;
  replyPattern: FormControl<IQeReply['replyPattern']>;
  multiple: FormControl<IQeReply['multiple']>;
  placeHolder: FormControl<IQeReply['placeHolder']>;
  replyRequired: FormControl<IQeReply['replyRequired']>;
  booleanValue: FormControl<IQeReply['booleanValue']>;
  withComment: FormControl<IQeReply['withComment']>;
  position: FormControl<IQeReply['position']>;
  qeQuestion: FormControl<IQeReply['qeQuestion']>;
};

export type QeReplyFormGroup = FormGroup<QeReplyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeReplyFormService {
  createQeReplyFormGroup(qeReply: QeReplyFormGroupInput = { id: null }): QeReplyFormGroup {
    const qeReplyRawValue = {
      ...this.getFormDefaults(),
      ...qeReply,
    };
    return new FormGroup<QeReplyFormGroupContent>({
      id: new FormControl(
        { value: qeReplyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nodeId: new FormControl(qeReplyRawValue.nodeId),
      text: new FormControl(qeReplyRawValue.text),
      title: new FormControl(qeReplyRawValue.title),
      label: new FormControl(qeReplyRawValue.label),
      replyType: new FormControl(qeReplyRawValue.replyType),
      dateMinValue: new FormControl(qeReplyRawValue.dateMinValue),
      dateMaxValue: new FormControl(qeReplyRawValue.dateMaxValue),
      integerMinValue: new FormControl(qeReplyRawValue.integerMinValue),
      integerMaxValue: new FormControl(qeReplyRawValue.integerMaxValue),
      doubleMinValue: new FormControl(qeReplyRawValue.doubleMinValue),
      doubleMaxValue: new FormControl(qeReplyRawValue.doubleMaxValue),
      rangeMinValue: new FormControl(qeReplyRawValue.rangeMinValue),
      rangeMaxValue: new FormControl(qeReplyRawValue.rangeMaxValue),
      selectList: new FormControl(qeReplyRawValue.selectList),
      step: new FormControl(qeReplyRawValue.step),
      replyPattern: new FormControl(qeReplyRawValue.replyPattern),
      multiple: new FormControl(qeReplyRawValue.multiple),
      placeHolder: new FormControl(qeReplyRawValue.placeHolder),
      replyRequired: new FormControl(qeReplyRawValue.replyRequired),
      booleanValue: new FormControl(qeReplyRawValue.booleanValue),
      withComment: new FormControl(qeReplyRawValue.withComment),
      position: new FormControl(qeReplyRawValue.position),
      qeQuestion: new FormControl(qeReplyRawValue.qeQuestion),
    });
  }

  getQeReply(form: QeReplyFormGroup): IQeReply | NewQeReply {
    return form.getRawValue() as IQeReply | NewQeReply;
  }

  resetForm(form: QeReplyFormGroup, qeReply: QeReplyFormGroupInput): void {
    const qeReplyRawValue = { ...this.getFormDefaults(), ...qeReply };
    form.reset(
      {
        ...qeReplyRawValue,
        id: { value: qeReplyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeReplyFormDefaults {
    return {
      id: null,
      multiple: false,
      replyRequired: false,
      booleanValue: false,
      withComment: false,
    };
  }
}
