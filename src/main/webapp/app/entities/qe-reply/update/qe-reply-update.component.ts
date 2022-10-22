import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeReplyFormService, QeReplyFormGroup } from './qe-reply-form.service';
import { IQeReply } from '../qe-reply.model';
import { QeReplyService } from '../service/qe-reply.service';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';
import { ReplyType } from 'app/entities/enumerations/reply-type.model';

@Component({
  selector: 'jhi-qe-reply-update',
  templateUrl: './qe-reply-update.component.html',
})
export class QeReplyUpdateComponent implements OnInit {
  isSaving = false;
  qeReply: IQeReply | null = null;
  replyTypeValues = Object.keys(ReplyType);

  qeQuestionsSharedCollection: IQeQuestion[] = [];

  editForm: QeReplyFormGroup = this.qeReplyFormService.createQeReplyFormGroup();

  constructor(
    protected qeReplyService: QeReplyService,
    protected qeReplyFormService: QeReplyFormService,
    protected qeQuestionService: QeQuestionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeQuestion = (o1: IQeQuestion | null, o2: IQeQuestion | null): boolean => this.qeQuestionService.compareQeQuestion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeReply }) => {
      this.qeReply = qeReply;
      if (qeReply) {
        this.updateForm(qeReply);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeReply = this.qeReplyFormService.getQeReply(this.editForm);
    if (qeReply.id !== null) {
      this.subscribeToSaveResponse(this.qeReplyService.update(qeReply));
    } else {
      this.subscribeToSaveResponse(this.qeReplyService.create(qeReply));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeReply>>): void {
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

  protected updateForm(qeReply: IQeReply): void {
    this.qeReply = qeReply;
    this.qeReplyFormService.resetForm(this.editForm, qeReply);

    this.qeQuestionsSharedCollection = this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(
      this.qeQuestionsSharedCollection,
      qeReply.qeQuestion
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeQuestionService
      .query()
      .pipe(map((res: HttpResponse<IQeQuestion[]>) => res.body ?? []))
      .pipe(
        map((qeQuestions: IQeQuestion[]) =>
          this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(qeQuestions, this.qeReply?.qeQuestion)
        )
      )
      .subscribe((qeQuestions: IQeQuestion[]) => (this.qeQuestionsSharedCollection = qeQuestions));
  }
}
