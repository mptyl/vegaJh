import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeRadioBoxComponent } from '../list/qe-radio-box.component';
import { QeRadioBoxDetailComponent } from '../detail/qe-radio-box-detail.component';
import { QeRadioBoxUpdateComponent } from '../update/qe-radio-box-update.component';
import { QeRadioBoxRoutingResolveService } from './qe-radio-box-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeRadioBoxRoute: Routes = [
  {
    path: '',
    component: QeRadioBoxComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeRadioBoxDetailComponent,
    resolve: {
      qeRadioBox: QeRadioBoxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeRadioBoxUpdateComponent,
    resolve: {
      qeRadioBox: QeRadioBoxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeRadioBoxUpdateComponent,
    resolve: {
      qeRadioBox: QeRadioBoxRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeRadioBoxRoute)],
  exports: [RouterModule],
})
export class QeRadioBoxRoutingModule {}
