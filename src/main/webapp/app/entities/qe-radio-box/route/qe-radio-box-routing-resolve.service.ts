import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeRadioBox } from '../qe-radio-box.model';
import { QeRadioBoxService } from '../service/qe-radio-box.service';

@Injectable({ providedIn: 'root' })
export class QeRadioBoxRoutingResolveService implements Resolve<IQeRadioBox | null> {
  constructor(protected service: QeRadioBoxService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeRadioBox | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeRadioBox: HttpResponse<IQeRadioBox>) => {
          if (qeRadioBox.body) {
            return of(qeRadioBox.body);
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
