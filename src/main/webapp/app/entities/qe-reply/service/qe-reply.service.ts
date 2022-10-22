import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeReply, NewQeReply } from '../qe-reply.model';

export type PartialUpdateQeReply = Partial<IQeReply> & Pick<IQeReply, 'id'>;

type RestOf<T extends IQeReply | NewQeReply> = Omit<T, 'dateMinValue' | 'dateMaxValue'> & {
  dateMinValue?: string | null;
  dateMaxValue?: string | null;
};

export type RestQeReply = RestOf<IQeReply>;

export type NewRestQeReply = RestOf<NewQeReply>;

export type PartialUpdateRestQeReply = RestOf<PartialUpdateQeReply>;

export type EntityResponseType = HttpResponse<IQeReply>;
export type EntityArrayResponseType = HttpResponse<IQeReply[]>;

@Injectable({ providedIn: 'root' })
export class QeReplyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-replies');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeReply: NewQeReply): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(qeReply);
    return this.http
      .post<RestQeReply>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(qeReply: IQeReply): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(qeReply);
    return this.http
      .put<RestQeReply>(`${this.resourceUrl}/${this.getQeReplyIdentifier(qeReply)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(qeReply: PartialUpdateQeReply): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(qeReply);
    return this.http
      .patch<RestQeReply>(`${this.resourceUrl}/${this.getQeReplyIdentifier(qeReply)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestQeReply>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestQeReply[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeReplyIdentifier(qeReply: Pick<IQeReply, 'id'>): number {
    return qeReply.id;
  }

  compareQeReply(o1: Pick<IQeReply, 'id'> | null, o2: Pick<IQeReply, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeReplyIdentifier(o1) === this.getQeReplyIdentifier(o2) : o1 === o2;
  }

  addQeReplyToCollectionIfMissing<Type extends Pick<IQeReply, 'id'>>(
    qeReplyCollection: Type[],
    ...qeRepliesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeReplies: Type[] = qeRepliesToCheck.filter(isPresent);
    if (qeReplies.length > 0) {
      const qeReplyCollectionIdentifiers = qeReplyCollection.map(qeReplyItem => this.getQeReplyIdentifier(qeReplyItem)!);
      const qeRepliesToAdd = qeReplies.filter(qeReplyItem => {
        const qeReplyIdentifier = this.getQeReplyIdentifier(qeReplyItem);
        if (qeReplyCollectionIdentifiers.includes(qeReplyIdentifier)) {
          return false;
        }
        qeReplyCollectionIdentifiers.push(qeReplyIdentifier);
        return true;
      });
      return [...qeRepliesToAdd, ...qeReplyCollection];
    }
    return qeReplyCollection;
  }

  protected convertDateFromClient<T extends IQeReply | NewQeReply | PartialUpdateQeReply>(qeReply: T): RestOf<T> {
    return {
      ...qeReply,
      dateMinValue: qeReply.dateMinValue?.format(DATE_FORMAT) ?? null,
      dateMaxValue: qeReply.dateMaxValue?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restQeReply: RestQeReply): IQeReply {
    return {
      ...restQeReply,
      dateMinValue: restQeReply.dateMinValue ? dayjs(restQeReply.dateMinValue) : undefined,
      dateMaxValue: restQeReply.dateMaxValue ? dayjs(restQeReply.dateMaxValue) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestQeReply>): HttpResponse<IQeReply> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestQeReply[]>): HttpResponse<IQeReply[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
