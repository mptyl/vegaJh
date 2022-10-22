import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { Orientation } from 'app/entities/enumerations/orientation.model';

export interface IQeCheckGroup {
  id: number;
  nodeId?: string | null;
  text?: string | null;
  radioboxGroupName?: string | null;
  orientation?: Orientation | null;
  positio?: number | null;
  qeQuestion?: Pick<IQeQuestion, 'id'> | null;
}

export type NewQeCheckGroup = Omit<IQeCheckGroup, 'id'> & { id: null };
