import { IQeGroup } from 'app/entities/qe-group/qe-group.model';

export interface IQeQuestion {
  id: number;
  nodeId?: string | null;
  text?: string | null;
  title?: string | null;
  questionText?: string | null;
  note?: string | null;
  minReplyNumber?: number | null;
  maxReplyNumber?: number | null;
  randomRepliesOrder?: boolean | null;
  valueOfAnswerSum?: number | null;
  attachmentsRequired?: number | null;
  image64?: string | null;
  imageAlt?: string | null;
  nodeToExpand?: number | null;
  position?: number | null;
  qeGroup?: Pick<IQeGroup, 'id'> | null;
}

export type NewQeQuestion = Omit<IQeQuestion, 'id'> & { id: null };
