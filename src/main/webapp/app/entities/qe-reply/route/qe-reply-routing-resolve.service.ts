import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeReply } from '../qe-reply.model';
import { QeReplyService } from '../service/qe-reply.service';

@Injectable({ providedIn: 'root' })
export class QeReplyRoutingResolveService implements Resolve<IQeReply | null> {
  constructor(protected service: QeReplyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeReply | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeReply: HttpResponse<IQeReply>) => {
          if (qeReply.body) {
            return of(qeReply.body);
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
