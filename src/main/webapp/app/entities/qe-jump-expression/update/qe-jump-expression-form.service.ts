import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IQeJumpExpression, NewQeJumpExpression } from '../qe-jump-expression.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IQeJumpExpression for edit and NewQeJumpExpressionFormGroupInput for create.
 */
type QeJumpExpressionFormGroupInput = IQeJumpExpression | PartialWithRequiredKeyOf<NewQeJumpExpression>;

type QeJumpExpressionFormDefaults = Pick<NewQeJumpExpression, 'id'>;

type QeJumpExpressionFormGroupContent = {
  id: FormControl<IQeJumpExpression['id'] | NewQeJumpExpression['id']>;
  nodeId: FormControl<IQeJumpExpression['nodeId']>;
  text: FormControl<IQeJumpExpression['text']>;
  expression: FormControl<IQeJumpExpression['expression']>;
  jumpTo: FormControl<IQeJumpExpression['jumpTo']>;
  position: FormControl<IQeJumpExpression['position']>;
  qeQuestion: FormControl<IQeJumpExpression['qeQuestion']>;
};

export type QeJumpExpressionFormGroup = FormGroup<QeJumpExpressionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class QeJumpExpressionFormService {
  createQeJumpExpressionFormGroup(qeJumpExpression: QeJumpExpressionFormGroupInput = { id: null }): QeJumpExpressionFormGroup {
    const qeJumpExpressionRawValue = {
      ...this.getFormDefaults(),
      ...qeJumpExpression,
    };
    return new FormGroup<QeJumpExpressionFormGroupContent>({
      id: new FormControl(
        { value: qeJumpExpressionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nodeId: new FormControl(qeJumpExpressionRawValue.nodeId),
      text: new FormControl(qeJumpExpressionRawValue.text),
      expression: new FormControl(qeJumpExpressionRawValue.expression),
      jumpTo: new FormControl(qeJumpExpressionRawValue.jumpTo),
      position: new FormControl(qeJumpExpressionRawValue.position),
      qeQuestion: new FormControl(qeJumpExpressionRawValue.qeQuestion),
    });
  }

  getQeJumpExpression(form: QeJumpExpressionFormGroup): IQeJumpExpression | NewQeJumpExpression {
    return form.getRawValue() as IQeJumpExpression | NewQeJumpExpression;
  }

  resetForm(form: QeJumpExpressionFormGroup, qeJumpExpression: QeJumpExpressionFormGroupInput): void {
    const qeJumpExpressionRawValue = { ...this.getFormDefaults(), ...qeJumpExpression };
    form.reset(
      {
        ...qeJumpExpressionRawValue,
        id: { value: qeJumpExpressionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): QeJumpExpressionFormDefaults {
    return {
      id: null,
    };
  }
}
