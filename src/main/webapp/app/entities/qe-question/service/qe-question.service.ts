import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeQuestion, NewQeQuestion } from '../qe-question.model';

export type PartialUpdateQeQuestion = Partial<IQeQuestion> & Pick<IQeQuestion, 'id'>;

export type EntityResponseType = HttpResponse<IQeQuestion>;
export type EntityArrayResponseType = HttpResponse<IQeQuestion[]>;

@Injectable({ providedIn: 'root' })
export class QeQuestionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-questions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeQuestion: NewQeQuestion): Observable<EntityResponseType> {
    return this.http.post<IQeQuestion>(this.resourceUrl, qeQuestion, { observe: 'response' });
  }

  update(qeQuestion: IQeQuestion): Observable<EntityResponseType> {
    return this.http.put<IQeQuestion>(`${this.resourceUrl}/${this.getQeQuestionIdentifier(qeQuestion)}`, qeQuestion, {
      observe: 'response',
    });
  }

  partialUpdate(qeQuestion: PartialUpdateQeQuestion): Observable<EntityResponseType> {
    return this.http.patch<IQeQuestion>(`${this.resourceUrl}/${this.getQeQuestionIdentifier(qeQuestion)}`, qeQuestion, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeQuestionIdentifier(qeQuestion: Pick<IQeQuestion, 'id'>): number {
    return qeQuestion.id;
  }

  compareQeQuestion(o1: Pick<IQeQuestion, 'id'> | null, o2: Pick<IQeQuestion, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeQuestionIdentifier(o1) === this.getQeQuestionIdentifier(o2) : o1 === o2;
  }

  addQeQuestionToCollectionIfMissing<Type extends Pick<IQeQuestion, 'id'>>(
    qeQuestionCollection: Type[],
    ...qeQuestionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeQuestions: Type[] = qeQuestionsToCheck.filter(isPresent);
    if (qeQuestions.length > 0) {
      const qeQuestionCollectionIdentifiers = qeQuestionCollection.map(qeQuestionItem => this.getQeQuestionIdentifier(qeQuestionItem)!);
      const qeQuestionsToAdd = qeQuestions.filter(qeQuestionItem => {
        const qeQuestionIdentifier = this.getQeQuestionIdentifier(qeQuestionItem);
        if (qeQuestionCollectionIdentifiers.includes(qeQuestionIdentifier)) {
          return false;
        }
        qeQuestionCollectionIdentifiers.push(qeQuestionIdentifier);
        return true;
      });
      return [...qeQuestionsToAdd, ...qeQuestionCollection];
    }
    return qeQuestionCollection;
  }
}
