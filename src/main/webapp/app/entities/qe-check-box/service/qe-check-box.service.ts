import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeCheckBox, NewQeCheckBox } from '../qe-check-box.model';

export type PartialUpdateQeCheckBox = Partial<IQeCheckBox> & Pick<IQeCheckBox, 'id'>;

export type EntityResponseType = HttpResponse<IQeCheckBox>;
export type EntityArrayResponseType = HttpResponse<IQeCheckBox[]>;

@Injectable({ providedIn: 'root' })
export class QeCheckBoxService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-check-boxes');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeCheckBox: NewQeCheckBox): Observable<EntityResponseType> {
    return this.http.post<IQeCheckBox>(this.resourceUrl, qeCheckBox, { observe: 'response' });
  }

  update(qeCheckBox: IQeCheckBox): Observable<EntityResponseType> {
    return this.http.put<IQeCheckBox>(`${this.resourceUrl}/${this.getQeCheckBoxIdentifier(qeCheckBox)}`, qeCheckBox, {
      observe: 'response',
    });
  }

  partialUpdate(qeCheckBox: PartialUpdateQeCheckBox): Observable<EntityResponseType> {
    return this.http.patch<IQeCheckBox>(`${this.resourceUrl}/${this.getQeCheckBoxIdentifier(qeCheckBox)}`, qeCheckBox, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeCheckBox>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeCheckBox[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeCheckBoxIdentifier(qeCheckBox: Pick<IQeCheckBox, 'id'>): number {
    return qeCheckBox.id;
  }

  compareQeCheckBox(o1: Pick<IQeCheckBox, 'id'> | null, o2: Pick<IQeCheckBox, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeCheckBoxIdentifier(o1) === this.getQeCheckBoxIdentifier(o2) : o1 === o2;
  }

  addQeCheckBoxToCollectionIfMissing<Type extends Pick<IQeCheckBox, 'id'>>(
    qeCheckBoxCollection: Type[],
    ...qeCheckBoxesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeCheckBoxes: Type[] = qeCheckBoxesToCheck.filter(isPresent);
    if (qeCheckBoxes.length > 0) {
      const qeCheckBoxCollectionIdentifiers = qeCheckBoxCollection.map(qeCheckBoxItem => this.getQeCheckBoxIdentifier(qeCheckBoxItem)!);
      const qeCheckBoxesToAdd = qeCheckBoxes.filter(qeCheckBoxItem => {
        const qeCheckBoxIdentifier = this.getQeCheckBoxIdentifier(qeCheckBoxItem);
        if (qeCheckBoxCollectionIdentifiers.includes(qeCheckBoxIdentifier)) {
          return false;
        }
        qeCheckBoxCollectionIdentifiers.push(qeCheckBoxIdentifier);
        return true;
      });
      return [...qeCheckBoxesToAdd, ...qeCheckBoxCollection];
    }
    return qeCheckBoxCollection;
  }
}
