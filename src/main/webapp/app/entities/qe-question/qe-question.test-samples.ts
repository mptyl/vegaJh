import { IQeQuestion, NewQeQuestion } from './qe-question.model';

export const sampleWithRequiredData: IQeQuestion = {
  id: 66590,
};

export const sampleWithPartialData: IQeQuestion = {
  id: 14312,
  text: 'withdrawal',
  questionText: 'tangibile THX',
  maxReplyNumber: 30288,
  valueOfAnswerSum: 32534,
  attachmentsRequired: 87810,
  image64: 'stabile Cambridgeshire Hat',
  imageAlt: 'Installazione Philippine',
};

export const sampleWithFullData: IQeQuestion = {
  id: 96817,
  nodeId: 'accrescitive Garden',
  text: 'Via Towels',
  title: 'withdrawal Orchestrazione SSL',
  questionText: 'withdrawal',
  note: 'Producer Chief',
  minReplyNumber: 49992,
  maxReplyNumber: 12183,
  randomRepliesOrder: true,
  valueOfAnswerSum: 1209,
  attachmentsRequired: 87347,
  image64: 'Shoes bi-direzionale Plastic',
  imageAlt: 'e-business Avon forti',
  nodeToExpand: 89332,
  position: 46933,
};

export const sampleWithNewData: NewQeQuestion = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
