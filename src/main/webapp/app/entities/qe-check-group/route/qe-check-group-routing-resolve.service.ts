import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeCheckGroup } from '../qe-check-group.model';
import { QeCheckGroupService } from '../service/qe-check-group.service';

@Injectable({ providedIn: 'root' })
export class QeCheckGroupRoutingResolveService implements Resolve<IQeCheckGroup | null> {
  constructor(protected service: QeCheckGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeCheckGroup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeCheckGroup: HttpResponse<IQeCheckGroup>) => {
          if (qeCheckGroup.body) {
            return of(qeCheckGroup.body);
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
