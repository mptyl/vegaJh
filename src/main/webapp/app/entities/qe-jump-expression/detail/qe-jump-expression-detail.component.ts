import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeJumpExpression } from '../qe-jump-expression.model';

@Component({
  selector: 'jhi-qe-jump-expression-detail',
  templateUrl: './qe-jump-expression-detail.component.html',
})
export class QeJumpExpressionDetailComponent implements OnInit {
  qeJumpExpression: IQeJumpExpression | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeJumpExpression }) => {
      this.qeJumpExpression = qeJumpExpression;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
