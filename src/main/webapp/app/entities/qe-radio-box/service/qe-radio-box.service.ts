import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeRadioBox, NewQeRadioBox } from '../qe-radio-box.model';

export type PartialUpdateQeRadioBox = Partial<IQeRadioBox> & Pick<IQeRadioBox, 'id'>;

export type EntityResponseType = HttpResponse<IQeRadioBox>;
export type EntityArrayResponseType = HttpResponse<IQeRadioBox[]>;

@Injectable({ providedIn: 'root' })
export class QeRadioBoxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-radio-boxes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeRadioBox: NewQeRadioBox): Observable<EntityResponseType> {
    return this.http.post<IQeRadioBox>(this.resourceUrl, qeRadioBox, { observe: 'response' });
  }

  update(qeRadioBox: IQeRadioBox): Observable<EntityResponseType> {
    return this.http.put<IQeRadioBox>(`${this.resourceUrl}/${this.getQeRadioBoxIdentifier(qeRadioBox)}`, qeRadioBox, {
      observe: 'response',
    });
  }

  partialUpdate(qeRadioBox: PartialUpdateQeRadioBox): Observable<EntityResponseType> {
    return this.http.patch<IQeRadioBox>(`${this.resourceUrl}/${this.getQeRadioBoxIdentifier(qeRadioBox)}`, qeRadioBox, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeRadioBox>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeRadioBox[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeRadioBoxIdentifier(qeRadioBox: Pick<IQeRadioBox, 'id'>): number {
    return qeRadioBox.id;
  }

  compareQeRadioBox(o1: Pick<IQeRadioBox, 'id'> | null, o2: Pick<IQeRadioBox, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeRadioBoxIdentifier(o1) === this.getQeRadioBoxIdentifier(o2) : o1 === o2;
  }

  addQeRadioBoxToCollectionIfMissing<Type extends Pick<IQeRadioBox, 'id'>>(
    qeRadioBoxCollection: Type[],
    ...qeRadioBoxesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeRadioBoxes: Type[] = qeRadioBoxesToCheck.filter(isPresent);
    if (qeRadioBoxes.length > 0) {
      const qeRadioBoxCollectionIdentifiers = qeRadioBoxCollection.map(qeRadioBoxItem => this.getQeRadioBoxIdentifier(qeRadioBoxItem)!);
      const qeRadioBoxesToAdd = qeRadioBoxes.filter(qeRadioBoxItem => {
        const qeRadioBoxIdentifier = this.getQeRadioBoxIdentifier(qeRadioBoxItem);
        if (qeRadioBoxCollectionIdentifiers.includes(qeRadioBoxIdentifier)) {
          return false;
        }
        qeRadioBoxCollectionIdentifiers.push(qeRadioBoxIdentifier);
        return true;
      });
      return [...qeRadioBoxesToAdd, ...qeRadioBoxCollection];
    }
    return qeRadioBoxCollection;
  }
}
