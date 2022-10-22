import { IQuestionnaire } from 'app/entities/questionnaire/questionnaire.model';

export interface IQeGroup {
  id: number;
  nodeId?: string | null;
  text?: string | null;
  random?: boolean | null;
  position?: number | null;
  questionnaire?: Pick<IQuestionnaire, 'id'> | null;
}

export type NewQeGroup = Omit<IQeGroup, 'id'> & { id: null };
