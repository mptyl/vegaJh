import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IQeGroup, NewQeGroup } from '../qe-group.model';

export type PartialUpdateQeGroup = Partial<IQeGroup> & Pick<IQeGroup, 'id'>;

export type EntityResponseType = HttpResponse<IQeGroup>;
export type EntityArrayResponseType = HttpResponse<IQeGroup[]>;

@Injectable({ providedIn: 'root' })
export class QeGroupService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/qe-groups');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(qeGroup: NewQeGroup): Observable<EntityResponseType> {
    return this.http.post<IQeGroup>(this.resourceUrl, qeGroup, { observe: 'response' });
  }

  update(qeGroup: IQeGroup): Observable<EntityResponseType> {
    return this.http.put<IQeGroup>(`${this.resourceUrl}/${this.getQeGroupIdentifier(qeGroup)}`, qeGroup, { observe: 'response' });
  }

  partialUpdate(qeGroup: PartialUpdateQeGroup): Observable<EntityResponseType> {
    return this.http.patch<IQeGroup>(`${this.resourceUrl}/${this.getQeGroupIdentifier(qeGroup)}`, qeGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQeGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQeGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getQeGroupIdentifier(qeGroup: Pick<IQeGroup, 'id'>): number {
    return qeGroup.id;
  }

  compareQeGroup(o1: Pick<IQeGroup, 'id'> | null, o2: Pick<IQeGroup, 'id'> | null): boolean {
    return o1 && o2 ? this.getQeGroupIdentifier(o1) === this.getQeGroupIdentifier(o2) : o1 === o2;
  }

  addQeGroupToCollectionIfMissing<Type extends Pick<IQeGroup, 'id'>>(
    qeGroupCollection: Type[],
    ...qeGroupsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const qeGroups: Type[] = qeGroupsToCheck.filter(isPresent);
    if (qeGroups.length > 0) {
      const qeGroupCollectionIdentifiers = qeGroupCollection.map(qeGroupItem => this.getQeGroupIdentifier(qeGroupItem)!);
      const qeGroupsToAdd = qeGroups.filter(qeGroupItem => {
        const qeGroupIdentifier = this.getQeGroupIdentifier(qeGroupItem);
        if (qeGroupCollectionIdentifiers.includes(qeGroupIdentifier)) {
          return false;
        }
        qeGroupCollectionIdentifiers.push(qeGroupIdentifier);
        return true;
      });
      return [...qeGroupsToAdd, ...qeGroupCollection];
    }
    return qeGroupCollection;
  }
}
