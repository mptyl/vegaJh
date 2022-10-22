import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQeQuestion } from '../qe-question.model';

@Component({
  selector: 'jhi-qe-question-detail',
  templateUrl: './qe-question-detail.component.html',
})
export class QeQuestionDetailComponent implements OnInit {
  qeQuestion: IQeQuestion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeQuestion }) => {
      this.qeQuestion = qeQuestion;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
