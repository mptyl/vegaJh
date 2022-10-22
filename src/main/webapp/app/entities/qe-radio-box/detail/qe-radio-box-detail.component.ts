import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeRadioBox } from '../qe-radio-box.model';

@Component({
  selector: 'jhi-qe-radio-box-detail',
  templateUrl: './qe-radio-box-detail.component.html',
})
export class QeRadioBoxDetailComponent implements OnInit {
  qeRadioBox: IQeRadioBox | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeRadioBox }) => {
      this.qeRadioBox = qeRadioBox;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
