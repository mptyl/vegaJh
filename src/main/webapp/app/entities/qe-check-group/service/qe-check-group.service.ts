import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeCheckGroup, NewQeCheckGroup } from '../qe-check-group.model';

export type PartialUpdateQeCheckGroup = Partial<IQeCheckGroup> & Pick<IQeCheckGroup, 'id'>;

export type EntityResponseType = HttpResponse<IQeCheckGroup>;
export type EntityArrayResponseType = HttpResponse<IQeCheckGroup[]>;

@Injectable({ providedIn: 'root' })
export class QeCheckGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-check-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeCheckGroup: NewQeCheckGroup): Observable<EntityResponseType> {
    return this.http.post<IQeCheckGroup>(this.resourceUrl, qeCheckGroup, { observe: 'response' });
  }

  update(qeCheckGroup: IQeCheckGroup): Observable<EntityResponseType> {
    return this.http.put<IQeCheckGroup>(`${this.resourceUrl}/${this.getQeCheckGroupIdentifier(qeCheckGroup)}`, qeCheckGroup, {
      observe: 'response',
    });
  }

  partialUpdate(qeCheckGroup: PartialUpdateQeCheckGroup): Observable<EntityResponseType> {
    return this.http.patch<IQeCheckGroup>(`${this.resourceUrl}/${this.getQeCheckGroupIdentifier(qeCheckGroup)}`, qeCheckGroup, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeCheckGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeCheckGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeCheckGroupIdentifier(qeCheckGroup: Pick<IQeCheckGroup, 'id'>): number {
    return qeCheckGroup.id;
  }

  compareQeCheckGroup(o1: Pick<IQeCheckGroup, 'id'> | null, o2: Pick<IQeCheckGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeCheckGroupIdentifier(o1) === this.getQeCheckGroupIdentifier(o2) : o1 === o2;
  }

  addQeCheckGroupToCollectionIfMissing<Type extends Pick<IQeCheckGroup, 'id'>>(
    qeCheckGroupCollection: Type[],
    ...qeCheckGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeCheckGroups: Type[] = qeCheckGroupsToCheck.filter(isPresent);
    if (qeCheckGroups.length > 0) {
      const qeCheckGroupCollectionIdentifiers = qeCheckGroupCollection.map(
        qeCheckGroupItem => this.getQeCheckGroupIdentifier(qeCheckGroupItem)!
      );
      const qeCheckGroupsToAdd = qeCheckGroups.filter(qeCheckGroupItem => {
        const qeCheckGroupIdentifier = this.getQeCheckGroupIdentifier(qeCheckGroupItem);
        if (qeCheckGroupCollectionIdentifiers.includes(qeCheckGroupIdentifier)) {
          return false;
        }
        qeCheckGroupCollectionIdentifiers.push(qeCheckGroupIdentifier);
        return true;
      });
      return [...qeCheckGroupsToAdd, ...qeCheckGroupCollection];
    }
    return qeCheckGroupCollection;
  }
}
