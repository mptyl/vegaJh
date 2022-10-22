import dayjs from 'dayjs/esm';

import { ReplyType } from 'app/entities/enumerations/reply-type.model';

import { IQeReply, NewQeReply } from './qe-reply.model';

export const sampleWithRequiredData: IQeReply = {
  id: 52088,
};

export const sampleWithPartialData: IQeReply = {
  id: 33844,
  nodeId: 'Vanuatu USB Tasty',
  text: 'modelli',
  replyType: ReplyType['PHONE'],
  dateMinValue: dayjs('2022-10-18'),
  dateMaxValue: dayjs('2022-10-18'),
  integerMinValue: 13537,
  integerMaxValue: 76132,
  doubleMaxValue: 50971,
  rangeMaxValue: 12967,
  step: 26856,
  replyPattern: 'AGP Aosta Account',
  multiple: false,
  booleanValue: false,
  withComment: false,
  position: 37229,
};

export const sampleWithFullData: IQeReply = {
  id: 84843,
  nodeId: 'cross-platform',
  text: 'Account',
  title: 'Contrada',
  label: 'Cordoba Cambridgeshire',
  replyType: ReplyType['PHONE'],
  dateMinValue: dayjs('2022-10-18'),
  dateMaxValue: dayjs('2022-10-18'),
  integerMinValue: 41250,
  integerMaxValue: 46963,
  doubleMinValue: 63411,
  doubleMaxValue: 22710,
  rangeMinValue: 61968,
  rangeMaxValue: 44449,
  selectList: 'back online',
  step: 22522,
  replyPattern: 'violet',
  multiple: true,
  placeHolder: 'decentralizzata framework',
  replyRequired: false,
  booleanValue: true,
  withComment: false,
  position: 51277,
};

export const sampleWithNewData: NewQeReply = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
