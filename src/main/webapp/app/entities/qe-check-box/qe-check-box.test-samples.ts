import { IQeCheckBox, NewQeCheckBox } from './qe-check-box.model';

export const sampleWithRequiredData: IQeCheckBox = {
  id: 9556,
};

export const sampleWithPartialData: IQeCheckBox = {
  id: 22473,
  label: 'Steel',
  boxvalue: 'Small Soft Tools',
};

export const sampleWithFullData: IQeCheckBox = {
  id: 26105,
  label: 'panel hacking hacking',
  boxvalue: 'Bike redundant',
  checked: true,
  position: 7680,
};

export const sampleWithNewData: NewQeCheckBox = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
