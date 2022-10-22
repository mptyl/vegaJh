import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeQuestionComponent } from '../list/qe-question.component';
import { QeQuestionDetailComponent } from '../detail/qe-question-detail.component';
import { QeQuestionUpdateComponent } from '../update/qe-question-update.component';
import { QeQuestionRoutingResolveService } from './qe-question-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeQuestionRoute: Routes = [
  {
    path: '',
    component: QeQuestionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeQuestionDetailComponent,
    resolve: {
      qeQuestion: QeQuestionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeQuestionUpdateComponent,
    resolve: {
      qeQuestion: QeQuestionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeQuestionUpdateComponent,
    resolve: {
      qeQuestion: QeQuestionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeQuestionRoute)],
  exports: [RouterModule],
})
export class QeQuestionRoutingModule {}
