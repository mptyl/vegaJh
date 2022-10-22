import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeJumpExpression, NewQeJumpExpression } from '../qe-jump-expression.model';

export type PartialUpdateQeJumpExpression = Partial<IQeJumpExpression> & Pick<IQeJumpExpression, 'id'>;

export type EntityResponseType = HttpResponse<IQeJumpExpression>;
export type EntityArrayResponseType = HttpResponse<IQeJumpExpression[]>;

@Injectable({ providedIn: 'root' })
export class QeJumpExpressionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-jump-expressions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeJumpExpression: NewQeJumpExpression): Observable<EntityResponseType> {
    return this.http.post<IQeJumpExpression>(this.resourceUrl, qeJumpExpression, { observe: 'response' });
  }

  update(qeJumpExpression: IQeJumpExpression): Observable<EntityResponseType> {
    return this.http.put<IQeJumpExpression>(
      `${this.resourceUrl}/${this.getQeJumpExpressionIdentifier(qeJumpExpression)}`,
      qeJumpExpression,
      { observe: 'response' }
    );
  }

  partialUpdate(qeJumpExpression: PartialUpdateQeJumpExpression): Observable<EntityResponseType> {
    return this.http.patch<IQeJumpExpression>(
      `${this.resourceUrl}/${this.getQeJumpExpressionIdentifier(qeJumpExpression)}`,
      qeJumpExpression,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeJumpExpression>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeJumpExpression[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeJumpExpressionIdentifier(qeJumpExpression: Pick<IQeJumpExpression, 'id'>): number {
    return qeJumpExpression.id;
  }

  compareQeJumpExpression(o1: Pick<IQeJumpExpression, 'id'> | null, o2: Pick<IQeJumpExpression, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeJumpExpressionIdentifier(o1) === this.getQeJumpExpressionIdentifier(o2) : o1 === o2;
  }

  addQeJumpExpressionToCollectionIfMissing<Type extends Pick<IQeJumpExpression, 'id'>>(
    qeJumpExpressionCollection: Type[],
    ...qeJumpExpressionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeJumpExpressions: Type[] = qeJumpExpressionsToCheck.filter(isPresent);
    if (qeJumpExpressions.length > 0) {
      const qeJumpExpressionCollectionIdentifiers = qeJumpExpressionCollection.map(
        qeJumpExpressionItem => this.getQeJumpExpressionIdentifier(qeJumpExpressionItem)!
      );
      const qeJumpExpressionsToAdd = qeJumpExpressions.filter(qeJumpExpressionItem => {
        const qeJumpExpressionIdentifier = this.getQeJumpExpressionIdentifier(qeJumpExpressionItem);
        if (qeJumpExpressionCollectionIdentifiers.includes(qeJumpExpressionIdentifier)) {
          return false;
        }
        qeJumpExpressionCollectionIdentifiers.push(qeJumpExpressionIdentifier);
        return true;
      });
      return [...qeJumpExpressionsToAdd, ...qeJumpExpressionCollection];
    }
    return qeJumpExpressionCollection;
  }
}
