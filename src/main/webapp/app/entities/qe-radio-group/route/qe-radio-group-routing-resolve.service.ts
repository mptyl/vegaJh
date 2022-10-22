import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeRadioGroup } from '../qe-radio-group.model';
import { QeRadioGroupService } from '../service/qe-radio-group.service';

@Injectable({ providedIn: 'root' })
export class QeRadioGroupRoutingResolveService implements Resolve<IQeRadioGroup | null> {
  constructor(protected service: QeRadioGroupService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeRadioGroup | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeRadioGroup: HttpResponse<IQeRadioGroup>) => {
          if (qeRadioGroup.body) {
            return of(qeRadioGroup.body);
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
