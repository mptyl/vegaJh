import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeGroup, NewQeGroup } from '../qe-group.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeGroup for edit and NewQeGroupFormGroupInput for create.
 */
type QeGroupFormGroupInput = IQeGroup | PartialWithRequiredKeyOf<NewQeGroup>;

type QeGroupFormDefaults = Pick<NewQeGroup, 'id' | 'random'>;

type QeGroupFormGroupContent = {
  id: FormControl<IQeGroup['id'] | NewQeGroup['id']>;
  nodeId: FormControl<IQeGroup['nodeId']>;
  text: FormControl<IQeGroup['text']>;
  random: FormControl<IQeGroup['random']>;
  position: FormControl<IQeGroup['position']>;
  questionnaire: FormControl<IQeGroup['questionnaire']>;
};

export type QeGroupFormGroup = FormGroup<QeGroupFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeGroupFormService {
  createQeGroupFormGroup(qeGroup: QeGroupFormGroupInput = { id: null }): QeGroupFormGroup {
    const qeGroupRawValue = {
      ...this.getFormDefaults(),
      ...qeGroup,
    };
    return new FormGroup<QeGroupFormGroupContent>({
      id: new FormControl(
        { value: qeGroupRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nodeId: new FormControl(qeGroupRawValue.nodeId),
      text: new FormControl(qeGroupRawValue.text),
      random: new FormControl(qeGroupRawValue.random),
      position: new FormControl(qeGroupRawValue.position),
      questionnaire: new FormControl(qeGroupRawValue.questionnaire),
    });
  }

  getQeGroup(form: QeGroupFormGroup): IQeGroup | NewQeGroup {
    return form.getRawValue() as IQeGroup | NewQeGroup;
  }

  resetForm(form: QeGroupFormGroup, qeGroup: QeGroupFormGroupInput): void {
    const qeGroupRawValue = { ...this.getFormDefaults(), ...qeGroup };
    form.reset(
      {
        ...qeGroupRawValue,
        id: { value: qeGroupRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeGroupFormDefaults {
    return {
      id: null,
      random: false,
    };
  }
}
