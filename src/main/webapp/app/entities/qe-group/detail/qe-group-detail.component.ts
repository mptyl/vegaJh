import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeGroup } from '../qe-group.model';

@Component({
  selector: 'jhi-qe-group-detail',
  templateUrl: './qe-group-detail.component.html',
})
export class QeGroupDetailComponent implements OnInit {
  qeGroup: IQeGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeGroup }) => {
      this.qeGroup = qeGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
