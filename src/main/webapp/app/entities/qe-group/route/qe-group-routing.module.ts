import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeGroupComponent } from '../list/qe-group.component';
import { QeGroupDetailComponent } from '../detail/qe-group-detail.component';
import { QeGroupUpdateComponent } from '../update/qe-group-update.component';
import { QeGroupRoutingResolveService } from './qe-group-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeGroupRoute: Routes = [
  {
    path: '',
    component: QeGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeGroupDetailComponent,
    resolve: {
      qeGroup: QeGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeGroupUpdateComponent,
    resolve: {
      qeGroup: QeGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeGroupUpdateComponent,
    resolve: {
      qeGroup: QeGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeGroupRoute)],
  exports: [RouterModule],
})
export class QeGroupRoutingModule {}
