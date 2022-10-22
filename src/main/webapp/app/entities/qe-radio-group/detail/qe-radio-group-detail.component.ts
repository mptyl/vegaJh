import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeRadioGroup } from '../qe-radio-group.model';

@Component({
  selector: 'jhi-qe-radio-group-detail',
  templateUrl: './qe-radio-group-detail.component.html',
})
export class QeRadioGroupDetailComponent implements OnInit {
  qeRadioGroup: IQeRadioGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeRadioGroup }) => {
      this.qeRadioGroup = qeRadioGroup;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
