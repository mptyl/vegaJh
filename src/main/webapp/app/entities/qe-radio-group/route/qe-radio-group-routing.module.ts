import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QeRadioGroupComponent } from '../list/qe-radio-group.component';
import { QeRadioGroupDetailComponent } from '../detail/qe-radio-group-detail.component';
import { QeRadioGroupUpdateComponent } from '../update/qe-radio-group-update.component';
import { QeRadioGroupRoutingResolveService } from './qe-radio-group-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const qeRadioGroupRoute: Routes = [
  {
    path: '',
    component: QeRadioGroupComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QeRadioGroupDetailComponent,
    resolve: {
      qeRadioGroup: QeRadioGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QeRadioGroupUpdateComponent,
    resolve: {
      qeRadioGroup: QeRadioGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QeRadioGroupUpdateComponent,
    resolve: {
      qeRadioGroup: QeRadioGroupRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qeRadioGroupRoute)],
  exports: [RouterModule],
})
export class QeRadioGroupRoutingModule {}
