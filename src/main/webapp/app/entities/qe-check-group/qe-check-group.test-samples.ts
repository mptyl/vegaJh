import { Orientation } from 'app/entities/enumerations/orientation.model';

import { IQeCheckGroup, NewQeCheckGroup } from './qe-check-group.model';

export const sampleWithRequiredData: IQeCheckGroup = {
  id: 41825,
};

export const sampleWithPartialData: IQeCheckGroup = {
  id: 1982,
  text: 'Corporate COM',
  radioboxGroupName: 'Perugia PNG',
  orientation: Orientation['ORIZZONTALE'],
  positio: 64276,
};

export const sampleWithFullData: IQeCheckGroup = {
  id: 29934,
  nodeId: 'Incredible',
  text: 'Pordenone SMS',
  radioboxGroupName: 'Via',
  orientation: Orientation['ORIZZONTALE'],
  positio: 49329,
};

export const sampleWithNewData: NewQeCheckGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
