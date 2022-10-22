import { IQeJumpExpression, NewQeJumpExpression } from './qe-jump-expression.model';

export const sampleWithRequiredData: IQeJumpExpression = {
  id: 71290,
};

export const sampleWithPartialData: IQeJumpExpression = {
  id: 2504,
  jumpTo: 'solid Via Zimbabwe',
};

export const sampleWithFullData: IQeJumpExpression = {
  id: 25932,
  nodeId: 'Central Division Taiwan',
  text: 'Loan hack accrescitive',
  expression: 'Paradigm',
  jumpTo: 'bluetooth',
  position: 94636,
};

export const sampleWithNewData: NewQeJumpExpression = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
