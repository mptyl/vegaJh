import { IQeRadioGroup } from 'app/entities/qe-radio-group/qe-radio-group.model';

export interface IQeRadioBox {
  id: number;
  label?: string | null;
  boxvalue?: string | null;
  checked?: boolean | null;
  position?: number | null;
  qeRadioGroup?: Pick<IQeRadioGroup, 'id'> | null;
}

export type NewQeRadioBox = Omit<IQeRadioBox, 'id'> & { id: null };
