import dayjs from 'dayjs/esm';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { ReplyType } from 'app/entities/enumerations/reply-type.model';

export interface IQeReply {
  id: number;
  nodeId?: string | null;
  text?: string | null;
  title?: string | null;
  label?: string | null;
  replyType?: ReplyType | null;
  dateMinValue?: dayjs.Dayjs | null;
  dateMaxValue?: dayjs.Dayjs | null;
  integerMinValue?: number | null;
  integerMaxValue?: number | null;
  doubleMinValue?: number | null;
  doubleMaxValue?: number | null;
  rangeMinValue?: number | null;
  rangeMaxValue?: number | null;
  selectList?: string | null;
  step?: number | null;
  replyPattern?: string | null;
  multiple?: boolean | null;
  placeHolder?: string | null;
  replyRequired?: boolean | null;
  booleanValue?: boolean | null;
  withComment?: boolean | null;
  position?: number | null;
  qeQuestion?: Pick<IQeQuestion, 'id'> | null;
}

export type NewQeReply = Omit<IQeReply, 'id'> & { id: null };
