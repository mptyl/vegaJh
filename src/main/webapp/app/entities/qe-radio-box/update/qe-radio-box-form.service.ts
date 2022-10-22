import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeRadioBox, NewQeRadioBox } from '../qe-radio-box.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeRadioBox for edit and NewQeRadioBoxFormGroupInput for create.
 */
type QeRadioBoxFormGroupInput = IQeRadioBox | PartialWithRequiredKeyOf<NewQeRadioBox>;

type QeRadioBoxFormDefaults = Pick<NewQeRadioBox, 'id' | 'checked'>;

type QeRadioBoxFormGroupContent = {
  id: FormControl<IQeRadioBox['id'] | NewQeRadioBox['id']>;
  label: FormControl<IQeRadioBox['label']>;
  boxvalue: FormControl<IQeRadioBox['boxvalue']>;
  checked: FormControl<IQeRadioBox['checked']>;
  position: FormControl<IQeRadioBox['position']>;
  qeRadioGroup: FormControl<IQeRadioBox['qeRadioGroup']>;
};

export type QeRadioBoxFormGroup = FormGroup<QeRadioBoxFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeRadioBoxFormService {
  createQeRadioBoxFormGroup(qeRadioBox: QeRadioBoxFormGroupInput = { id: null }): QeRadioBoxFormGroup {
    const qeRadioBoxRawValue = {
      ...this.getFormDefaults(),
      ...qeRadioBox,
    };
    return new FormGroup<QeRadioBoxFormGroupContent>({
      id: new FormControl(
        { value: qeRadioBoxRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      label: new FormControl(qeRadioBoxRawValue.label),
      boxvalue: new FormControl(qeRadioBoxRawValue.boxvalue),
      checked: new FormControl(qeRadioBoxRawValue.checked),
      position: new FormControl(qeRadioBoxRawValue.position),
      qeRadioGroup: new FormControl(qeRadioBoxRawValue.qeRadioGroup),
    });
  }

  getQeRadioBox(form: QeRadioBoxFormGroup): IQeRadioBox | NewQeRadioBox {
    return form.getRawValue() as IQeRadioBox | NewQeRadioBox;
  }

  resetForm(form: QeRadioBoxFormGroup, qeRadioBox: QeRadioBoxFormGroupInput): void {
    const qeRadioBoxRawValue = { ...this.getFormDefaults(), ...qeRadioBox };
    form.reset(
      {
        ...qeRadioBoxRawValue,
        id: { value: qeRadioBoxRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeRadioBoxFormDefaults {
    return {
      id: null,
      checked: false,
    };
  }
}
