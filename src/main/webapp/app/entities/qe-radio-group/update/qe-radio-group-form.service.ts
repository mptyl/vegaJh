import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeRadioGroup, NewQeRadioGroup } from '../qe-radio-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeRadioGroup for edit and NewQeRadioGroupFormGroupInput for create.
 */
type QeRadioGroupFormGroupInput = IQeRadioGroup | PartialWithRequiredKeyOf<NewQeRadioGroup>;

type QeRadioGroupFormDefaults = Pick<NewQeRadioGroup, 'id'>;

type QeRadioGroupFormGroupContent = {
  id: FormControl<IQeRadioGroup['id'] | NewQeRadioGroup['id']>;
  nodeId: FormControl<IQeRadioGroup['nodeId']>;
  text: FormControl<IQeRadioGroup['text']>;
  radioboxGroupName: FormControl<IQeRadioGroup['radioboxGroupName']>;
  orientation: FormControl<IQeRadioGroup['orientation']>;
  position: FormControl<IQeRadioGroup['position']>;
  qeQuestion: FormControl<IQeRadioGroup['qeQuestion']>;
};

export type QeRadioGroupFormGroup = FormGroup<QeRadioGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeRadioGroupFormService {
  createQeRadioGroupFormGroup(qeRadioGroup: QeRadioGroupFormGroupInput = { id: null }): QeRadioGroupFormGroup {
    const qeRadioGroupRawValue = {
      ...this.getFormDefaults(),
      ...qeRadioGroup,
    };
    return new FormGroup<QeRadioGroupFormGroupContent>({
      id: new FormControl(
        { value: qeRadioGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nodeId: new FormControl(qeRadioGroupRawValue.nodeId),
      text: new FormControl(qeRadioGroupRawValue.text),
      radioboxGroupName: new FormControl(qeRadioGroupRawValue.radioboxGroupName),
      orientation: new FormControl(qeRadioGroupRawValue.orientation),
      position: new FormControl(qeRadioGroupRawValue.position),
      qeQuestion: new FormControl(qeRadioGroupRawValue.qeQuestion),
    });
  }

  getQeRadioGroup(form: QeRadioGroupFormGroup): IQeRadioGroup | NewQeRadioGroup {
    return form.getRawValue() as IQeRadioGroup | NewQeRadioGroup;
  }

  resetForm(form: QeRadioGroupFormGroup, qeRadioGroup: QeRadioGroupFormGroupInput): void {
    const qeRadioGroupRawValue = { ...this.getFormDefaults(), ...qeRadioGroup };
    form.reset(
      {
        ...qeRadioGroupRawValue,
        id: { value: qeRadioGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeRadioGroupFormDefaults {
    return {
      id: null,
    };
  }
}
