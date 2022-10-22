import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeCheckGroup, NewQeCheckGroup } from '../qe-check-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeCheckGroup for edit and NewQeCheckGroupFormGroupInput for create.
 */
type QeCheckGroupFormGroupInput = IQeCheckGroup | PartialWithRequiredKeyOf<NewQeCheckGroup>;

type QeCheckGroupFormDefaults = Pick<NewQeCheckGroup, 'id'>;

type QeCheckGroupFormGroupContent = {
  id: FormControl<IQeCheckGroup['id'] | NewQeCheckGroup['id']>;
  nodeId: FormControl<IQeCheckGroup['nodeId']>;
  text: FormControl<IQeCheckGroup['text']>;
  radioboxGroupName: FormControl<IQeCheckGroup['radioboxGroupName']>;
  orientation: FormControl<IQeCheckGroup['orientation']>;
  positio: FormControl<IQeCheckGroup['positio']>;
  qeQuestion: FormControl<IQeCheckGroup['qeQuestion']>;
};

export type QeCheckGroupFormGroup = FormGroup<QeCheckGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeCheckGroupFormService {
  createQeCheckGroupFormGroup(qeCheckGroup: QeCheckGroupFormGroupInput = { id: null }): QeCheckGroupFormGroup {
    const qeCheckGroupRawValue = {
      ...this.getFormDefaults(),
      ...qeCheckGroup,
    };
    return new FormGroup<QeCheckGroupFormGroupContent>({
      id: new FormControl(
        { value: qeCheckGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nodeId: new FormControl(qeCheckGroupRawValue.nodeId),
      text: new FormControl(qeCheckGroupRawValue.text),
      radioboxGroupName: new FormControl(qeCheckGroupRawValue.radioboxGroupName),
      orientation: new FormControl(qeCheckGroupRawValue.orientation),
      positio: new FormControl(qeCheckGroupRawValue.positio),
      qeQuestion: new FormControl(qeCheckGroupRawValue.qeQuestion),
    });
  }

  getQeCheckGroup(form: QeCheckGroupFormGroup): IQeCheckGroup | NewQeCheckGroup {
    return form.getRawValue() as IQeCheckGroup | NewQeCheckGroup;
  }

  resetForm(form: QeCheckGroupFormGroup, qeCheckGroup: QeCheckGroupFormGroupInput): void {
    const qeCheckGroupRawValue = { ...this.getFormDefaults(), ...qeCheckGroup };
    form.reset(
      {
        ...qeCheckGroupRawValue,
        id: { value: qeCheckGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeCheckGroupFormDefaults {
    return {
      id: null,
    };
  }
}
