import { IQeRadioBox, NewQeRadioBox } from './qe-radio-box.model';

export const sampleWithRequiredData: IQeRadioBox = {
  id: 31249,
};

export const sampleWithPartialData: IQeRadioBox = {
  id: 43976,
  checked: false,
  position: 38067,
};

export const sampleWithFullData: IQeRadioBox = {
  id: 12854,
  label: 'progressiva',
  boxvalue: 'Gold',
  checked: false,
  position: 24388,
};

export const sampleWithNewData: NewQeRadioBox = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
