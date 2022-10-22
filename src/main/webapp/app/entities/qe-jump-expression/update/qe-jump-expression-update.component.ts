import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeJumpExpressionFormService, QeJumpExpressionFormGroup } from './qe-jump-expression-form.service';
import { IQeJumpExpression } from '../qe-jump-expression.model';
import { QeJumpExpressionService } from '../service/qe-jump-expression.service';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';

@Component({
  selector: 'jhi-qe-jump-expression-update',
  templateUrl: './qe-jump-expression-update.component.html',
})
export class QeJumpExpressionUpdateComponent implements OnInit {
  isSaving = false;
  qeJumpExpression: IQeJumpExpression | null = null;

  qeQuestionsSharedCollection: IQeQuestion[] = [];

  editForm: QeJumpExpressionFormGroup = this.qeJumpExpressionFormService.createQeJumpExpressionFormGroup();

  constructor(
    protected qeJumpExpressionService: QeJumpExpressionService,
    protected qeJumpExpressionFormService: QeJumpExpressionFormService,
    protected qeQuestionService: QeQuestionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeQuestion = (o1: IQeQuestion | null, o2: IQeQuestion | null): boolean => this.qeQuestionService.compareQeQuestion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeJumpExpression }) => {
      this.qeJumpExpression = qeJumpExpression;
      if (qeJumpExpression) {
        this.updateForm(qeJumpExpression);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeJumpExpression = this.qeJumpExpressionFormService.getQeJumpExpression(this.editForm);
    if (qeJumpExpression.id !== null) {
      this.subscribeToSaveResponse(this.qeJumpExpressionService.update(qeJumpExpression));
    } else {
      this.subscribeToSaveResponse(this.qeJumpExpressionService.create(qeJumpExpression));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeJumpExpression>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(qeJumpExpression: IQeJumpExpression): void {
    this.qeJumpExpression = qeJumpExpression;
    this.qeJumpExpressionFormService.resetForm(this.editForm, qeJumpExpression);

    this.qeQuestionsSharedCollection = this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(
      this.qeQuestionsSharedCollection,
      qeJumpExpression.qeQuestion
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeQuestionService
      .query()
      .pipe(map((res: HttpResponse<IQeQuestion[]>) => res.body ?? []))
      .pipe(
        map((qeQuestions: IQeQuestion[]) =>
          this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(qeQuestions, this.qeJumpExpression?.qeQuestion)
        )
      )
      .subscribe((qeQuestions: IQeQuestion[]) => (this.qeQuestionsSharedCollection = qeQuestions));
  }
}
