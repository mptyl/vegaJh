import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeRadioGroup, NewQeRadioGroup } from '../qe-radio-group.model';

export type PartialUpdateQeRadioGroup = Partial<IQeRadioGroup> & Pick<IQeRadioGroup, 'id'>;

export type EntityResponseType = HttpResponse<IQeRadioGroup>;
export type EntityArrayResponseType = HttpResponse<IQeRadioGroup[]>;

@Injectable({ providedIn: 'root' })
export class QeRadioGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-radio-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeRadioGroup: NewQeRadioGroup): Observable<EntityResponseType> {
    return this.http.post<IQeRadioGroup>(this.resourceUrl, qeRadioGroup, { observe: 'response' });
  }

  update(qeRadioGroup: IQeRadioGroup): Observable<EntityResponseType> {
    return this.http.put<IQeRadioGroup>(`${this.resourceUrl}/${this.getQeRadioGroupIdentifier(qeRadioGroup)}`, qeRadioGroup, {
      observe: 'response',
    });
  }

  partialUpdate(qeRadioGroup: PartialUpdateQeRadioGroup): Observable<EntityResponseType> {
    return this.http.patch<IQeRadioGroup>(`${this.resourceUrl}/${this.getQeRadioGroupIdentifier(qeRadioGroup)}`, qeRadioGroup, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeRadioGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeRadioGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeRadioGroupIdentifier(qeRadioGroup: Pick<IQeRadioGroup, 'id'>): number {
    return qeRadioGroup.id;
  }

  compareQeRadioGroup(o1: Pick<IQeRadioGroup, 'id'> | null, o2: Pick<IQeRadioGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeRadioGroupIdentifier(o1) === this.getQeRadioGroupIdentifier(o2) : o1 === o2;
  }

  addQeRadioGroupToCollectionIfMissing<Type extends Pick<IQeRadioGroup, 'id'>>(
    qeRadioGroupCollection: Type[],
    ...qeRadioGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeRadioGroups: Type[] = qeRadioGroupsToCheck.filter(isPresent);
    if (qeRadioGroups.length > 0) {
      const qeRadioGroupCollectionIdentifiers = qeRadioGroupCollection.map(
        qeRadioGroupItem => this.getQeRadioGroupIdentifier(qeRadioGroupItem)!
      );
      const qeRadioGroupsToAdd = qeRadioGroups.filter(qeRadioGroupItem => {
        const qeRadioGroupIdentifier = this.getQeRadioGroupIdentifier(qeRadioGroupItem);
        if (qeRadioGroupCollectionIdentifiers.includes(qeRadioGroupIdentifier)) {
          return false;
        }
        qeRadioGroupCollectionIdentifiers.push(qeRadioGroupIdentifier);
        return true;
      });
      return [...qeRadioGroupsToAdd, ...qeRadioGroupCollection];
    }
    return qeRadioGroupCollection;
  }
}
