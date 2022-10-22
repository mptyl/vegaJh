import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeCheckGroupComponent } from '../list/qe-check-group.component';
import { QeCheckGroupDetailComponent } from '../detail/qe-check-group-detail.component';
import { QeCheckGroupUpdateComponent } from '../update/qe-check-group-update.component';
import { QeCheckGroupRoutingResolveService } from './qe-check-group-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeCheckGroupRoute: Routes = [
  {
    path: '',
    component: QeCheckGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeCheckGroupDetailComponent,
    resolve: {
      qeCheckGroup: QeCheckGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeCheckGroupUpdateComponent,
    resolve: {
      qeCheckGroup: QeCheckGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeCheckGroupUpdateComponent,
    resolve: {
      qeCheckGroup: QeCheckGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeCheckGroupRoute)],
  exports: [RouterModule],
})
export class QeCheckGroupRoutingModule {}
