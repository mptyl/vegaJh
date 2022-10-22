import { IQeCheckGroup } from 'app/entities/qe-check-group/qe-check-group.model';

export interface IQeCheckBox {
  id: number;
  label?: string | null;
  boxvalue?: string | null;
  checked?: boolean | null;
  position?: number | null;
  qeCheckGroup?: Pick<IQeCheckGroup, 'id'> | null;
}

export type NewQeCheckBox = Omit<IQeCheckBox, 'id'> & { id: null };
