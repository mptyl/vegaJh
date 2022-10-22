import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';

export interface IQeJumpExpression {
  id: number;
  nodeId?: string | null;
  text?: string | null;
  expression?: string | null;
  jumpTo?: string | null;
  position?: number | null;
  qeQuestion?: Pick<IQeQuestion, 'id'> | null;
}

export type NewQeJumpExpression = Omit<IQeJumpExpression, 'id'> & { id: null };
