import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeCheckBoxComponent } from '../list/qe-check-box.component';
import { QeCheckBoxDetailComponent } from '../detail/qe-check-box-detail.component';
import { QeCheckBoxUpdateComponent } from '../update/qe-check-box-update.component';
import { QeCheckBoxRoutingResolveService } from './qe-check-box-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeCheckBoxRoute: Routes = [
  {
    path: '',
    component: QeCheckBoxComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeCheckBoxDetailComponent,
    resolve: {
      qeCheckBox: QeCheckBoxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeCheckBoxUpdateComponent,
    resolve: {
      qeCheckBox: QeCheckBoxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeCheckBoxUpdateComponent,
    resolve: {
      qeCheckBox: QeCheckBoxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeCheckBoxRoute)],
  exports: [RouterModule],
})
export class QeCheckBoxRoutingModule {}
