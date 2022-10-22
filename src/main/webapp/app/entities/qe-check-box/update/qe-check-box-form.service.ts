import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeCheckBox, NewQeCheckBox } from '../qe-check-box.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeCheckBox for edit and NewQeCheckBoxFormGroupInput for create.
 */
type QeCheckBoxFormGroupInput = IQeCheckBox | PartialWithRequiredKeyOf<NewQeCheckBox>;

type QeCheckBoxFormDefaults = Pick<NewQeCheckBox, 'id' | 'checked'>;

type QeCheckBoxFormGroupContent = {
  id: FormControl<IQeCheckBox['id'] | NewQeCheckBox['id']>;
  label: FormControl<IQeCheckBox['label']>;
  boxvalue: FormControl<IQeCheckBox['boxvalue']>;
  checked: FormControl<IQeCheckBox['checked']>;
  position: FormControl<IQeCheckBox['position']>;
  qeCheckGroup: FormControl<IQeCheckBox['qeCheckGroup']>;
};

export type QeCheckBoxFormGroup = FormGroup<QeCheckBoxFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeCheckBoxFormService {
  createQeCheckBoxFormGroup(qeCheckBox: QeCheckBoxFormGroupInput = { id: null }): QeCheckBoxFormGroup {
    const qeCheckBoxRawValue = {
      ...this.getFormDefaults(),
      ...qeCheckBox,
    };
    return new FormGroup<QeCheckBoxFormGroupContent>({
      id: new FormControl(
        { value: qeCheckBoxRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      label: new FormControl(qeCheckBoxRawValue.label),
      boxvalue: new FormControl(qeCheckBoxRawValue.boxvalue),
      checked: new FormControl(qeCheckBoxRawValue.checked),
      position: new FormControl(qeCheckBoxRawValue.position),
      qeCheckGroup: new FormControl(qeCheckBoxRawValue.qeCheckGroup),
    });
  }

  getQeCheckBox(form: QeCheckBoxFormGroup): IQeCheckBox | NewQeCheckBox {
    return form.getRawValue() as IQeCheckBox | NewQeCheckBox;
  }

  resetForm(form: QeCheckBoxFormGroup, qeCheckBox: QeCheckBoxFormGroupInput): void {
    const qeCheckBoxRawValue = { ...this.getFormDefaults(), ...qeCheckBox };
    form.reset(
      {
        ...qeCheckBoxRawValue,
        id: { value: qeCheckBoxRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeCheckBoxFormDefaults {
    return {
      id: null,
      checked: false,
    };
  }
}
