import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeCheckGroup } from '../qe-check-group.model';

@Component({
  selector: 'jhi-qe-check-group-detail',
  templateUrl: './qe-check-group-detail.component.html',
})
export class QeCheckGroupDetailComponent implements OnInit {
  qeCheckGroup: IQeCheckGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeCheckGroup }) => {
      this.qeCheckGroup = qeCheckGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
