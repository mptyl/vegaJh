import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeGroup } from '../qe-group.model';
import { QeGroupService } from '../service/qe-group.service';

@Injectable({ providedIn: 'root' })
export class QeGroupRoutingResolveService implements Resolve<IQeGroup | null> {
  constructor(protected service: QeGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeGroup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeGroup: HttpResponse<IQeGroup>) => {
          if (qeGroup.body) {
            return of(qeGroup.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
