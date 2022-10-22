import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeCheckBox } from '../qe-check-box.model';

@Component({
  selector: 'jhi-qe-check-box-detail',
  templateUrl: './qe-check-box-detail.component.html',
})
export class QeCheckBoxDetailComponent implements OnInit {
  qeCheckBox: IQeCheckBox | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeCheckBox }) => {
      this.qeCheckBox = qeCheckBox;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
