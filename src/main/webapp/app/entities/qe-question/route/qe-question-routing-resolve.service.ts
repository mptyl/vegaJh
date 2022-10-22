import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQeQuestion } from '../qe-question.model';
import { QeQuestionService } from '../service/qe-question.service';

@Injectable({ providedIn: 'root' })
export class QeQuestionRoutingResolveService implements Resolve<IQeQuestion | null> {
  constructor(protected service: QeQuestionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQeQuestion | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qeQuestion: HttpResponse<IQeQuestion>) => {
          if (qeQuestion.body) {
            return of(qeQuestion.body);
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
