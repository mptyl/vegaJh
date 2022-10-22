import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { QeCheckGroupFormService, QeCheckGroupFormGroup } from './qe-check-group-form.service';
import { IQeCheckGroup } from '../qe-check-group.model';
import { QeCheckGroupService } from '../service/qe-check-group.service';
import { IQeQuestion } from 'app/entities/qe-question/qe-question.model';
import { QeQuestionService } from 'app/entities/qe-question/service/qe-question.service';
import { Orientation } from 'app/entities/enumerations/orientation.model';

@Component({
  selector: 'jhi-qe-check-group-update',
  templateUrl: './qe-check-group-update.component.html',
})
export class QeCheckGroupUpdateComponent implements OnInit {
  isSaving = false;
  qeCheckGroup: IQeCheckGroup | null = null;
  orientationValues = Object.keys(Orientation);

  qeQuestionsSharedCollection: IQeQuestion[] = [];

  editForm: QeCheckGroupFormGroup = this.qeCheckGroupFormService.createQeCheckGroupFormGroup();

  constructor(
    protected qeCheckGroupService: QeCheckGroupService,
    protected qeCheckGroupFormService: QeCheckGroupFormService,
    protected qeQuestionService: QeQuestionService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareQeQuestion = (o1: IQeQuestion | null, o2: IQeQuestion | null): boolean => this.qeQuestionService.compareQeQuestion(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qeCheckGroup }) => {
      this.qeCheckGroup = qeCheckGroup;
      if (qeCheckGroup) {
        this.updateForm(qeCheckGroup);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const qeCheckGroup = this.qeCheckGroupFormService.getQeCheckGroup(this.editForm);
    if (qeCheckGroup.id !== null) {
      this.subscribeToSaveResponse(this.qeCheckGroupService.update(qeCheckGroup));
    } else {
      this.subscribeToSaveResponse(this.qeCheckGroupService.create(qeCheckGroup));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQeCheckGroup>>): void {
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

  protected updateForm(qeCheckGroup: IQeCheckGroup): void {
    this.qeCheckGroup = qeCheckGroup;
    this.qeCheckGroupFormService.resetForm(this.editForm, qeCheckGroup);

    this.qeQuestionsSharedCollection = this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(
      this.qeQuestionsSharedCollection,
      qeCheckGroup.qeQuestion
    );
  }

  protected loadRelationshipsOptions(): void {
    this.qeQuestionService
      .query()
      .pipe(map((res: HttpResponse<IQeQuestion[]>) => res.body ?? []))
      .pipe(
        map((qeQuestions: IQeQuestion[]) =>
          this.qeQuestionService.addQeQuestionToCollectionIfMissing<IQeQuestion>(qeQuestions, this.qeCheckGroup?.qeQuestion)
        )
      )
      .subscribe((qeQuestions: IQeQuestion[]) => (this.qeQuestionsSharedCollection = qeQuestions));
  }
}
