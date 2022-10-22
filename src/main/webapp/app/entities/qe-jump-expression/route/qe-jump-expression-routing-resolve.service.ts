import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeJumpExpression } from '../qe-jump-expression.model';
import { QeJumpExpressionService } from '../service/qe-jump-expression.service';

@Injectable({ providedIn: 'root' })
export class QeJumpExpressionRoutingResolveService implements Resolve<IQeJumpExpression | null> {
  constructor(protected service: QeJumpExpressionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeJumpExpression | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeJumpExpression: HttpResponse<IQeJumpExpression>) => {
          if (qeJumpExpression.body) {
            return of(qeJumpExpression.body);
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
