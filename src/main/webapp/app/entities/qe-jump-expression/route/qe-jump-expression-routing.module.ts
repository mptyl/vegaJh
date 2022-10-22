import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeJumpExpressionComponent } from '../list/qe-jump-expression.component';
import { QeJumpExpressionDetailComponent } from '../detail/qe-jump-expression-detail.component';
import { QeJumpExpressionUpdateComponent } from '../update/qe-jump-expression-update.component';
import { QeJumpExpressionRoutingResolveService } from './qe-jump-expression-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeJumpExpressionRoute: Routes = [
  {
    path: '',
    component: QeJumpExpressionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeJumpExpressionDetailComponent,
    resolve: {
      qeJumpExpression: QeJumpExpressionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeJumpExpressionUpdateComponent,
    resolve: {
      qeJumpExpression: QeJumpExpressionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeJumpExpressionUpdateComponent,
    resolve: {
      qeJumpExpression: QeJumpExpressionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeJumpExpressionRoute)],
  exports: [RouterModule],
})
export class QeJumpExpressionRoutingModule {}
