import { Orientation } from 'app/entities/enumerations/orientation.model';

import { IQeRadioGroup, NewQeRadioGroup } from './qe-radio-group.model';

export const sampleWithRequiredData: IQeRadioGroup = {
  id: 34434,
};

export const sampleWithPartialData: IQeRadioGroup = {
  id: 12795,
  text: 'Car circuit Buckinghamshire',
  position: 67741,
};

export const sampleWithFullData: IQeRadioGroup = {
  id: 57205,
  nodeId: 'Strategist',
  text: 'clienti innovativa',
  radioboxGroupName: 'estesa background Incrocio',
  orientation: Orientation['VERTICALE'],
  position: 12912,
};

export const sampleWithNewData: NewQeRadioGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
