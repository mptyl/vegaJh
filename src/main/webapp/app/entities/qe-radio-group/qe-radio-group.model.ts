import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { Orientation } from 'app/entities/enumerations/orientation.model';

export interface IQeRadioGroup {
  id: number;
  nodeId?: string | null;
  text?: string | null;
  radioboxGroupName?: string | null;
  orientation?: Orientation | null;
  position?: number | null;
  qeQuestion?: Pick<IQeQuestion, 'id'> | null;
}

export type NewQeRadioGroup = Omit<IQeRadioGroup, 'id'> & { id: null };
