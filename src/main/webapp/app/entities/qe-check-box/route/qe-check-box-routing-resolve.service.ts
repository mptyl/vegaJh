import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeCheckBox } from '../qe-check-box.model';
import { QeCheckBoxService } from '../service/qe-check-box.service';

@Injectable({ providedIn: 'root' })
export class QeCheckBoxRoutingResolveService implements Resolve<IQeCheckBox | null> {
  constructor(protected service: QeCheckBoxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeCheckBox | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeCheckBox: HttpResponse<IQeCheckBox>) => {
          if (qeCheckBox.body) {
            return of(qeCheckBox.body);
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
