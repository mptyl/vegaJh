import { IQeGroup, NewQeGroup } from './qe-group.model';

export const sampleWithRequiredData: IQeGroup = {
  id: 96620,
};

export const sampleWithPartialData: IQeGroup = {
  id: 53707,
  nodeId: 'Rwanda',
  text: 'Rotonda',
  position: 50438,
};

export const sampleWithFullData: IQeGroup = {
  id: 82869,
  nodeId: 'partnerships optical Tokelau',
  text: 'Functionality JBOD metodologie',
  random: false,
  position: 56640,
};

export const sampleWithNewData: NewQeGroup = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
