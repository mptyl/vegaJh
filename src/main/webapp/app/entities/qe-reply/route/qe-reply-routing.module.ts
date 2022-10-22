import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeReplyComponent } from '../list/qe-reply.component';
import { QeReplyDetailComponent } from '../detail/qe-reply-detail.component';
import { QeReplyUpdateComponent } from '../update/qe-reply-update.component';
import { QeReplyRoutingResolveService } from './qe-reply-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeReplyRoute: Routes = [
  {
    path: '',
    component: QeReplyComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeReplyDetailComponent,
    resolve: {
      qeReply: QeReplyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeReplyUpdateComponent,
    resolve: {
      qeReply: QeReplyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeReplyUpdateComponent,
    resolve: {
      qeReply: QeReplyRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeReplyRoute)],
  exports: [RouterModule],
})
export class QeReplyRoutingModule {}
